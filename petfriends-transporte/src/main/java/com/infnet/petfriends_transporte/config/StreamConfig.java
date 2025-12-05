package com.infnet.petfriends_transporte.config;

import com.infnet.petfriends_transporte.events.PedidoEmPreparacaoEvent;
import com.infnet.petfriends_transporte.service.TransporteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.function.Consumer;

/**
 * QUESTAO 11 DO AT - Configuracao Spring Cloud Stream
 * Define como receber eventos do topico Almoxarifado
 */
@Configuration
@Profile("!local")
@Slf4j
public class StreamConfig {

    /**
     * Bean que consome eventos do topico petfriends-almoxarifado-topic
     */
    @Bean
    public Consumer<PedidoEmPreparacaoEvent> receberPedido(
            TransporteService service) {

        return evento -> {
            log.info("=== EVENTO RECEBIDO ===");
            log.info("Tipo: {}", evento.getEventType());
            log.info("Pedido: {}", evento.getPedidoId());

            try {
                service.processarNovaEntrega(evento);
            } catch (Exception e) {
                log.error("Erro ao processar entrega: {}", e.getMessage(), e);
            }
        };
    }
}
