package com.app.miniautorizador.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CartaoDTO {

  private String numeroCartao;

  private String senha;
}
