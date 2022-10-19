package com.app.miniautorizador.services.impl;

import com.app.miniautorizador.controllers.dtos.CartaoDTO;
import com.app.miniautorizador.domain.Cartao;
import com.app.miniautorizador.repositories.CartaoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CartaoServiceImplTest {

  @InjectMocks private CartaoServiceImpl cartaoService;

  @Mock private CartaoRepository repository;

  @Test
  public void criaCartaoTest() {
    CartaoDTO novoCartaoMocado = criaCartaoMocado();
    cartaoService.criaCartao(novoCartaoMocado);
    verify(repository, times(1)).save(Mockito.any(Cartao.class));
  }

  @Test
  public void atualizaCartaoTest() {
    CartaoDTO novoCartaoMocado = criaCartaoMocado();
    Cartao cartaoMocado =
        new Cartao(novoCartaoMocado.getNumeroCartao(), novoCartaoMocado.getSenha());
    assertEquals(new BigDecimal(500).setScale(2), cartaoMocado.getSaldo());

    cartaoMocado.realizaTrasacao(new BigDecimal(125.35));
    assertEquals(new BigDecimal(374.65).setScale(2, RoundingMode.HALF_UP), cartaoMocado.getSaldo());

    cartaoService.atualizaCartao(cartaoMocado);
    verify(repository, times(1)).save(Mockito.any(Cartao.class));
  }

  @Test
  public void obterSaldoTest() {
    cartaoService.findByNumeroCartao(Mockito.anyString());
    verify(repository, times(1)).findByNumeroCartao(Mockito.anyString());
  }

  private CartaoDTO criaCartaoMocado() {
    return CartaoDTO.builder().numeroCartao("6549873025634501").senha("1234").build();
  }
}
