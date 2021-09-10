package br.com.tarefas.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.tarefas.model.enums.TarefaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@Builder
@Entity
@Table(name = "tarefas")
@NamedQuery(name = "Tarefa.tarefasPorCategoria", query = "select t from Tarefa t inner join t.categoria c where c.nome = ?1")
public class Tarefa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "{Tarefa.descricao.NotBlank}")
	@Size(min = 5, max = 150, message = "{Tarefa.descricao.Size}")
	@Column(length = 150, nullable = false)
	private String descricao;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private TarefaStatus status = TarefaStatus.ABERTO;

	@NotNull(message = "{Tarefa.dataEntrega.NotNull}")
	@FutureOrPresent(message = "{Tarefa.dataEntrega.FutureOrPresent}")
	private LocalDate dataEntrega;

	private boolean visivel;

	@ManyToOne(optional = false)
	@JoinColumn(name = "categoria_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_tarefa__categoria_id",
		foreignKeyDefinition = "foreign key (categoria_id) references categorias(id) on delete cascade"))
	private Categoria categoria;

	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_tarefa__usuario_id",
		foreignKeyDefinition = "foreign key (usuario_id) references usuarios(id) on delete cascade"))
	private Usuario usuario;

}
