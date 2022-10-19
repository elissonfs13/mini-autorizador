package com.app.miniautorizador.integration;

import com.app.miniautorizador.MiniAutorizadorApplication;
import com.app.miniautorizador.controllers.TransacaoController;
import com.app.miniautorizador.controllers.dtos.CartaoDTO;
import com.app.miniautorizador.controllers.dtos.TransacaoDTO;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiniAutorizadorApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransacaoControllerIntegrationTest {

  private static String OK = "OK";

  @Autowired private TransacaoController controller;

  @Autowired private CartaoService cartaoService;

  @Test
  public void estagio01_processaTransacaoOk() {
    String numCartao = "6549873035634501";
    String senhaCartao = "1234";
    TransacaoDTO transacaoDTO = getTransacaoDTO(numCartao, senhaCartao, 100);
    CartaoDTO cartaoDTO = criaCartaoDTO(numCartao, senhaCartao);
    cartaoService.criaCartao(cartaoDTO);

    ResponseEntity<String> response = controller.processaTransacao(transacaoDTO);
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(OK, response.getBody());
  }

  private CartaoDTO criaCartaoDTO(String numCartao, String senhaCartao) {
    return CartaoDTO.builder().numeroCartao(numCartao).senha(senhaCartao).build();
  }

  private TransacaoDTO getTransacaoDTO(String numCartao, String senhaCartao, int value) {
    return TransacaoDTO.builder()
        .numeroCartao(numCartao)
        .senhaCartao(senhaCartao)
        .valor(new BigDecimal(value))
        .build();
  }
}
