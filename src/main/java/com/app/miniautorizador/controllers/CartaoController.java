package com.app.miniautorizador.controllers;

import com.app.miniautorizador.controllers.dtos.CartaoDTO;
import com.app.miniautorizador.services.CartaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cartoes")
public class CartaoController {

  private CartaoService cartaoService;

  @Autowired
  public CartaoController(CartaoService cartaoService) {
    this.cartaoService = cartaoService;
  }

  @PostMapping
  public ResponseEntity<CartaoDTO> criarCartao(@RequestBody CartaoDTO novoCartao) {

    try {
      cartaoService.save(novoCartao);
      log.info("Cartão criado com sucesso!");
      return ResponseEntity.status(HttpStatus.CREATED).body(novoCartao);
    } catch(DataIntegrityViolationException e) {
      log.info("Cartão já cadastrado!");
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(novoCartao);
    }
  }
}
