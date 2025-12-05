package com.infnet.petfriends_pedidos.events;

import java.time.LocalDateTime;

/**
 * Evento disparado quando pedido é enviado para preparação.
 * Este evento será consumido pelo microsserviço Almoxarifado.
 */
public class PedidoEnviadoEvent extends BaseEvent<String> {

    public final LocalDateTime dataEnvio;
    public final String clienteId;

    public PedidoEnviadoEvent(String id, String clienteId, LocalDateTime dataEnvio) {
        super(id);
        this.clienteId = clienteId;
        this.dataEnvio = dataEnvio;
    }
}