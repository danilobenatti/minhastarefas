package br.com.tarefas.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tarefas.exception.TarefaStatusException;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.enums.TarefaStatus;
import br.com.tarefas.repository.TarefaRepository;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepository repository;

	public List<Tarefa> getAllTarefas() {
		return repository.findAll();
	}

	public List<Tarefa> getTarefasByDescricao(String string) {
		return repository.findByDescricaoContainingIgnoreCase(string);
	}

	public Tarefa getTarefaById(Integer integer) {
		return repository.findById(integer)
				.orElseThrow(() -> new EntityNotFoundException(
						"Tarefa com id " + integer + "não encontrada."));
	}

	public Tarefa postTarefa(Tarefa tarefa) {
		return repository.save(tarefa);
	}

	public Tarefa updateTarefa(Integer id, Tarefa tarefa) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException(
					"Tarefa com id [" + id + "] não encontrada.");
		}
		tarefa.setId(id);
		return postTarefa(tarefa);
	}

	public void deleteTarefaById(Integer integer) {
		repository.deleteById(integer);
	}

	public Tarefa iniciarTarefaPorId(Integer id) {
		var tarefa = getTarefaById(id);
		if (!tarefa.getStatus().equals(TarefaStatus.ABERTO)) {
			throw new TarefaStatusException(
					"Não é possível iniciar uma tarefa em andamento");
		}
		tarefa.setStatus(TarefaStatus.EM_ANDAMENTO);
		return repository.save(tarefa);
	}

	public Tarefa concluirTarefaPorId(Integer id) {
		var tarefa = getTarefaById(id);
		if (tarefa.getStatus().equals(TarefaStatus.CANCELADA)) {
			throw new TarefaStatusException(
					"Não é possível concluir uma tarefa cancelada");
		}
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		return repository.save(tarefa);
	}

	public Tarefa cancelarTarefaPorId(Integer id) {
		var tarefa = getTarefaById(id);
		if (tarefa.getStatus().equals(TarefaStatus.CONCLUIDA)) {
			throw new TarefaStatusException(
					"Não é possível cancelar uma tarefa concluída");
		}
		tarefa.setStatus(TarefaStatus.CANCELADA);
		return repository.save(tarefa);
	}

}
