package com.infnet.petfriends_almoxarifado.config;

import com.infnet.petfriends_almoxarifado.events.PedidoParaAlmoxarifadoEvent;
import com.infnet.petfriends_almoxarifado.service.AlmoxarifadoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.function.Consumer;

/**
 * QUESTÃO 9 DO AT - Configuração Spring Cloud Stream
 * Define como receber eventos do tópico Pedidos
 */
@Configuration
@Profile("!local")
@Slf4j
public class StreamConfig {

    /**
     * Bean que consome eventos do tópico petfriends-pedidos-topic
     * Nome do bean deve bater com spring.cloud.function.definition
     */
    @Bean
    public Consumer<PedidoParaAlmoxarifadoEvent> receberPedido(
            AlmoxarifadoService service) {

        return evento -> {
            log.info("=== EVENTO RECEBIDO ===");
            log.info("Tipo: {}", evento.getEventType());
            log.info("Pedido: {}", evento.getPedidoId());

            try {
                service.processarNovoPedido(evento);
            } catch (Exception e) {
                log.error("Erro ao processar pedido: {}", e.getMessage(), e);
            }
        };
    }
}