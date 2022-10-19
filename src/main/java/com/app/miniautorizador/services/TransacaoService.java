package com.app.miniautorizador.services;

import com.app.miniautorizador.controllers.dtos.TransacaoDTO;

public interface TransacaoService {

  void processaTransacao(TransacaoDTO transacao);
}
