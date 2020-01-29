package info.andrefelipelaus.megasenabackend.controller.form;

import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import info.andrefelipelaus.megasenabackend.model.Usuario;
import lombok.Data;

@Data
public class UsuarioForm {
	
	@NotNull
	private String nome;
	
	@NotNull
	private String nomeCompleto;
	
	@NotNull
	private String email;
	
	@NotNull
	private String senha;
	
	public Usuario converter() {
		return new Usuario(this.nome, this.nomeCompleto, this.email, new BCryptPasswordEncoder().encode(senha));
	}

}
