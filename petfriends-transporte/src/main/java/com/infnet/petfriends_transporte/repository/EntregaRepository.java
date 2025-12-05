package com.infnet.petfriends_transporte.repository;

import com.infnet.petfriends_transporte.domain.Entrega;
import com.infnet.petfriends_transporte.domain.StatusEntrega;
import com.infnet.petfriends_transporte.domain.vo.CodigoRastreio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * QUESTAO 3 DO AT - Repository do Transporte
 */
@Repository
public interface EntregaRepository extends JpaRepository<Entrega, String> {

    Optional<Entrega> findByPedidoId(String pedidoId);

    List<Entrega> findByStatus(StatusEntrega status);

    Optional<Entrega> findByCodigoRastreio(CodigoRastreio codigo);
}
