package com.infnet.petfriends_almoxarifado.service;

import com.infnet.petfriends_almoxarifado.domain.PedidoAlmoxarifado;
import com.infnet.petfriends_almoxarifado.events.PedidoEmPreparacaoEvent;
import com.infnet.petfriends_almoxarifado.events.PedidoParaAlmoxarifadoEvent;
import com.infnet.petfriends_almoxarifado.repository.PedidoAlmoxarifadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * QUESTÃO 10 DO AT - Serviço que recebe eventos de Pedidos
 * e processa no contexto do Almoxarifado
 */
@Service
@Slf4j
public class AlmoxarifadoService {

    private final PedidoAlmoxarifadoRepository repository;
    private final StreamBridge streamBridge;
    private final boolean pubsubEnabled; // Controle para saber se estamos em Local ou Prod

    public AlmoxarifadoService(PedidoAlmoxarifadoRepository repository,
                               StreamBridge streamBridge,
                               Environment env) {
        this.repository = repository;
        this.streamBridge = streamBridge;
        // Verifica se o PubSub está ligado no application.yml (evita erro no profile local)
        this.pubsubEnabled = !"false".equals(env.getProperty("spring.cloud.gcp.pubsub.enabled"));
    }

    /**
     * Processa novo pedido recebido do microsserviço Pedidos
     */
    @Transactional
    public void processarNovoPedido(PedidoParaAlmoxarifadoEvent evento) {

        log.info("Recebendo pedido: {}", evento.getPedidoId());

        // Converter evento para modelo interno (camada anticorrupção)
        PedidoAlmoxarifado pedido = PedidoAlmoxarifado.criar(
                evento.getPedidoId(),
                evento.getClienteId(),
                evento.getValorTotal()
        );

        // Salvar
        repository.save(pedido);
        log.info("Pedido {} salvo com status: {}",
                pedido.getPedidoId(), pedido.getStatus());

        // Iniciar preparação automaticamente
        pedido.iniciarPreparacao();
        repository.save(pedido);

        log.info("Pedido {} iniciou preparação", pedido.getPedidoId());

        // Publicar evento de mudança de estado
        PedidoEmPreparacaoEvent eventoPreparacao = new PedidoEmPreparacaoEvent(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "PedidoEmPreparacao",
                pedido.getPedidoId(),
                pedido.getId()
        );

        // Decisão: Enviar Real ou Simular?
        if (pubsubEnabled) {
            streamBridge.send("publicarEvento-out-0", eventoPreparacao);
            log.info("Evento publicado: Pedido {} em preparação", pedido.getPedidoId());
        } else {
            log.info("========================================");
            log.info("🧪 SIMULACAO: Evento seria enviado ao Pub/Sub (Profile Local)");
            log.info("Tópico: petfriends-almoxarifado-topic");
            log.info("Payload: {}", eventoPreparacao);
            log.info("========================================");
        }
    }
}