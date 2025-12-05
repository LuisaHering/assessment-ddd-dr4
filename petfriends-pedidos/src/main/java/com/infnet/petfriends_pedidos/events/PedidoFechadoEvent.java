package com.infnet.petfriends_pedidos.events;

import java.time.LocalDateTime;

/**
 * Evento disparado quando pedido é fechado (pagamento confirmado).
 */
public class PedidoFechadoEvent extends BaseEvent<String> {

    public final LocalDateTime dataFechamento;

    public PedidoFechadoEvent(String id, LocalDateTime dataFechamento) {
        super(id);
        this.dataFechamento = dataFechamento;
    }
}