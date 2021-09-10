package br.com.tarefas.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tarefas.controller.response.JwtResponse;
import br.com.tarefas.model.Role;
import br.com.tarefas.model.Usuario;
import br.com.tarefas.repository.RoleRepository;
import br.com.tarefas.repository.UsuarioRepository;
import br.com.tarefas.security.JwtUtils;
import br.com.tarefas.security.UserDetailsImpl;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	public List<Usuario> getAllUsuarios() {
		return repository.findAll();
	}

	public List<Usuario> getUsuariosByNome(String string) {
		return repository.findByNomeContainingIgnoreCase(string);
	}

	public Usuario getUsuarioById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"Usuario com id " + id + " não encontrada."));
	}

	public Usuario postUsuario(Usuario usuario) {
		usuario.setRoles(getRoles(usuario));
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return repository.save(usuario);
	}

	public Usuario updateUsuario(Integer id, Usuario usuario) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException(
					"Usuario com id [" + id + "] não encontrado.");
		}
		usuario.setId(id);
		return postUsuario(usuario);
	}

	private Set<Role> getRoles(Usuario usuario) {
		return usuario.getRoles().stream()
				.map(role -> roleRepository.findByName(role.getName()))
				.collect(Collectors.toSet());
	}

	public void deleteUsuarioById(Integer integer) {
		repository.deleteById(integer);
	}

	public JwtResponse autenticaUsuario(String nome, String senha) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(nome, senha));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String generateJwtToken = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication
				.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority()).collect(Collectors.toList());

		return new JwtResponse(generateJwtToken, userDetails.getId(),
				userDetails.getUsername(), roles);
	}

}
