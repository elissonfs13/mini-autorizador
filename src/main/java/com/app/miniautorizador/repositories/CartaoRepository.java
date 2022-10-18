package com.app.miniautorizador.repositories;

import com.app.miniautorizador.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

  Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
