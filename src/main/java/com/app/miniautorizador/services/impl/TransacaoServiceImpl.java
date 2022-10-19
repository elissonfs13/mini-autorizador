package com.app.miniautorizador.services.impl;

import com.app.miniautorizador.controllers.dtos.TransacaoDTO;
import com.app.miniautorizador.domain.Cartao;
import com.app.miniautorizador.services.CartaoService;
import com.app.miniautorizador.services.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TransacaoServiceImpl implements TransacaoService {

  private CartaoService cartaoService;

  @Autowired
  public TransacaoServiceImpl(CartaoService cartaoService) {
    this.cartaoService = cartaoService;
  }

  @Override
  @Transactional
  public void processaTransacao(TransacaoDTO transacao) {
    Optional<Cartao> optCartao = cartaoService.findByNumeroCartao(transacao.getNumeroCartao());
    Cartao cartao = optCartao.orElseThrow();
    cartao.validaSenha(transacao.getSenhaCartao());
    cartao.realizaTrasacao(transacao.getValor());
    cartaoService.atualizaCartao(cartao);
  }
}
