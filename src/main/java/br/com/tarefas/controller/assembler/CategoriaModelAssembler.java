package br.com.tarefas.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.com.tarefas.controller.CategoriaController;
import br.com.tarefas.controller.response.CategoriaResponse;
import br.com.tarefas.model.Categoria;

@Component
public class CategoriaModelAssembler
		implements
			RepresentationModelAssembler<Categoria, EntityModel<CategoriaResponse>> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public EntityModel<CategoriaResponse> toModel(Categoria categoria) {
		final CategoriaResponse categoriaResp = mapper.map(categoria,
				CategoriaResponse.class);
		return EntityModel.of(categoriaResp,
				linkTo(methodOn(CategoriaController.class)
						.findCategoria(categoriaResp.getId())).withSelfRel(),
				linkTo(methodOn(CategoriaController.class)
						.listCategorias(new HashMap<>()))
								.withRel("categorias"));
	}

}
