package com.infnet.petfriends_pedidos.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Evento disparado quando um novo pedido é criado no sistema.
 * Representa algo que JÁ ACONTECEU (verbo no passado).
 * Contém os dados essenciais do pedido criado.
 */
public class PedidoCriadoEvent extends BaseEvent<String> {

    public final String clienteId;
    public final BigDecimal valorTotal;
    public final LocalDateTime dataCriacao;
    public final String status;

    public PedidoCriadoEvent(String id, String clienteId,
                             BigDecimal valorTotal,
                             LocalDateTime dataCriacao,
                             String status) {
        super(id);
        this.clienteId = clienteId;
        this.valorTotal = valorTotal;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }
}