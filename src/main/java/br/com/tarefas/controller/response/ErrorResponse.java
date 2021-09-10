package br.com.tarefas.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

	public ErrorResponse(String mensagem) {
		this.mensagem = mensagem;
	}

	private String campo;

	private String mensagem;

}
