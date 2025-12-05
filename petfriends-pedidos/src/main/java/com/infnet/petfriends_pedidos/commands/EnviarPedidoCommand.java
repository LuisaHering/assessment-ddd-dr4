package com.infnet.petfriends_pedidos.commands;

/**
 * Comando para enviar pedido ao almoxarifado.
 * FECHADO -> EM_PREPARACAO
 */
public class EnviarPedidoCommand extends BaseCommand<String> {

    public EnviarPedidoCommand(String id) {
        super(id);
    }
}