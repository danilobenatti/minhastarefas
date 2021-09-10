package br.com.tarefas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tarefas.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	List<Usuario> findByNomeContainingIgnoreCase(String nome);

	Optional<Usuario> findByNome(String username);
}
