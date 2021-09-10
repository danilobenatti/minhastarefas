package br.com.tarefas.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.tarefas.exception.TarefaStatusException;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.enums.TarefaStatus;
import br.com.tarefas.repository.TarefaRepository;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

	@Mock
	private TarefaRepository repository;

	@InjectMocks
	private TarefaService service;

	@Test
	void naoDeveConcluirTarefaCancelada() {
		var idExemplo = 1;

		var tarefa = new Tarefa();
		ReflectionTestUtils.setField(tarefa, "id", idExemplo);
		tarefa.setDescricao("Teste 01");
		tarefa.setStatus(TarefaStatus.CANCELADA);

		Mockito.when(repository.findById(idExemplo)).thenReturn(Optional.of(tarefa));

		Assertions.assertThrows(TarefaStatusException.class, () -> service.concluirTarefaPorId(idExemplo));
	}

	@Test
	void naoDeveCancelarTarefaConcluida() {
		var idExemplo = 1;

		var tarefa = new Tarefa();
		ReflectionTestUtils.setField(tarefa, "id", idExemplo);
		tarefa.setDescricao("Teste 01");
		tarefa.setStatus(TarefaStatus.CONCLUIDA);

		Mockito.when(repository.findById(idExemplo)).thenReturn(Optional.of(tarefa));

		Assertions.assertThrows(TarefaStatusException.class, () -> service.cancelarTarefaPorId(idExemplo));
	}
}
