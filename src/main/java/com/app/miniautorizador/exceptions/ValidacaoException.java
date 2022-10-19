package com.app.miniautorizador.exceptions;

public class ValidacaoException extends RuntimeException{

  private String msg;

  public ValidacaoException(String msg) {
    super(msg);
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }
}
