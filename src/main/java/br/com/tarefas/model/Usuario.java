package br.com.tarefas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ToString.Include
	private Integer id;

	@NotBlank(message = "{Usuario.nome.NotBlank}")
	@Size(min = 4, max = 50, message = "{Usuario.nome.Size}")
	@Column(nullable = false)
	@ToString.Include
	private String nome;

	@NotBlank(message = "{Usuario.senha.NotBlank}")
	@Column(nullable = false)
	private String senha;

	@Email(message = "{Usuario.email.Email}")
	@Column(nullable = false)
	@ToString.Include
	private String email;

	@JsonIgnore
	@Builder.Default
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Tarefa> tarefas = new ArrayList<>();

	@JsonIgnore
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuarios_roles",
		joinColumns = @JoinColumn(name = "usuario_id",
			referencedColumnName = "id",
			foreignKey = @ForeignKey(name = "fk_usuarios_roles__usuario_id",
				foreignKeyDefinition = "foreign key (usuario_id) references usuarios(id) on delete cascade")),
		inverseJoinColumns = @JoinColumn(name = "role_id",
			referencedColumnName = "id",
			foreignKey = @ForeignKey(name = "fk_usuarios_roles__role_id",
				foreignKeyDefinition = "foreign key (role_id) references roles(id) on delete cascade")))
	private Set<Role> roles = new HashSet<>();

}
