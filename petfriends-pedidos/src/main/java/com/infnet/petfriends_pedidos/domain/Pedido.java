package com.infnet.petfriends_pedidos.domain;

import com.infnet.petfriends_pedidos.commands.*;
import com.infnet.petfriends_pedidos.events.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * QUESTAO 1A DO AT - Agregado Pedido
 * Agregado NAO-ANEMICO com metodos de negocio
 * Implementa transicoes de estado conforme diagrama do Assessment
 */
@Aggregate
@Entity
@Slf4j
public class Pedido {

    @AggregateIdentifier
    @Id
    private String id;

    private String clienteId;
    private BigDecimal valorTotal;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataFechamento;
    private String status;

    // Construtor padrao obrigatorio
    public Pedido() {
    }

    // =================================================================
    // COMANDO: CRIAR PEDIDO (NOVO)
    // =================================================================

    @CommandHandler
    public Pedido(CriarPedidoCommand command) {
        log.info("========================================");
        log.info("COMANDO RECEBIDO: CriarPedidoCommand");
        log.info("Cliente ID: {}", command.clienteId);
        log.info("Valor Total: {}", command.valorTotal);

        // Validacoes de negocio
        if (command.valorTotal == null || command.valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("ERRO: Valor do pedido invalido: {}", command.valorTotal);
            throw new IllegalArgumentException("Valor do pedido deve ser positivo");
        }
        if (command.clienteId == null || command.clienteId.isBlank()) {
            log.error("ERRO: Cliente nao informado");
            throw new IllegalArgumentException("Cliente e obrigatorio");
        }

        log.info("Validacoes OK! Aplicando evento PedidoCriadoEvent");

        // Aplica evento (NAO altera estado aqui!)
        AggregateLifecycle.apply(new PedidoCriadoEvent(
                command.id,
                command.clienteId,
                command.valorTotal,
                LocalDateTime.now(),
                StatusPedido.NOVO.name()
        ));
    }

    @EventSourcingHandler
    protected void on(PedidoCriadoEvent evento) {
        log.info("EVENTO APLICADO: PedidoCriadoEvent");
        log.info("Pedido ID: {}", evento.id);
        log.info("Status: NOVO");

        // UNICO local que altera estado
        this.id = evento.id;
        this.clienteId = evento.clienteId;
        this.valorTotal = evento.valorTotal;
        this.dataCriacao = evento.dataCriacao;
        this.status = evento.status;

        log.info("Estado do agregado atualizado!");
        log.info("========================================");
    }

    // =================================================================
    // COMANDO: FECHAR PEDIDO (NOVO -> FECHADO)
    // =================================================================

    @CommandHandler
    public void handle(FecharPedidoCommand command) {
        log.info("========================================");
        log.info("COMANDO RECEBIDO: FecharPedidoCommand");
        log.info("Pedido ID: {}", this.id);
        log.info("Status Atual: {}", this.status);

        // Validacao de regra de negocio
        if (!this.status.equals(StatusPedido.NOVO.name())) {
            log.error("ERRO: Transicao invalida. Status atual: {}", this.status);
            throw new IllegalStateException(
                    "Apenas pedidos novos podem ser fechados. Status atual: " + this.status
            );
        }

        log.info("Validacao OK! Aplicando evento PedidoFechadoEvent");

        // Aplica evento
        AggregateLifecycle.apply(new PedidoFechadoEvent(
                this.id,
                LocalDateTime.now()
        ));
    }

    @EventSourcingHandler
    protected void on(PedidoFechadoEvent evento) {
        log.info("EVENTO APLICADO: PedidoFechadoEvent");
        log.info("Status: NOVO -> FECHADO");

        this.status = StatusPedido.FECHADO.name();
        this.dataFechamento = evento.dataFechamento;

        log.info("Estado do agregado atualizado!");
        log.info("========================================");
    }

    // =================================================================
    // COMANDO: ENVIAR PEDIDO (FECHADO -> EM_PREPARACAO)
    // =================================================================

    @CommandHandler
    public void handle(EnviarPedidoCommand command) {
        log.info("========================================");
        log.info("COMANDO RECEBIDO: EnviarPedidoCommand");
        log.info("Pedido ID: {}", this.id);
        log.info("Status Atual: {}", this.status);

        // Validacao de regra de negocio
        if (!this.status.equals(StatusPedido.FECHADO.name())) {
            log.error("ERRO: Transicao invalida. Status atual: {}", this.status);
            throw new IllegalStateException(
                    "Apenas pedidos fechados podem ser enviados. Status atual: " + this.status
            );
        }

        log.info("Validacao OK! Aplicando evento PedidoEnviadoEvent");

        // Aplica evento
        AggregateLifecycle.apply(new PedidoEnviadoEvent(
                this.id,
                this.clienteId,
                LocalDateTime.now()
        ));
    }

    @EventSourcingHandler
    protected void on(PedidoEnviadoEvent evento) {
        log.info("EVENTO APLICADO: PedidoEnviadoEvent");
        log.info("Status: FECHADO -> EM_PREPARACAO");

        this.status = StatusPedido.EM_PREPARACAO.name();

        log.info("Estado do agregado atualizado!");
        log.info(">>> TRIGGER: EventListener detectara este evento");
        log.info("========================================");
    }

    // =================================================================
    // COMANDO: CANCELAR PEDIDO
    // =================================================================

    @CommandHandler
    public void handle(CancelarPedidoCommand command) {
        log.info("========================================");
        log.info("COMANDO RECEBIDO: CancelarPedidoCommand");
        log.info("Pedido ID: {}", this.id);
        log.info("Motivo: {}", command.motivo);

        // Validacao de regra de negocio
        if (this.status.equals(StatusPedido.CANCELADO.name())) {
            log.error("ERRO: Pedido ja esta cancelado");
            throw new IllegalStateException("Pedido ja esta cancelado");
        }
        if (this.status.equals(StatusPedido.EM_TRANSITO.name()) ||
                this.status.equals(StatusPedido.ENTREGUE.name())) {
            log.error("ERRO: Nao e possivel cancelar. Status: {}", this.status);
            throw new IllegalStateException(
                    "Nao e possivel cancelar pedido em transito ou entregue"
            );
        }

        log.info("Validacao OK! Aplicando evento PedidoCanceladoEvent");

        // Aplica evento
        AggregateLifecycle.apply(new PedidoCanceladoEvent(
                this.id,
                command.motivo,
                LocalDateTime.now()
        ));
    }

    @EventSourcingHandler
    protected void on(PedidoCanceladoEvent evento) {
        log.info("EVENTO APLICADO: PedidoCanceladoEvent");
        log.info("Status: {} -> CANCELADO", this.status);

        this.status = StatusPedido.CANCELADO.name();

        log.info("Estado do agregado atualizado!");
        log.info("========================================");
    }

    // =================================================================
    // GETTERS E SETTERS (necessarios para JPA)
    // =================================================================

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}