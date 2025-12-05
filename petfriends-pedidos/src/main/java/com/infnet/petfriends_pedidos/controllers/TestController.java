package com.infnet.petfriends_pedidos.controllers;

import com.infnet.petfriends_pedidos.messaging.events.PedidoParaAlmoxarifadoEvent;
import com.infnet.petfriends_pedidos.services.PedidoEventPublisher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/test")
@Tag(name = "Testes - Simulacao de Mensageria")
@Slf4j
public class TestController {

    private final PedidoEventPublisher publisher;

    public TestController(PedidoEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/simular-publicacao")
    @Operation(summary = "Simula publicacao de evento no Pub/Sub")
    public String simularPublicacao(
            @RequestParam String pedidoId,
            @RequestParam String clienteId,
            @RequestParam BigDecimal valorTotal) {

        log.info("========================================");
        log.info("SIMULACAO: Publicando evento no Pub/Sub");
        log.info("========================================");
        log.info("Pedido ID: {}", pedidoId);
        log.info("Cliente ID: {}", clienteId);
        log.info("Valor Total: {}", valorTotal);

        // Simula publicacao (na verdade so loga)
        publisher.publicarPedidoEnviado(pedidoId, clienteId, valorTotal);

        log.info(">>> EVENTO PUBLICADO NO TOPICO: petfriends-pedidos-topic");
        log.info(">>> Em producao, este evento seria entregue ao Almoxarifado via Pub/Sub");
        log.info("========================================");

        return "Evento publicado! Veja os logs acima.";
    }
}