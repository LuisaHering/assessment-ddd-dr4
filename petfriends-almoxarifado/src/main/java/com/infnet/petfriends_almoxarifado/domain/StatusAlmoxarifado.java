package com.infnet.petfriends_almoxarifado.domain;

/**
 * Estados possíveis do pedido no contexto do Almoxarifado
 */
public enum StatusAlmoxarifado {
    AGUARDANDO_PREPARACAO,
    EM_PREPARACAO,
    PRONTO_ENVIO,
    DESPACHADO,
    CANCELADO
}
