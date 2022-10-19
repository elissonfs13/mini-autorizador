package com.app.miniautorizador.controllers;

import com.app.miniautorizador.exceptions.SenhaIncorretaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {

  private static String CARTAO_INEXISTENTE = "CARTAO_INEXISTENTE";
  private static String SALDO_INSUFICIENTE = "SALDO_INSUFICIENTE";
  private static String SENHA_INVALIDA = "SENHA_INVALIDA";

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
    log.info("Transação não realizada: " + SALDO_INSUFICIENTE);
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(SALDO_INSUFICIENTE);
  }

  @ExceptionHandler({NoSuchElementException.class})
  public ResponseEntity<String> handleNoSuchElement(NoSuchElementException ex) {
    log.info("Transação não realizada: " + CARTAO_INEXISTENTE);
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(CARTAO_INEXISTENTE);
  }

  @ExceptionHandler({SenhaIncorretaException.class})
  public ResponseEntity<String> handleSenhaIncorreta(SenhaIncorretaException ex) {
    log.info("Transação não realizada: " + SENHA_INVALIDA);
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(SENHA_INVALIDA);
  }
}
