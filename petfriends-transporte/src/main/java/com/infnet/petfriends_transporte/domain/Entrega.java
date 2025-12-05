package com.infnet.petfriends_transporte.domain;

import com.infnet.petfriends_transporte.domain.vo.CodigoRastreio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * QUESTAO 3 DO AT - Agregado Entrega
 * Agregado NAO-ANEMICO com metodos de negocio
 */
@Entity
@Table(name = "entregas")
@Getter
@Setter
@NoArgsConstructor
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String pedidoId;

    private String almoxarifadoId;

    @Column(nullable = false)
    private LocalDateTime dataRecebimento;

    private LocalDateTime dataEntregaPrevista;
    private LocalDateTime dataEntregaReal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEntrega status;

    @Embedded
    private CodigoRastreio codigoRastreio;

    private String observacoes;

    // =========================================
    // FACTORY METHOD (padrao DDD)
    // =========================================

    public static Entrega criar(String pedidoId, String almoxarifadoId) {
        Entrega entrega = new Entrega();
        entrega.pedidoId = pedidoId;
        entrega.almoxarifadoId = almoxarifadoId;
        entrega.dataRecebimento = LocalDateTime.now();
        entrega.dataEntregaPrevista = LocalDateTime.now().plusDays(5);
        entrega.status = StatusEntrega.AGUARDANDO_COLETA;
        entrega.codigoRastreio = CodigoRastreio.gerar();
        return entrega;
    }

    // =========================================
    // METODOS DE NEGOCIO (Agregado nao-anemico)
    // =========================================

    public void iniciarTransporte() {
        if (this.status != StatusEntrega.AGUARDANDO_COLETA) {
            throw new IllegalStateException(
                    "Entrega nao pode iniciar transporte. Status atual: " + this.status
            );
        }
        this.status = StatusEntrega.EM_TRANSITO;
    }

    public void sairParaEntrega() {
        if (this.status != StatusEntrega.EM_TRANSITO) {
            throw new IllegalStateException(
                    "Entrega nao esta em transito. Status atual: " + this.status
            );
        }
        this.status = StatusEntrega.SAIU_PARA_ENTREGA;
    }

    public void confirmarEntrega() {
        if (this.status != StatusEntrega.SAIU_PARA_ENTREGA) {
            throw new IllegalStateException(
                    "Entrega nao saiu para entrega. Status atual: " + this.status
            );
        }
        this.status = StatusEntrega.ENTREGUE;
        this.dataEntregaReal = LocalDateTime.now();
    }

    public void registrarTentativaFalha(String motivo) {
        if (this.status == StatusEntrega.ENTREGUE) {
            throw new IllegalStateException("Entrega ja foi concluida");
        }
        this.status = StatusEntrega.TENTATIVA_FALHA;
        this.observacoes = "Tentativa falha: " + motivo;
    }

    public void marcarComoExtraviado(String motivo) {
        if (this.status == StatusEntrega.ENTREGUE) {
            throw new IllegalStateException("Entrega ja foi concluida");
        }
        this.status = StatusEntrega.EXTRAVIADO;
        this.observacoes = "Extraviado: " + motivo;
    }
}
