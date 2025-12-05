package com.infnet.petfriends_pedidos.services;

import com.infnet.petfriends_pedidos.domain.Pedido;
import java.util.concurrent.CompletableFuture;

/**
 * Interface do serviço de comandos.
 * Define operações que modificam o estado do agregado Pedido.
 */

public interface PedidoCommandService {
    CompletableFuture<String> criarPedido(Pedido pedido);
    CompletableFuture<Void> fecharPedido(String id);
    CompletableFuture<Void> enviarPedido(String id);
    CompletableFuture<Void> cancelarPedido(String id, String motivo);
}