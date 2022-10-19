package com.app.miniautorizador.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class TransacaoDTO {

  private String numeroCartao;

  private String senhaCartao;

  private BigDecimal valor;
}
