package com.infnet.petfriends_almoxarifado.controller;

import com.infnet.petfriends_almoxarifado.events.PedidoParaAlmoxarifadoEvent;
import com.infnet.petfriends_almoxarifado.service.AlmoxarifadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Tag(name = "Testes - Simulacao de Mensageria")
@Slf4j
public class TestController {

    private final AlmoxarifadoService service;

    public TestController(AlmoxarifadoService service) {
        this.service = service;
    }

    @PostMapping("/receber-evento")
    @Operation(summary = "Simula recepcao de evento do Pub/Sub")
    public String receberEvento(@RequestBody PedidoParaAlmoxarifadoEvent evento) {

        log.info("========================================");
        log.info("SIMULACAO: Recebendo evento do Pub/Sub");
        log.info("========================================");
        log.info("Topico: petfriends-pedidos-topic");
        log.info("Subscription: almoxarifado-group");
        log.info("Evento ID: {}", evento.getEventId());
        log.info("Pedido ID: {}", evento.getPedidoId());
        log.info(">>> Em producao, este evento viria automaticamente via Pub/Sub");
        log.info("========================================");

        // Processar como se fosse evento real
        service.processarNovoPedido(evento);

        log.info(">>> PROCESSAMENTO CONCLUIDO");
        log.info("========================================");

        return "Evento recebido e processado! Veja os logs acima.";
    }
}
