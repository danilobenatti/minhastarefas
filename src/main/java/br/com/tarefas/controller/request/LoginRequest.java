package br.com.tarefas.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	private String nome;

	private String senha;
}
