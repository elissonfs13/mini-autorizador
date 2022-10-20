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
  public TransacaoServiceImpl(final CartaoService cartaoService) {
    this.cartaoService = cartaoService;
  }

  /**
   * Método responsável por processar uma nova transação e realizar as validações necessárias antes
   * da realização da transação e consequente atualização na base de dados. As validações são
   * realizadas por meio do lançamento e captura de exceções provenientes do processamento de cada
   * passo da transação.
   *
   * Validações:
   * - Cartão existir na base de dados;
   * - Senha do cartão informada corretamente;
   * - Saldo disponível e suficiente para a transação.
   *
   * @param transacao informações de entrada para a transação
   */
  @Override
  @Transactional
  public void processaTransacao(final TransacaoDTO transacao) {
    Optional<Cartao> optCartao = cartaoService.findByNumeroCartao(transacao.getNumeroCartao());
    Cartao cartao = optCartao.orElseThrow();
    cartao.validaSenha(transacao.getSenhaCartao());
    cartao.realizaTrasacao(transacao.getValor());
    cartaoService.atualizaCartao(cartao);
  }
}
