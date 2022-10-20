package com.app.miniautorizador.controllers;

import com.app.miniautorizador.controllers.dtos.TransacaoDTO;
import com.app.miniautorizador.services.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

  private static String OK = "OK";

  private TransacaoService transacaoService;

  @Autowired
  public TransacaoController(TransacaoService transacaoService) {
    this.transacaoService = transacaoService;
  }

  @Operation(summary = "Realiza nova transação")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Transação processada com sucesso"),
      @ApiResponse(responseCode = "422", description = "Transação não validada")
  })
  @PostMapping
  public ResponseEntity<String> processaTransacao(@RequestBody TransacaoDTO transacao) {

    transacaoService.processaTransacao(transacao);
    log.info("Transação realizada com sucesso!");
    return ResponseEntity.status(HttpStatus.CREATED).body(OK);
  }
}
