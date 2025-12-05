package com.infnet.petfriends_pedidos.domain;

/**
 * Estados possíveis do Pedido conforme diagrama de estados
 * apresentado nas aulas 7 e 12.
 */
public enum StatusPedido {
    NOVO,
    FECHADO,
    EM_PREPARACAO,
    EM_TRANSITO,
    ENTREGUE,
    CANCELADO
}