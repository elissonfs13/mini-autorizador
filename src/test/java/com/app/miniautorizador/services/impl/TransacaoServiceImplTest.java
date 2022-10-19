package com.app.miniautorizador.services.impl;

import com.app.miniautorizador.controllers.dtos.TransacaoDTO;
import com.app.miniautorizador.domain.Cartao;
import com.app.miniautorizador.exceptions.SenhaIncorretaException;
import com.app.miniautorizador.services.CartaoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransacaoServiceImplTest {

  @InjectMocks private TransacaoServiceImpl transacaoService;

  @Mock private CartaoService cartaoService;

  private Validator validator;

  @Before
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void processaTransacaoTest() {
    String numeroCartao = "6549873025634599";
    String senhaCartao = "1234";
    TransacaoDTO transacaoMocada = getTransacaoMocada(numeroCartao, senhaCartao, 100);
    when(cartaoService.findByNumeroCartao(Mockito.anyString()))
        .thenReturn(getOptionalCartao(numeroCartao, senhaCartao));
    transacaoService.processaTransacao(transacaoMocada);
    verify(cartaoService, times(1)).atualizaCartao(Mockito.any(Cartao.class));
  }

  @Test(expected = NoSuchElementException.class)
  public void processaTransacaoComCartaoInexistenteTest() {
    String numeroCartao = "6549873025634599";
    String senhaCartao = "1234";
    TransacaoDTO transacaoMocada = getTransacaoMocada(numeroCartao, senhaCartao, 100);
    when(cartaoService.findByNumeroCartao(Mockito.anyString())).thenReturn(Optional.empty());
    transacaoService.processaTransacao(transacaoMocada);
    verify(cartaoService, times(0)).atualizaCartao(Mockito.any(Cartao.class));
  }

  @Test(expected = SenhaIncorretaException.class)
  public void processaTransacaoComSenhaIncorretaTest() {
    String numeroCartao = "6549873025634599";
    String senhaCartaoCorreta = "1234";
    String senhaCartaoIncorreta = "1245";
    TransacaoDTO transacaoMocada = getTransacaoMocada(numeroCartao, senhaCartaoIncorreta, 100);
    when(cartaoService.findByNumeroCartao(Mockito.anyString()))
        .thenReturn(getOptionalCartao(numeroCartao, senhaCartaoCorreta));
    transacaoService.processaTransacao(transacaoMocada);
    verify(cartaoService, times(0)).atualizaCartao(Mockito.any(Cartao.class));
  }

  @Test
  public void processaTransacaoComSaldoInsuficienteTest() {
    String numeroCartao = "6549873025634599";
    String senhaCartao = "1234";
    TransacaoDTO transacaoMocada = getTransacaoMocada(numeroCartao, senhaCartao, 600);
    Optional<Cartao> optionalCartao = getOptionalCartao(numeroCartao, senhaCartao);
    when(cartaoService.findByNumeroCartao(Mockito.anyString())).thenReturn(optionalCartao);
    transacaoService.processaTransacao(transacaoMocada);
    Set<ConstraintViolation<Cartao>> violations = validator.validate(optionalCartao.get());
    assertFalse(violations.isEmpty());
    assertEquals(1, violations.size());
  }

  private Optional<Cartao> getOptionalCartao(String numeroCartao, String senha) {
    Cartao cartao = new Cartao(numeroCartao, senha);
    return Optional.of(cartao);
  }

  private TransacaoDTO getTransacaoMocada(String numeroCartao, String senhaCartao, int valor) {
    return TransacaoDTO.builder()
        .numeroCartao(numeroCartao)
        .senhaCartao(senhaCartao)
        .valor(new BigDecimal(valor))
        .build();
  }
}
