package com.app.miniautorizador.services;

import com.app.miniautorizador.controllers.dtos.CartaoDTO;
import com.app.miniautorizador.domain.Cartao;

import java.util.Optional;

public interface CartaoService {

  void criaCartao(CartaoDTO newartao);

  Optional<Cartao> findByNumeroCartao(String numeroCartao);

  void atualizaCartao(Cartao cartao);
}
