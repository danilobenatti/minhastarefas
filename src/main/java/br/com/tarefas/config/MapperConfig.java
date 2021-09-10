package br.com.tarefas.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.tarefas.controller.response.CategoriaResponse;
import br.com.tarefas.controller.response.TarefaResponse;
import br.com.tarefas.controller.response.TarefaResponseDTO;
import br.com.tarefas.controller.response.UsuarioResponse;
import br.com.tarefas.model.Tarefa;

@Configuration
public class MapperConfig {

	@Bean
	public ModelMapper mapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(Tarefa.class, TarefaResponse.class)
				.addMapping(src -> src.getCategoria().getNome(),
						(dest, value) -> dest.setCategoria((String) value))
				.addMapping(src -> src.getUsuario().getNome(),
						(dest, value) -> dest.setUsuario((String) value));

		modelMapper.createTypeMap(Tarefa.class, TarefaResponseDTO.class)
				.addMapping(src -> src.getCategoria().getClass(),
						(dest, value) -> dest.setCategoria((CategoriaResponse) value))
				.addMapping(src -> src.getUsuario().getClass(),
						(dest, value) -> dest.setUsuario((UsuarioResponse) value));

		return modelMapper;
	}
}
