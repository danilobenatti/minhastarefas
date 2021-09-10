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

import br.com.tarefas.controller.assembler.CategoriaModelAssembler;
import br.com.tarefas.controller.request.CategoriaRequest;
import br.com.tarefas.controller.response.CategoriaResponse;
import br.com.tarefas.model.Categoria;
import br.com.tarefas.service.CategoriaService;

@RestController
@RequestMapping(path = "/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoriaModelAssembler assembler;

	@GetMapping(path = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<EntityModel<CategoriaResponse>> listCategorias(
			@RequestParam Map<String, String> parametros) {
		List<Categoria> categorias;

		if (parametros.isEmpty()) {
			categorias = service.getAllCategorias();
		} else {
			categorias = service.getCategoriasByNome(parametros.get("nome"));
		}

		List<EntityModel<CategoriaResponse>> categoriasModels = categorias
				.stream().map(assembler::toModel).collect(Collectors.toList());

		return CollectionModel
				.of(categoriasModels,
						linkTo(methodOn(CategoriaController.class)
								.listCategorias(new HashMap<>()))
										.withSelfRel());
	}

	@GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<CategoriaResponse> findCategoria(
			@PathVariable(value = "id") Integer id) {
		return assembler.toModel(service.getCategoriaById(id));
	}

	@PostMapping(path = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<CategoriaResponse>> saveCategoria(
			@Valid @RequestBody CategoriaRequest categoriaRequest) {
		Categoria categoriaReq = mapper.map(categoriaRequest, Categoria.class);

		EntityModel<CategoriaResponse> categoriaModel = assembler
				.toModel(service.postCategoria(categoriaReq));

		return ResponseEntity.created(
				categoriaModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(categoriaModel);
	}

	@PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<CategoriaResponse>> updateCategoria(
			@PathVariable Integer id,
			@Valid @RequestBody CategoriaRequest categoriaRequest) {
		Categoria categoria = mapper.map(categoriaRequest, Categoria.class);
		Categoria categoriaAtualizada = service.updateCategoria(id, categoria);
		EntityModel<CategoriaResponse> categoriaModel = assembler
				.toModel(categoriaAtualizada);
		return ResponseEntity.ok().body(categoriaModel);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletecategoria(@PathVariable(value = "id") Integer id) {
		service.deleteCategoriaById(id);
	}

}
