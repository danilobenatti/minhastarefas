package br.com.tarefas.controller.request;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.tarefas.model.Tarefa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

	private Integer id;

	@NotBlank(message = "{Usuario.nome.NotBlank}")
	@Size(min = 4, max = 50, message = "{Usuario.nome.Size}")
	private String nome;

	@NotBlank(message = "{Usuario.senha.NotBlank}")
	private String senha;

	@Email(message = "{Usuario.email.Email}")
	private String email;

	private List<Tarefa> tarefas;

	private Set<RoleRequest> roles;

}
