package br.com.tarefas.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse {

	private Integer id;

	private String nome;

	private String email;
}
