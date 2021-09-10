package br.com.tarefas.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.com.tarefas.controller.UsuarioController;
import br.com.tarefas.controller.response.UsuarioResponse;
import br.com.tarefas.model.Usuario;

@Component
public class UsuarioModelAssembler
		implements
			RepresentationModelAssembler<Usuario, EntityModel<UsuarioResponse>> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public EntityModel<UsuarioResponse> toModel(Usuario usuario) {
		final UsuarioResponse usuarioResp = mapper.map(usuario,
				UsuarioResponse.class);
		return EntityModel.of(usuarioResp,
				linkTo(methodOn(UsuarioController.class)
						.findUsuario(usuarioResp.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class)
						.listUsuarios(new HashMap<>())).withRel("usuarios"));
	}

}
