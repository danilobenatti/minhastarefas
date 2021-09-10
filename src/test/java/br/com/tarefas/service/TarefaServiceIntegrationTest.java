package br.com.tarefas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tarefas.exception.TarefaStatusException;
import br.com.tarefas.model.enums.TarefaStatus;

@SpringBootTest
class TarefaServiceIntegrationTest {

	@Autowired
	private TarefaService service;

	@Test
	void deveIniciarTarefa() {
		var tarefa = service.iniciarTarefaPorId(3);
		Assertions.assertEquals(TarefaStatus.EM_ANDAMENTO, tarefa.getStatus());
	}

	@Test
	void naoDeveIniciarTarefaConcluida() {
		var tarefa = service.getTarefaById(3);
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		service.postTarefa(tarefa);

		Assertions.assertThrows(TarefaStatusException.class, () -> service.iniciarTarefaPorId(3));
	}
}
