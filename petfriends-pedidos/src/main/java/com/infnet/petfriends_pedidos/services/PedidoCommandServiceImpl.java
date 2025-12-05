package com.infnet.petfriends_pedidos.services;

import com.infnet.petfriends_pedidos.commands.*;
import com.infnet.petfriends_pedidos.domain.Pedido;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class PedidoCommandServiceImpl implements PedidoCommandService {

    private final CommandGateway commandGateway;

    public PedidoCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> criarPedido(Pedido pedido) {
        return commandGateway.send(new CriarPedidoCommand(
                UUID.randomUUID().toString(),
                pedido.getClienteId(),
                pedido.getValorTotal()
        ));
    }

    @Override
    public CompletableFuture<Void> fecharPedido(String id) {
        return commandGateway.send(new FecharPedidoCommand(id));
    }

    @Override
    public CompletableFuture<Void> enviarPedido(String id) {
        return commandGateway.send(new EnviarPedidoCommand(id));
    }

    @Override
    public CompletableFuture<Void> cancelarPedido(String id, String motivo) {
        return commandGateway.send(new CancelarPedidoCommand(id, motivo));
    }
}