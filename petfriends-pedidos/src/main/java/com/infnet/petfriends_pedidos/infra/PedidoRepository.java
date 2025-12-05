package com.infnet.petfriends_pedidos.infra;

import com.infnet.petfriends_pedidos.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA para persistência do Read Model (estado atual).
 * Separado do Event Store - princípio do CQRS.
 */
public interface PedidoRepository extends JpaRepository<Pedido, String> {
}