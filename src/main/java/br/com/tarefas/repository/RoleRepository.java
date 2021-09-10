package br.com.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tarefas.model.Role;
import br.com.tarefas.model.enums.EnumRole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(EnumRole name);

}
