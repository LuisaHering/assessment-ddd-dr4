package com.infnet.petfriends_pedidos.services;

import com.infnet.petfriends_pedidos.messaging.events.PedidoParaAlmoxarifadoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Wrapper que decide se usa StreamBridge real ou mock
 */
@Service
@Slf4j
public class PedidoEventPublisherWrapper {

    private final StreamBridge streamBridge;
    private final boolean pubsubEnabled;

    public PedidoEventPublisherWrapper(
            StreamBridge streamBridge,
            org.springframework.core.env.Environment env) {
        this.streamBridge = streamBridge;
        this.pubsubEnabled =
                !"false".equals(env.getProperty("spring.cloud.gcp.pubsub.enabled"));
    }

    public void publicarPedidoEnviado(String pedidoId, String clienteId,
                                      BigDecimal valorTotal) {

        PedidoParaAlmoxarifadoEvent evento = new PedidoParaAlmoxarifadoEvent(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                pedidoId,
                clienteId,
                valorTotal
        );

        if (pubsubEnabled) {
            // PRODUÇÃO: Publica no Pub/Sub real
            streamBridge.send("publicarPedidoEnviado-out-0", evento);
            log.info("✅ Evento publicado no Pub/Sub: {}", pedidoId);
        } else {
            // LOCAL: Apenas loga
            log.info("========================================");
            log.info("🧪 SIMULAÇÃO: Evento seria publicado no Pub/Sub");
            log.info("Tópico: petfriends-pedidos-topic");
            log.info("Pedido ID: {}", pedidoId);
            log.info("Cliente ID: {}", clienteId);
            log.info("Valor Total: {}", valorTotal);
            log.info(">>> EM PRODUÇÃO: Este evento seria entregue ao Almoxarifado");
            log.info("========================================");
        }
    }
}