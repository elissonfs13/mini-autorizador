package com.app.miniautorizador.integration;

import com.app.miniautorizador.MiniAutorizadorApplication;
import com.app.miniautorizador.controllers.CartaoController;
import com.app.miniautorizador.controllers.dtos.CartaoDTO;
import com.app.miniautorizador.services.CartaoService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiniAutorizadorApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartaoControllerIntegrationTest {

  @Autowired private CartaoController controller;

  @Autowired private CartaoService cartaoService;

  @Test
  public void estagio01_obterSaldoCartaoInexistente() {
    String numeroCartao = "6549473025734500";
    ResponseEntity<BigDecimal> response = controller.obterSaldo(numeroCartao);
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
  public void estagio02_obterSaldoCartaoExistente() {
    String numeroCartao = "6549473025734501";
    String senhaCartao = "1234";
    CartaoDTO novoCartaoDTO = criaCartaoDTO(numeroCartao, senhaCartao);
    cartaoService.criaCartao(novoCartaoDTO);

    ResponseEntity<BigDecimal> response = controller.obterSaldo(numeroCartao);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(new BigDecimal(500).setScale(2), response.getBody());
  }

  @Test
  public void estagio03_criarNovoCartao() {
    String numeroCartao = "6549473025734502";
    String senhaCartao = "1234";
    CartaoDTO novoCartaoDTO = criaCartaoDTO(numeroCartao, senhaCartao);

    ResponseEntity<CartaoDTO> response = controller.criarCartao(novoCartaoDTO);
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(numeroCartao, response.getBody().getNumeroCartao());
  }

  @Test
  public void estagio03_criarCartaoExistente() {
    String numeroCartao = "6549473025734503";
    String senhaCartao = "1234";
    CartaoDTO novoCartaoDTO = criaCartaoDTO(numeroCartao, senhaCartao);

    cartaoService.criaCartao(novoCartaoDTO);

    ResponseEntity<CartaoDTO> response = controller.criarCartao(novoCartaoDTO);
    assertNotNull(response);
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(numeroCartao, response.getBody().getNumeroCartao());
  }

  private CartaoDTO criaCartaoDTO(String numCartao, String senhaCartao) {
    return CartaoDTO.builder().numeroCartao(numCartao).senha(senhaCartao).build();
  }
}
