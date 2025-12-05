package com.infnet.petfriends_pedidos.listeners;

import com.infnet.petfriends_pedidos.events.PedidoEnviadoEvent;
import com.infnet.petfriends_pedidos.services.PedidoEventPublisherWrapper;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * Escuta eventos do Axon e publica para outros microsservicos
 */
@Component
@Slf4j
public class PedidoEventListener {

    private final PedidoEventPublisherWrapper publisher;

    public PedidoEventListener(PedidoEventPublisherWrapper publisher) {
        this.publisher = publisher;
    }

    /**
     * Quando Axon dispara PedidoEnviadoEvent,
     * este metodo publica evento para o Almoxarifado via Pub/Sub
     */
    @EventHandler
    public void on(PedidoEnviadoEvent evento) {
        log.info("========================================");
        log.info("EVENT LISTENER DETECTOU: PedidoEnviadoEvent");
        log.info("Pedido ID: {}", evento.id);
        log.info("Cliente ID: {}", evento.clienteId);
        log.info(">>> Publicando para Almoxarifado via Pub/Sub...");

        // Buscar dados do pedido (simplificado - posso injetar repository)
        // Aqui so temos clienteId do evento
        // MOCK: Usando valor fixo de 100.00 - idealmente buscar do BD
        publisher.publicarPedidoEnviado(
                evento.id,
                evento.clienteId,
                java.math.BigDecimal.valueOf(100.00) // Mock - idealmente buscar do BD
        );

        log.info("========================================");
    }
}