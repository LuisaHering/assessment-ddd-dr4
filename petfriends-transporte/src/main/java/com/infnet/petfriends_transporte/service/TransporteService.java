package com.infnet.petfriends_transporte.service;

import com.infnet.petfriends_transporte.domain.Entrega;
import com.infnet.petfriends_transporte.events.EntregaCriadaEvent;
import com.infnet.petfriends_transporte.events.PedidoEmPreparacaoEvent;
import com.infnet.petfriends_transporte.repository.EntregaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * QUESTAO 12 DO AT - Servico que recebe eventos do Almoxarifado
 * e processa no contexto de Transporte
 */
@Service
@Slf4j
public class TransporteService {

    private final EntregaRepository repository;
    private final StreamBridge streamBridge;
    private final boolean pubsubEnabled; // Controle para saber se estamos em Local ou Prod

    public TransporteService(EntregaRepository repository,
                             StreamBridge streamBridge,
                             Environment env) {
        this.repository = repository;
        this.streamBridge = streamBridge;
        // Verifica se o PubSub está ligado no application.yml
        this.pubsubEnabled = !"false".equals(env.getProperty("spring.cloud.gcp.pubsub.enabled"));
    }

    /**
     * Processa pedido recebido do Almoxarifado
     */
    @Transactional
    public void processarNovaEntrega(PedidoEmPreparacaoEvent evento) {

        log.info("Recebendo pedido para entrega: {}", evento.getPedidoId());

        // Converter evento para modelo interno (camada anticorrupcao)
        Entrega entrega = Entrega.criar(
                evento.getPedidoId(),
                evento.getAlmoxarifadoId()
        );

        // Salvar
        repository.save(entrega);
        log.info("Entrega {} criada com status: {}",
                entrega.getId(), entrega.getStatus());
        log.info("Codigo de rastreio: {}",
                entrega.getCodigoRastreio().getCodigo());

        // Publicar evento com codigo de rastreio
        EntregaCriadaEvent eventoEntrega = new EntregaCriadaEvent(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "EntregaCriada",
                entrega.getPedidoId(),
                entrega.getId(),
                entrega.getCodigoRastreio().getCodigo()
        );

        // Decisão: Enviar Real ou Simular?
        if (pubsubEnabled) {
            streamBridge.send("publicarEvento-out-0", eventoEntrega);
            log.info("Evento publicado: Entrega {} criada", entrega.getId());
        } else {
            log.info("========================================");
            log.info("🧪 SIMULACAO: Evento seria enviado ao Pub/Sub (Profile Local)");
            log.info("Tópico: petfriends-transporte-topic");
            log.info("Rastreio: {}", entrega.getCodigoRastreio().getCodigo());
            log.info("========================================");
        }
    }

    /**
     * Inicia transporte da entrega
     */
    @Transactional
    public void iniciarTransporte(String pedidoId) {
        Entrega entrega = repository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Entrega nao encontrada para pedido: " + pedidoId));

        entrega.iniciarTransporte();
        repository.save(entrega);

        log.info("Transporte iniciado para pedido: {}", pedidoId);
    }
}