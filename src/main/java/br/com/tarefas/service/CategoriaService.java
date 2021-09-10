package br.com.tarefas.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tarefas.model.Categoria;
import br.com.tarefas.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> getAllCategorias() {
		return repository.findAll();
	}

	public List<Categoria> getCategoriasByNome(String string) {
		return repository.findByNomeContainingIgnoreCase(string);
	}

	public Categoria updateCategoria(Integer id, Categoria categoria) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException(
					"Categoria com id [" + id + "] não encontrada");
		}
		categoria.setId(id);
		return postCategoria(categoria);
	}

	public Categoria getCategoriaById(Integer integer) {
		return repository.findById(integer)
				.orElseThrow(() -> new EntityNotFoundException(
						"Categoria com id " + integer + "não encontrada."));
	}

	public Categoria postCategoria(Categoria categoria) {
		return repository.save(categoria);
	}

	public void deleteCategoriaById(Integer integer) {
		repository.deleteById(integer);
	}
}
