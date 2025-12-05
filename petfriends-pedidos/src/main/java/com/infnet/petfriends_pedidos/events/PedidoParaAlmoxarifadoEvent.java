package com.infnet.petfriends_pedidos.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * QUESTÃO 7 DO AT - Evento para Almoxarifado (Fat Event)
 * Contém todas as informações necessárias para preparação
 */
public class PedidoParaAlmoxarifadoEvent {

    // Metadados do evento
    private String eventId;
    private LocalDateTime timestamp;
    private String eventType = "PedidoParaAlmoxarifado";

    // Identificadores
    private String pedidoId;
    private String clienteId;

    // Dados para preparação
    private BigDecimal valorTotal;
    private String observacoes;

    // Construtor default (Jackson precisa)
    public PedidoParaAlmoxarifadoEvent() {
    }

    // Construtor completo
    public PedidoParaAlmoxarifadoEvent(String eventId, LocalDateTime timestamp,
                                       String pedidoId, String clienteId,
                                       BigDecimal valorTotal) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters (TODOS necessários para serialização JSON)
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
