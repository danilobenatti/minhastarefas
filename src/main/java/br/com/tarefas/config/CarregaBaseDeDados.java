package br.com.tarefas.config;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.tarefas.model.Categoria;
import br.com.tarefas.model.Role;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.Usuario;
import br.com.tarefas.model.enums.EnumRole;
import br.com.tarefas.model.enums.TarefaStatus;
import br.com.tarefas.repository.RoleRepository;
import br.com.tarefas.service.CategoriaService;
import br.com.tarefas.service.TarefaService;
import br.com.tarefas.service.UsuarioService;

@Configuration
@Profile(value = "dev")
public class CarregaBaseDeDados {

	@Bean
	CommandLineRunner executar(UsuarioService usuarioService,
			CategoriaService categoriaService, TarefaService tarefaService,
			PasswordEncoder encoder, RoleRepository roleRepository) {
		return args -> {
			var senha = "123456";

			Role roleAdmin = new Role(EnumRole.ROLE_ADMIN);
			roleAdmin = roleRepository.save(roleAdmin);

			Role roleUser = new Role(EnumRole.ROLE_USER);
			roleUser = roleRepository.save(roleUser);

			Role roleGuest = new Role(EnumRole.ROLE_GUEST);
			roleGuest = roleRepository.save(roleGuest);

			var usuario1 = Usuario.builder().nome("Admin").senha(senha)
					.email("admin@test.com").roles(Set.of(roleAdmin)).build();
			usuarioService.postUsuario(usuario1);

			var usuario2 = Usuario.builder().nome("User").senha(senha)
					.email("user@test.com").roles(Set.of(roleUser, roleAdmin))
					.build();
			usuarioService.postUsuario(usuario2);

			var usuario3 = Usuario.builder().nome("Ghest").senha(senha)
					.email("guest@test.com").roles(Set.of(roleGuest)).build();
			usuarioService.postUsuario(usuario3);

			var categoria1 = new Categoria().setNome("Estudos");
			categoriaService.postCategoria(categoria1);

			var categoria2 = new Categoria().setNome("Lazer");
			categoriaService.postCategoria(categoria2);

			var tarefa1 = Tarefa.builder().descricao("Aprender Spring Boot")
					.status(TarefaStatus.ABERTO)
					.dataEntrega(LocalDate.now().plusDays(1)).visivel(true)
					.categoria(categoria1).usuario(usuario1).build();
			tarefaService.postTarefa(tarefa1);

			var tarefa2 = Tarefa.builder().descricao("Aprender Spring Data JPA")
					.status(TarefaStatus.ABERTO)
					.dataEntrega(LocalDate.now().plusDays(2)).visivel(true)
					.categoria(categoria1).usuario(usuario1).build();
			tarefaService.postTarefa(tarefa2);

			var tarefa3 = Tarefa.builder().descricao("Linguagem Java Básico")
					.status(TarefaStatus.EM_ANDAMENTO)
					.dataEntrega(LocalDate.now().plusDays(3)).visivel(true)
					.categoria(categoria1).usuario(usuario2).build();
			tarefaService.postTarefa(tarefa3);

			var tarefa4 = Tarefa.builder().descricao("Ler um jornal ou revista")
					.status(TarefaStatus.EM_ANDAMENTO)
					.dataEntrega(LocalDate.now().plusDays(1)).visivel(true)
					.categoria(categoria2).usuario(usuario3).build();
			tarefaService.postTarefa(tarefa4);
		};
	}

}
