package br.com.tarefas.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefas.controller.assembler.UsuarioModelAssembler;
import br.com.tarefas.controller.request.UsuarioRequest;
import br.com.tarefas.controller.response.UsuarioResponse;
import br.com.tarefas.model.Usuario;
import br.com.tarefas.service.UsuarioService;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UsuarioModelAssembler assembler;

	@GetMapping(path = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<EntityModel<UsuarioResponse>> listUsuarios(
			@RequestParam Map<String, String> parametros) {
		List<Usuario> usuarios;
		if (parametros.isEmpty()) {
			usuarios = service.getAllUsuarios();
		} else {
			usuarios = service.getUsuariosByNome(parametros.get("nome"));
		}
		List<EntityModel<UsuarioResponse>> usuariosModels = usuarios.stream()
				.map(assembler::toModel).collect(Collectors.toList());
		return CollectionModel.of(usuariosModels, linkTo(
				methodOn(UsuarioController.class).listUsuarios(new HashMap<>()))
						.withSelfRel());
	}

	@GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<UsuarioResponse> findUsuario(
			@PathVariable(value = "id") Integer id) {
		return assembler.toModel(service.getUsuarioById(id));
	}

	@PostMapping(path = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<UsuarioResponse>> saveUsuario(
			@Valid @RequestBody UsuarioRequest usuarioRequest) {
		EntityModel<UsuarioResponse> usuarioModel = assembler.toModel(
				service.postUsuario(mapper.map(usuarioRequest, Usuario.class)));
		return ResponseEntity.created(
				usuarioModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuarioModel);
	}

	@PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<UsuarioResponse>> updateUsuario(
			@PathVariable(value = "id") Integer id,
			@Valid @RequestBody UsuarioRequest usuarioRequest) {
		EntityModel<UsuarioResponse> usuarioModel = assembler.toModel(service
				.updateUsuario(id, mapper.map(usuarioRequest, Usuario.class)));
		return ResponseEntity.ok().body(usuarioModel);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteUsuario(@PathVariable(value = "id") Integer id) {
		service.deleteUsuarioById(id);
	}

}
