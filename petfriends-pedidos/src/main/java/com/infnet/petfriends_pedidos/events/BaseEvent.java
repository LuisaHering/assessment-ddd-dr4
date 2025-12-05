package com.infnet.petfriends_pedidos.events;

/**
 * Classe base para todos os eventos de domínio que serão
 * persistidos no Event Store do Axon Framework.
 * Contém o identificador do agregado ao qual o evento pertence.
 */
public class BaseEvent<T> {

    public final T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}