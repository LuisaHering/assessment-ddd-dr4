package com.infnet.petfriends_pedidos.controllers;

import com.infnet.petfriends_pedidos.domain.Pedido;
import com.infnet.petfriends_pedidos.services.PedidoCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/pedidos")
@Tag(name = "Pedidos - Comandos", description = "Operações de escrita (Commands)")
public class PedidoCommandController {

    private final PedidoCommandService service;

    public PedidoCommandController(PedidoCommandService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar novo pedido")
    public CompletableFuture<String> criarPedido(@RequestBody Pedido pedido) {
        return service.criarPedido(pedido);
    }

    @PutMapping("/{id}/fechar")
    @Operation(summary = "Fechar pedido (confirmar pagamento)")
    public CompletableFuture<Void> fecharPedido(@PathVariable String id) {
        return service.fecharPedido(id);
    }

    @PutMapping("/{id}/enviar")
    @Operation(summary = "Enviar pedido para preparação")
    public CompletableFuture<Void> enviarPedido(@PathVariable String id) {
        return service.enviarPedido(id);
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido")
    public CompletableFuture<Void> cancelarPedido(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        String motivo = body.getOrDefault("motivo", "Não informado");
        return service.cancelarPedido(id, motivo);
    }
}