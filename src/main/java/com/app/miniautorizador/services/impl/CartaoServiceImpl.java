package com.app.miniautorizador.services.impl;

import com.app.miniautorizador.controllers.dtos.CartaoDTO;
import com.app.miniautorizador.domain.Cartao;
import com.app.miniautorizador.repositories.CartaoRepository;
import com.app.miniautorizador.services.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoServiceImpl implements CartaoService {

  private CartaoRepository repository;

  @Autowired
  public CartaoServiceImpl(final CartaoRepository repository) {
    this.repository = repository;
  }

  @Override
  public void criaCartao(final CartaoDTO novoCartao) {
    final Cartao cartao = new Cartao(novoCartao.getNumeroCartao(), novoCartao.getSenha());
    repository.save(cartao);
  }

  @Override
  public Optional<Cartao> findByNumeroCartao(final String numeroCartao) {
    return repository.findByNumeroCartao(numeroCartao);
  }

  @Override
  public void atualizaCartao(final Cartao cartao) {
    repository.save(cartao);
  }
}
