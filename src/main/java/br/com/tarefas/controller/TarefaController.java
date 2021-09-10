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

import br.com.tarefas.controller.assembler.TarefaModelAssembler;
import br.com.tarefas.controller.request.TarefaRequest;
import br.com.tarefas.controller.response.TarefaResponseDTO;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.service.TarefaService;

@RestController
@RequestMapping(path = "/tarefa")
public class TarefaController {

	@Autowired
	private TarefaService service;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private TarefaModelAssembler assembler;

	@GetMapping(path = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<EntityModel<TarefaResponseDTO>> listTarefas(
			@RequestParam Map<String, String> parametros) {
		List<Tarefa> tarefas;

		if (parametros.isEmpty()) {
			tarefas = service.getAllTarefas();
		} else {
			tarefas = service
					.getTarefasByDescricao(parametros.get("descricao"));
		}

		List<EntityModel<TarefaResponseDTO>> tarefasModels = tarefas.stream()
				.map(assembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(tarefasModels, linkTo(
				methodOn(TarefaController.class).listTarefas(new HashMap<>()))
						.withSelfRel());
	}

	@GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<TarefaResponseDTO> findTarefa(
			@PathVariable(value = "id") Integer id) {
		return assembler.toModel(service.getTarefaById(id));
	}

	@PostMapping(path = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<TarefaResponseDTO>> saveTarefa(
			@Valid @RequestBody TarefaRequest tarefaRequest) {
		Tarefa tarefaReq = mapper.map(tarefaRequest, Tarefa.class);

		EntityModel<TarefaResponseDTO> tarefaModel = assembler
				.toModel(service.postTarefa(tarefaReq));

		return ResponseEntity.created(
				tarefaModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(tarefaModel);
	}

	@PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<TarefaResponseDTO>> updateTarefa(
			@PathVariable(value = "id") Integer id,
			@Valid @RequestBody TarefaRequest tarefaRequest) {
		Tarefa tarefa = mapper.map(tarefaRequest, Tarefa.class);
		Tarefa tarefaAtualizada = service.updateTarefa(id, tarefa);
		EntityModel<TarefaResponseDTO> tarefaModel = assembler
				.toModel(tarefaAtualizada);
		return ResponseEntity.ok().body(tarefaModel);

	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteTarefa(@PathVariable(value = "id") Integer id) {
		service.deleteTarefaById(id);
	}

	@PutMapping(path = "/{id}/iniciar", produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<TarefaResponseDTO> iniciarTarefa(
			@PathVariable Integer id) {
		return assembler.toModel(service.iniciarTarefaPorId(id));
	}

	@PutMapping(path = "/{id}/concluir", produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<TarefaResponseDTO> concluirTarefa(
			@PathVariable Integer id) {
		return assembler.toModel(service.concluirTarefaPorId(id));
	}

	@PutMapping(path = "/{id}/cancelar", produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<TarefaResponseDTO> cancelarTarefa(
			@PathVariable Integer id) {
		return assembler.toModel(service.cancelarTarefaPorId(id));
	}

}
