package com.infnet.petfriends_pedidos.messaging.events;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * QUESTÃO 7 DO AT - Evento enviado ao Almoxarifado
 * Fat Event com payload completo (Open Host Service)
 */
public class PedidoParaAlmoxarifadoEvent {

    private String eventId;
    private LocalDateTime timestamp;
    private String eventType = "PedidoParaAlmoxarifado";

    // Identificadores
    private String pedidoId;
    private String clienteId;

    // Dados para preparação
    private BigDecimal valorTotal;
    private String prioridade = "NORMAL";

    // Construtor default (Jackson precisa)
    public PedidoParaAlmoxarifadoEvent() {}

    // Construtor com dados
    public PedidoParaAlmoxarifadoEvent(String eventId, LocalDateTime timestamp,
                                       String pedidoId, String clienteId,
                                       BigDecimal valorTotal) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters
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

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
}