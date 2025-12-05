package com.infnet.petfriends_pedidos.services;

import com.infnet.petfriends_pedidos.messaging.events.PedidoParaAlmoxarifadoEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * COMPETÊNCIA 3 - Serviço responsável por publicar eventos
 * Usa Spring Cloud Stream (abstração do Message Broker)
 */
@Service
public class PedidoEventPublisher {

    private final StreamBridge streamBridge;

    public PedidoEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    /**
     * Publica evento quando pedido é enviado ao almoxarifado
     */
    public void publicarPedidoEnviado(String pedidoId, String clienteId,
                                      java.math.BigDecimal valorTotal) {

        // Criar evento
        PedidoParaAlmoxarifadoEvent evento = new PedidoParaAlmoxarifadoEvent(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                pedidoId,
                clienteId,
                valorTotal
        );

        // Publicar no tópico
        streamBridge.send("publicarPedidoEnviado-out-0", evento);

        System.out.println("Evento publicado: Pedido " + pedidoId +
                " enviado ao almoxarifado");
    }
}