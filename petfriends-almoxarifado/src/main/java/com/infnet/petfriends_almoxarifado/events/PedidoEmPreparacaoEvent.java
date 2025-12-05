package com.infnet.petfriends_almoxarifado.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Evento PUBLICADO pelo Almoxarifado
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEmPreparacaoEvent {

    private String eventId;
    private LocalDateTime timestamp;
    private String eventType = "PedidoEmPreparacao";

    private String pedidoId;
    private String almoxarifadoId;
}