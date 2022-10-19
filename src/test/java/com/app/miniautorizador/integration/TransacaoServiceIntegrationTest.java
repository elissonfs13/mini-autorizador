package com.app.miniautorizador.integration;

import com.app.miniautorizador.MiniAutorizadorApplication;
import com.app.miniautorizador.controllers.dtos.CartaoDTO;
import com.app.miniautorizador.controllers.dtos.TransacaoDTO;
import com.app.miniautorizador.domain.Cartao;
import com.app.miniautorizador.exceptions.SenhaIncorretaException;
import com.app.miniautorizador.services.CartaoService;
import com.app.miniautorizador.services.TransacaoService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiniAutorizadorApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransacaoServiceIntegrationTest {

  @Autowired private TransacaoService transacaoService;

  @Autowired private CartaoService cartaoService;

  @Test
  public void estagio01_processaTransacaoOk() {
    String numCartao = "6549873025634501";
    String senhaCartao = "1234";
    TransacaoDTO transacaoMocada = getTransacaoMocada(numCartao, senhaCartao, 100);
    CartaoDTO novoCartaoMocado = criaCartaoMocado(numCartao, senhaCartao);
    cartaoService.criaCartao(novoCartaoMocado);

    Optional<Cartao> optCartaoRetornadoPrimeiro =
        cartaoService.findByNumeroCartao(novoCartaoMocado.getNumeroCartao());
    assertNotNull(optCartaoRetornadoPrimeiro);
    assertTrue(optCartaoRetornadoPrimeiro.isPresent());
    assertEquals(new BigDecimal(500).setScale(2), optCartaoRetornadoPrimeiro.get().getSaldo());

    transacaoService.processaTransacao(transacaoMocada);
    Optional<Cartao> optCartaoRetornadoSegundo =
        cartaoService.findByNumeroCartao(novoCartaoMocado.getNumeroCartao());
    assertNotNull(optCartaoRetornadoSegundo);
    assertTrue(optCartaoRetornadoSegundo.isPresent());
    assertEquals(new BigDecimal(400).setScale(2), optCartaoRetornadoSegundo.get().getSaldo());
  }

  @Test(expected = NoSuchElementException.class)
  public void estagio02_tentaProcessarTransacaoComCartaoInexistente() {
    String numCartao = "6549873025634502";
    String senhaCartao = "1234";
    TransacaoDTO transacaoMocada = getTransacaoMocada(numCartao, senhaCartao, 100);
    transacaoService.processaTransacao(transacaoMocada);
  }

  @Test(expected = SenhaIncorretaException.class)
  public void estagio03_tentaProcessarTransacaoComSenhaDoCartaoIncorreta() {
    String senhaCartaoCorreta = "1234";
    String senhaCartaoIncorreta = "1245";
    String numCartao = "6549873025634503";
    TransacaoDTO transacaoMocada = getTransacaoMocada(numCartao, senhaCartaoIncorreta, 100);
    CartaoDTO novoCartaoMocado = criaCartaoMocado(numCartao, senhaCartaoCorreta);
    cartaoService.criaCartao(novoCartaoMocado);

    Optional<Cartao> optCartaoRetornadoPrimeiro =
        cartaoService.findByNumeroCartao(novoCartaoMocado.getNumeroCartao());
    assertNotNull(optCartaoRetornadoPrimeiro);
    assertTrue(optCartaoRetornadoPrimeiro.isPresent());
    assertEquals(new BigDecimal(500).setScale(2), optCartaoRetornadoPrimeiro.get().getSaldo());

    transacaoService.processaTransacao(transacaoMocada);
  }

  @Test(expected = TransactionSystemException.class)
  public void estagio04_tentaProcessarTransacaoEmCartaoComSaldoInsuficiente() {
    String numCartao = "6549873025634504";
    String senhaCartao = "1234";
    TransacaoDTO transacaoMocada = getTransacaoMocada(numCartao, senhaCartao, 600);
    CartaoDTO novoCartaoMocado = criaCartaoMocado(numCartao, senhaCartao);
    cartaoService.criaCartao(novoCartaoMocado);

    Optional<Cartao> optCartaoRetornadoPrimeiro =
        cartaoService.findByNumeroCartao(novoCartaoMocado.getNumeroCartao());
    assertNotNull(optCartaoRetornadoPrimeiro);
    assertTrue(optCartaoRetornadoPrimeiro.isPresent());
    assertEquals(new BigDecimal(500).setScale(2), optCartaoRetornadoPrimeiro.get().getSaldo());

    transacaoService.processaTransacao(transacaoMocada);
  }

  private CartaoDTO criaCartaoMocado(String numCartao, String senhaCartao) {
    return CartaoDTO.builder().numeroCartao(numCartao).senha(senhaCartao).build();
  }

  private TransacaoDTO getTransacaoMocada(String numCartao, String senhaCartao, int value) {
    return TransacaoDTO.builder()
        .numeroCartao(numCartao)
        .senhaCartao(senhaCartao)
        .valor(new BigDecimal(value))
        .build();
  }
}
