package com.infnet.petfriends_transporte.controller;

import com.infnet.petfriends_transporte.events.PedidoEmPreparacaoEvent;
import com.infnet.petfriends_transporte.service.TransporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Tag(name = "Testes - Simulacao de Mensageria")
@Slf4j
public class TestController {

    private final TransporteService service;

    public TestController(TransporteService service) {
        this.service = service;
    }

    @PostMapping("/receber-evento")
    @Operation(summary = "Simula recepcao de evento do Pub/Sub")
    public String receberEvento(@RequestBody PedidoEmPreparacaoEvent evento) {

        log.info("========================================");
        log.info("SIMULACAO: Recebendo evento do Pub/Sub");
        log.info("========================================");
        log.info("Topico: petfriends-almoxarifado-topic");
        log.info("Subscription: transporte-group");
        log.info("Evento ID: {}", evento.getEventId());
        log.info("Pedido ID: {}", evento.getPedidoId());
        log.info(">>> Em producao, este evento viria automaticamente via Pub/Sub");
        log.info("========================================");

        // Processar como se fosse evento real
        service.processarNovaEntrega(evento);

        log.info(">>> PROCESSAMENTO CONCLUIDO");
        log.info("========================================");

        return "Evento recebido e processado! Veja os logs acima.";
    }
}