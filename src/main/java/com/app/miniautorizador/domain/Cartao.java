package com.app.miniautorizador.domain;

import com.app.miniautorizador.exceptions.SenhaIncorretaException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
public class Cartao implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "numero_conta", nullable = false, unique = true)
  private String numeroCartao;

  private String senha;

  @Min(0)
  private BigDecimal saldo;

  public Cartao() {}

  public Cartao(String numeroCartao, String senha) {
    this.numeroCartao = numeroCartao;
    this.senha = senha;
    this.saldo = new BigDecimal(500).setScale(2);
  }

  public void validaSenha(String senhaEntrada) {
    if (!this.getSenha().equals(senhaEntrada)) throw new SenhaIncorretaException();
  }

  public void realizaTrasacao(BigDecimal valor) {
    setSaldo(this.saldo.subtract(valor));
  }

  private void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }
}
