package com.infnet.petfriends_pedidos.services;

import com.infnet.petfriends_pedidos.domain.Pedido;
import com.infnet.petfriends_pedidos.infra.PedidoRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoQueryServiceImpl implements PedidoQueryService {

    private final EventStore eventStore;
    private final PedidoRepository pedidoRepository;

    public PedidoQueryServiceImpl(EventStore eventStore,
                                  PedidoRepository pedidoRepository) {
        this.eventStore = eventStore;
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * QUESTÃO 8: Lista TODOS os eventos do agregado específico.
     */
    @Override
    public List<Object> listarEventos(String id) {
        return eventStore.readEvents(id, 0)
                .asStream()
                .map(record -> record.getPayload())
                .collect(Collectors.toList());
    }

    /**
     * QUESTÃO 10: Obtém o estado atual do agregado por ID.
     */
    @Override
    public Optional<Pedido> obterPorId(String id) {
        return pedidoRepository.findById(id);
    }
}