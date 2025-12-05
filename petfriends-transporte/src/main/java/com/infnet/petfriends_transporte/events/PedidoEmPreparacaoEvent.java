package com.infnet.petfriends_transporte.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Evento RECEBIDO do microservico Almoxarifado
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEmPreparacaoEvent {

    private String eventId;
    private LocalDateTime timestamp;
    private String eventType;

    private String pedidoId;
    private String almoxarifadoId;
}
