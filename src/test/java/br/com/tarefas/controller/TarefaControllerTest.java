package br.com.tarefas.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.tarefas.controller.response.TarefaResponseDTO;
import br.com.tarefas.model.Categoria;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.Usuario;
import br.com.tarefas.model.enums.TarefaStatus;
import br.com.tarefas.service.TarefaService;

@SpringBootTest
class TarefaControllerTest {

	@Autowired
	private TarefaController controller;

	@MockBean
	private TarefaService service;

	@Test
	void validaTarefaResponse() {
		Usuario usuario = new Usuario();
		ReflectionTestUtils.setField(usuario, "id", 1);

		Categoria categoria = new Categoria();
		ReflectionTestUtils.setField(categoria, "id", 1);

		Tarefa tarefa = new Tarefa();
		ReflectionTestUtils.setField(tarefa, "id", 1);
		tarefa.setStatus(TarefaStatus.ABERTO);
		tarefa.setCategoria(categoria);
		tarefa.setUsuario(usuario);

		Mockito.when(service.getTarefaById(tarefa.getId())).thenReturn(tarefa);

		EntityModel<TarefaResponseDTO> entityModel = controller
				.findTarefa(tarefa.getId());
		TarefaResponseDTO tarefaResponse = entityModel.getContent();

		Assertions.assertEquals(tarefa.getId(), tarefaResponse.getId());
		Assertions.assertEquals(1, tarefaResponse.getCategoria().getId());
		Assertions.assertEquals(1, tarefaResponse.getUsuario().getId());
		Assertions.assertEquals(TarefaStatus.ABERTO.name(),
				tarefaResponse.getStatus());
	}
}
