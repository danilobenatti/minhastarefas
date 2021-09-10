package br.com.tarefas.controller.request;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.tarefas.model.Categoria;
import br.com.tarefas.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaRequest {

	private Integer id;

	@NotBlank(message = "{Tarefa.descricao.NotBlank}")
	@Size(min = 5, max = 150, message = "{Tarefa.descricao.Size}")
	private String descricao;

	@NotNull(message = "{Tarefa.dataEntrega.NotNull}")
	@FutureOrPresent(message = "{Tarefa.dataEntrega.FutureOrPresent}")
	private LocalDate dataEntrega;

	@NotNull(message = "{Tarefa.categoria.NotNull}")
	private Categoria categoria;

	@NotNull(message = "{Tarefa.usuario.NotNull}")
	private Usuario usuario;
}
