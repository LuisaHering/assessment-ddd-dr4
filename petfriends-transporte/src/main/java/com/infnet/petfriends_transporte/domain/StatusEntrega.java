package com.infnet.petfriends_transporte.domain;

/**
 * Estados possiveis da entrega no contexto de Transporte
 */
public enum StatusEntrega {
    AGUARDANDO_COLETA,
    EM_TRANSITO,
    SAIU_PARA_ENTREGA,
    ENTREGUE,
    TENTATIVA_FALHA,
    EXTRAVIADO
}