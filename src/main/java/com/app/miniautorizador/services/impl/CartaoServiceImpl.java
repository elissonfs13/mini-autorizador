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
  public CartaoServiceImpl(CartaoRepository repository) {
    this.repository = repository;
  }

  @Override
  public void save(CartaoDTO newCartao) {
    Cartao cartao = new Cartao(newCartao.getNumeroCartao(), newCartao.getSenha());
    repository.save(cartao);
  }

  @Override
  public Optional<Cartao> obterSaldo(String numeroCartao) {
    return repository.findByNumeroCartao(numeroCartao);
  }
}
