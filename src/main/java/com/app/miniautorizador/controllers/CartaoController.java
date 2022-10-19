package com.app.miniautorizador.controllers;

import com.app.miniautorizador.controllers.dtos.CartaoDTO;
import com.app.miniautorizador.domain.Cartao;
import com.app.miniautorizador.services.CartaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

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
      cartaoService.criaCartao(novoCartao);
      log.info("Cartão {} criado com sucesso!", novoCartao.getNumeroCartao());
      return ResponseEntity.status(HttpStatus.CREATED).body(novoCartao);
    } catch (DataIntegrityViolationException e) {
      log.info("Cartão {} já cadastrado!", novoCartao.getNumeroCartao());
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(novoCartao);
    }
  }

  @GetMapping("/{numeroCartao}")
  public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {
    Optional<Cartao> optCartao = cartaoService.findByNumeroCartao(numeroCartao);
    log.info("Consulta de saldo do cartão: {}", numeroCartao);
    return optCartao.isPresent()
        ? ResponseEntity.status(HttpStatus.OK).body(optCartao.get().getSaldo())
        : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
