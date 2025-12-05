package com.infnet.petfriends_pedidos.commands;

import java.math.BigDecimal;

/**
 * Comando para criar um novo pedido.
 * Representa a INTENÇÃO de criar (verbo imperativo).
 * Será processado pelo CommandHandler no agregado.
 */
public class CriarPedidoCommand extends BaseCommand<String> {

    public final String clienteId;
    public final BigDecimal valorTotal;

    public CriarPedidoCommand(String id, String clienteId,
                              BigDecimal valorTotal) {
        super(id);
        this.clienteId = clienteId;
        this.valorTotal = valorTotal;
    }
}