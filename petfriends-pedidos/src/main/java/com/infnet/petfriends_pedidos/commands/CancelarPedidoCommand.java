package com.infnet.petfriends_pedidos.commands;

/**
 * Comando para cancelar pedido.
 * FECHADO ou EM_PREPARACAO -> CANCELADO
 */
public class CancelarPedidoCommand extends BaseCommand<String> {

    public final String motivo;

    public CancelarPedidoCommand(String id, String motivo) {
        super(id);
        this.motivo = motivo;
    }
}