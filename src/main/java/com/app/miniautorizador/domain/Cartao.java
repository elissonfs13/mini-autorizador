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
import java.math.RoundingMode;

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
    this.saldo = new BigDecimal(500).setScale(2, RoundingMode.HALF_UP);
  }

  public void validaSenha(String senhaEntrada) {
    if (senhaIncorreta(senhaEntrada)) throw new SenhaIncorretaException();
  }

  public void realizaTrasacao(BigDecimal valor) {
    setSaldo(this.saldo.subtract(valor));
  }

  private Boolean senhaIncorreta(String senhaEntrada) {
    return this.getSenha().equals(senhaEntrada) ? Boolean.FALSE : Boolean.TRUE;
  }

  private void setSaldo(BigDecimal saldo) {
    this.saldo = saldo.setScale(2, RoundingMode.HALF_UP);
  }
}
