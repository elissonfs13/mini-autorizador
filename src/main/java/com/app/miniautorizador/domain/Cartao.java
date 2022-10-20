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

  /**
   * Utilização de constraints para garantir que não existam cartões com números repetidos na base
   * de dados
   */
  @Column(name = "numero_conta", nullable = false, unique = true)
  private String numeroCartao;

  private String senha;

  /**
   * Utilização da biblioteca javax.validation para garantir que o atributo 'saldo' não possua valor
   * negativo
   */
  @Min(0)
  private BigDecimal saldo;

  public Cartao() {}

  /**
   * Construtor de um novo cartão com número e senha passados como parâmetro. O atributo saldo do
   * novo cartão é iniciado com o valor R$500,00 por padrão.
   *
   * @param numeroCartao número do novo cartão
   * @param senha senha do novo cartão
   */
  public Cartao(final String numeroCartao, final String senha) {
    this.numeroCartao = numeroCartao;
    this.senha = senha;
    this.saldo = new BigDecimal(500).setScale(2, RoundingMode.HALF_UP);
  }

  /**
   * Método responsável por realizar a comparação de uma senha passada como parâmetro com a senha do
   * cartão. Caso os valores sejam diferentes, ou seja, a senha passada for incorreta, a exceção
   * 'SenhaIncorretaException' é lançada para ser tratada por quem deseja realizar essa validação.
   *
   * @param senhaEntrada senha a ser comparada com a senha do cartão.
   */
  public void validaSenha(final String senhaEntrada) {
    if (senhaIncorreta(senhaEntrada)) throw new SenhaIncorretaException();
  }

  /**
   * Método responsável por realizar o débito de um valor passado como parâmetro do saldo do cartão.
   * Caso o valor resultante da transação for negativo, a exceção 'ConstraintViolationException' é
   * lançada, fazendo com que a transação não seja executada e o valor do saldo permanece
   * inalterado. Foi adicionado o modificador 'synchronized' para garantir o devido tratamento de
   * concorrência na solução e tornar o método 'Thread-safe'.
   *
   * @param valor valor a ser debitado do saldo do cartão.
   */
  public synchronized void realizaTrasacao(final BigDecimal valor) {
    setSaldo(this.saldo.subtract(valor));
  }

  private Boolean senhaIncorreta(final String senhaEntrada) {
    return this.getSenha().equals(senhaEntrada) ? Boolean.FALSE : Boolean.TRUE;
  }

  private void setSaldo(final BigDecimal saldo) {
    this.saldo = saldo.setScale(2, RoundingMode.HALF_UP);
  }
}
