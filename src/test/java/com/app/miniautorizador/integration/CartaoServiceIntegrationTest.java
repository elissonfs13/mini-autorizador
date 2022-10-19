package com.app.miniautorizador.integration;

import com.app.miniautorizador.MiniAutorizadorApplication;
import com.app.miniautorizador.controllers.dtos.CartaoDTO;
import com.app.miniautorizador.domain.Cartao;
import com.app.miniautorizador.services.CartaoService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiniAutorizadorApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartaoServiceIntegrationTest {

  @Autowired private CartaoService cartaoService;

  @Test
  public void estagio01_buscaCartaoInexistente() {
    String numeroCartao = "6549873025734500";
    Optional<Cartao> optCartaoRetornado = cartaoService.findByNumeroCartao(numeroCartao);
    assertNotNull(optCartaoRetornado);
    assertFalse(optCartaoRetornado.isPresent());
  }

  @Test
  public void estagio02_salvaCartaoEBuscaCartaoCadastrado() {
    String numCartao = "6549873025734501";
    String senhaCartao = "1234";
    CartaoDTO novoCartaoMocado = criaCartaoMocado(numCartao, senhaCartao);
    cartaoService.criaCartao(novoCartaoMocado);

    Optional<Cartao> optCartaoRetornado =
        cartaoService.findByNumeroCartao(novoCartaoMocado.getNumeroCartao());
    assertNotNull(optCartaoRetornado);
    assertTrue(optCartaoRetornado.isPresent());
    assertEquals(numCartao, optCartaoRetornado.get().getNumeroCartao());
    assertEquals(new BigDecimal(500).setScale(2), optCartaoRetornado.get().getSaldo());
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void estagio03_tentaSalvarCartaoJaCadastrado() {
    String numCartao = "6549873025734501";
    String senhaCartao = "1234";
    CartaoDTO novoCartaoMocado = criaCartaoMocado(numCartao, senhaCartao);
    cartaoService.criaCartao(novoCartaoMocado);
  }

  private CartaoDTO criaCartaoMocado(String numCartao, String senhaCartao) {
    return CartaoDTO.builder().numeroCartao(numCartao).senha(senhaCartao).build();
  }
}
