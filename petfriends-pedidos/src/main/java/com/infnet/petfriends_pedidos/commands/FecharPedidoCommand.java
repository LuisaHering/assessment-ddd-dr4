package com.infnet.petfriends_pedidos.commands;

/**
 * Comando para fechar pedido após pagamento confirmado.
 * NOVO -> FECHADO
 */
public class FecharPedidoCommand extends BaseCommand<String> {

    public FecharPedidoCommand(String id) {
        super(id);
    }
}