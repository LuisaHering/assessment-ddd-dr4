package com.infnet.petfriends_almoxarifado.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * QUESTÃO 1 DO AT - Agregado PedidoAlmoxarifado
 * Agregado NÃO-ANÊMICO com métodos de negócio
 */
@Entity
@Table(name = "pedidos_almoxarifado")
@Getter
@Setter
@NoArgsConstructor
public class PedidoAlmoxarifado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String pedidoId; // ID do pedido original

    private String clienteId;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAlmoxarifado status;

    private String observacoes;

    // =========================================
    // FACTORY METHOD (padrão DDD)
    // =========================================

    public static PedidoAlmoxarifado criar(String pedidoId, String clienteId,
                                           BigDecimal valorTotal) {
        PedidoAlmoxarifado pedido = new PedidoAlmoxarifado();
        pedido.pedidoId = pedidoId;
        pedido.clienteId = clienteId;
        pedido.valorTotal = valorTotal;
        pedido.dataSolicitacao = LocalDateTime.now();
        pedido.status = StatusAlmoxarifado.AGUARDANDO_PREPARACAO;
        return pedido;
    }

    // =========================================
    // MÉTODOS DE NEGÓCIO (Agregado não-anêmico)
    // =========================================

    public void iniciarPreparacao() {
        if (this.status != StatusAlmoxarifado.AGUARDANDO_PREPARACAO) {
            throw new IllegalStateException(
                    "Pedido não pode iniciar preparação. Status atual: " + this.status
            );
        }
        this.status = StatusAlmoxarifado.EM_PREPARACAO;
    }

    public void concluirPreparacao() {
        if (this.status != StatusAlmoxarifado.EM_PREPARACAO) {
            throw new IllegalStateException(
                    "Pedido não está em preparação. Status atual: " + this.status
            );
        }
        this.status = StatusAlmoxarifado.PRONTO_ENVIO;
    }

    public void despachar() {
        if (this.status != StatusAlmoxarifado.PRONTO_ENVIO) {
            throw new IllegalStateException(
                    "Pedido não está pronto para envio. Status atual: " + this.status
            );
        }
        this.status = StatusAlmoxarifado.DESPACHADO;
    }

    public void cancelar(String motivo) {
        if (this.status == StatusAlmoxarifado.DESPACHADO) {
            throw new IllegalStateException(
                    "Não é possível cancelar pedido já despachado"
            );
        }
        this.status = StatusAlmoxarifado.CANCELADO;
        this.observacoes = "Cancelado: " + motivo;
    }
}
