package com.infnet.petfriends_pedidos.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Classe base para todos os comandos que serão processados
 * pelo Axon Framework e provocarão a persistência de eventos.
 * A anotação @TargetAggregateIdentifier indica qual agregado
 * será alvo do comando.
 */
public class BaseCommand<T> {

    @TargetAggregateIdentifier
    public final T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}