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
import br.com.tarefas.controller.TarefaController;
import br.com.tarefas.controller.UsuarioController;
import br.com.tarefas.controller.response.TarefaResponseDTO;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.enums.TarefaStatus;

@Component
public class TarefaModelAssembler
		implements
			RepresentationModelAssembler<Tarefa, EntityModel<TarefaResponseDTO>> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public EntityModel<TarefaResponseDTO> toModel(Tarefa tarefa) {
		final TarefaResponseDTO tarefaResp = mapper.map(tarefa,
				TarefaResponseDTO.class);

		final EntityModel<TarefaResponseDTO> tarefaModel = EntityModel.of(
				tarefaResp,
				linkTo(methodOn(TarefaController.class)
						.findTarefa(tarefaResp.getId())).withSelfRel(),
				linkTo(methodOn(TarefaController.class)
						.listTarefas(new HashMap<>())).withRel("tarefas"),
				linkTo(methodOn(CategoriaController.class)
						.findCategoria(tarefaResp.getCategoria().getId()))
								.withRel("categoria"),
				linkTo(methodOn(UsuarioController.class)
						.findUsuario(tarefaResp.getUsuario().getId()))
								.withRel("usuario"));

		if (TarefaStatus.EM_ANDAMENTO.equals(tarefa.getStatus())) {
			tarefaModel.add(
					linkTo(methodOn(TarefaController.class)
							.concluirTarefa(tarefaResp.getId()))
									.withRel("concluir"),
					linkTo(methodOn(TarefaController.class)
							.cancelarTarefa(tarefaResp.getId()))
									.withRel("cancelar"));
		}

		if (TarefaStatus.ABERTO.equals(tarefa.getStatus())) {
			tarefaModel.add(linkTo(methodOn(TarefaController.class)
					.iniciarTarefa(tarefaResp.getId())).withRel("iniciar"));
		}

		return tarefaModel;
	}

}
