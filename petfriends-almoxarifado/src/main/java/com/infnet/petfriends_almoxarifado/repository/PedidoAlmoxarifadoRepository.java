package com.infnet.petfriends_almoxarifado.repository;

import com.infnet.petfriends_almoxarifado.domain.PedidoAlmoxarifado;
import com.infnet.petfriends_almoxarifado.domain.StatusAlmoxarifado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * QUESTÃO 1 DO AT - Repository do Almoxarifado
 */
@Repository
public interface PedidoAlmoxarifadoRepository
        extends JpaRepository<PedidoAlmoxarifado, String> {

    Optional<PedidoAlmoxarifado> findByPedidoId(String pedidoId);

    List<PedidoAlmoxarifado> findByStatus(StatusAlmoxarifado status);
}
