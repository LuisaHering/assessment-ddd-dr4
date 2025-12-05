package com.infnet.petfriends_pedidos.controllers;

import com.infnet.petfriends_pedidos.domain.Pedido;
import com.infnet.petfriends_pedidos.services.PedidoQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
@Tag(name = "Pedidos - Queries", description = "Operações de leitura (Queries)")
public class PedidoQueryController {

    private final PedidoQueryService service;

    public PedidoQueryController(PedidoQueryService service) {
        this.service = service;
    }

    @GetMapping("/{id}/eventos")
    @Operation(summary = "Listar eventos do pedido")
    public List<Object> listarEventos(@PathVariable(value = "id") String id) {
        return service.listarEventos(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter pedido por ID")
    public Pedido obterPorId(@PathVariable(value = "id") String id) {
        return service.obterPorId(id).orElse(null);
    }
}