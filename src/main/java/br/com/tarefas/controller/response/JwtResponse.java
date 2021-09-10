package br.com.tarefas.controller.response;

import java.util.List;

import br.com.tarefas.model.enums.EnumRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private String token;

	private String type = "Bearer";

	private Integer id;

	private String username;

	private List<String> roles;

	public boolean isAdmin() {
		return roles.contains(EnumRole.ROLE_ADMIN.name());
	}

	public JwtResponse(String token, Integer id, String username,
			List<String> roles) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
}
