package com.infnet.petfriends_pedidos.services;

import com.infnet.petfriends_pedidos.domain.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoQueryService {
    List<Object> listarEventos(String id);
    Optional<Pedido> obterPorId(String id);
}