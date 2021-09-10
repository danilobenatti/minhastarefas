package br.com.tarefas.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequest {

	private Integer id;

	@NotBlank(message = "{Categoria.nome.NotBlank}")
	@Size(min = 5, max = 50, message = "{Categoria.nome.Size}")
	private String nome;
}
