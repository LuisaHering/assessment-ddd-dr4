package com.infnet.petfriends_almoxarifado.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Evento RECEBIDO do microsserviço Pedidos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoParaAlmoxarifadoEvent {

    private String eventId;
    private LocalDateTime timestamp;
    private String eventType;

    private String pedidoId;
    private String clienteId;
    private BigDecimal valorTotal;
    private String prioridade;
}