package com.app.miniautorizador.domain;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Cartao implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "numero_conta", nullable = false, unique = true)
  private String numeroCartao;

  private String senha;

  private BigDecimal saldo;

  public Cartao(String numeroCartao, String senha) {
    this.numeroCartao = numeroCartao;
    this.senha = senha;
    this.saldo = new BigDecimal(500);
  }
}
