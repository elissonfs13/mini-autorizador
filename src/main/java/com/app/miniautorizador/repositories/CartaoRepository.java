package com.app.miniautorizador.repositories;

import com.app.miniautorizador.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

  /**
   * Busca por cartão com o número informado.
   *
   * @param numeroCartao número do cartão a ser buscado.
   * @return container que pode conter ou não um cartão resultante da busca.
   */
  Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
