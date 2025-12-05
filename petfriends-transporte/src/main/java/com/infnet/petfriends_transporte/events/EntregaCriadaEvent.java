package com.infnet.petfriends_transporte.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Evento PUBLICADO pelo Transporte
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntregaCriadaEvent {

    private String eventId;
    private LocalDateTime timestamp;
    private String eventType = "EntregaCriada";

    private String pedidoId;
    private String entregaId;
    private String codigoRastreio;
}