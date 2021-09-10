package br.com.tarefas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tarefas.model.Categoria;
import br.com.tarefas.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

	List<Tarefa> findByDescricaoContainingIgnoreCase(String descricao);
	
	List<Tarefa> findByCategoria(Categoria categoria);

	@Query(value = "select t from Tarefa t inner join t.categoria c where c.nome = ?1")
	List<Tarefa> findByNomeCategoria(String nomeCategoria);

	List<Tarefa> tarefasPorCategoria(String nomeCategoria);
}
