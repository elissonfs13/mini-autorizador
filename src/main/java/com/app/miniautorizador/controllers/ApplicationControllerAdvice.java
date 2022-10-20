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

  /**
   * Captura exceção proveniente da realização da transação. Realiza validação de saldo insuficiente
   * na conta. A exceção é lançada quando o resultado da transação for negativo.
   *
   * @param ex exceção capturada
   * @return status = 422 e body = "SALDO_INSUFICIENTE"
   */
  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
    log.info("Transação não realizada: " + SALDO_INSUFICIENTE);
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(SALDO_INSUFICIENTE);
  }

  /**
   * Captura exceção proveniente da busca do cartão na base de dados. Realiza validação de cartão
   * existente na base de dados. A exceção é lançada quando o número de cartão informado na
   * transação não se encontra na base de dados.
   *
   * @param ex exceção capturada
   * @return status = 422 e body = "CARTAO_INEXISTENTE"
   */
  @ExceptionHandler({NoSuchElementException.class})
  public ResponseEntity<String> handleNoSuchElement(NoSuchElementException ex) {
    log.info("Transação não realizada: " + CARTAO_INEXISTENTE);
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(CARTAO_INEXISTENTE);
  }

  /**
   * Captura exceção proveniente da comparação entre a senha do cartão e a senha informada na
   * transação. Realiza validação de senha correta do cartão. A exceção é lançada quando a senha
   * informada é diferente da senha do cartão, ou seja é incorreta.
   *
   * @param ex exceção capturada
   * @return status = 422 e body = "SENHA_INVALIDA"
   */
  @ExceptionHandler({SenhaIncorretaException.class})
  public ResponseEntity<String> handleSenhaIncorreta(SenhaIncorretaException ex) {
    log.info("Transação não realizada: " + SENHA_INVALIDA);
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(SENHA_INVALIDA);
  }
}
