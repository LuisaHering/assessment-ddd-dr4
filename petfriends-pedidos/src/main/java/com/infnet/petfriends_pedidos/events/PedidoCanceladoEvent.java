package com.infnet.petfriends_pedidos.events;

import java.time.LocalDateTime;

/**
 * Evento disparado quando pedido é cancelado.
 */
public class PedidoCanceladoEvent extends BaseEvent<String> {

    public final String motivo;
    public final LocalDateTime dataCancelamento;

    public PedidoCanceladoEvent(String id, String motivo, LocalDateTime dataCancelamento) {
        super(id);
        this.motivo = motivo;
        this.dataCancelamento = dataCancelamento;
    }
}