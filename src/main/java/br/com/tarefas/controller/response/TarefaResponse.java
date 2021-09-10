package br.com.tarefas.controller.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaResponse {

	private Integer id;

	private String descricao;

	private String status;

	private LocalDate dataEntrega;

	private String categoria;

	private String usuario;
}
