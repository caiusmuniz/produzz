package br.com.produzz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = -8912030934827978989L;

	@Id
	@Column(name = "NR_PDZ001")
	private Long id;

	@NotNull
	@Size(min = 1, max = 120)
	@Column(name = "DE_NOME")
	private String nome;

	@NotNull
	@Size(min = 1, max = 120)
	@Column(name = "DE_SOBRENOME")
	private String sobrenome;

	@NotNull
	@Size(min = 1, max = 120)
	@Column(name = "ed_eletronico")
	private String email;
	
	@NotNull
	@Size(min = 1, max = 64)
	@Column(name = "DE_SENHA")
	private String senha;

	@NotNull
	@Size(min = 1, max = 1)
	@Column(name = "IC_ATIVO")
	private String status;

	@Column(name = "FK_TIPO_USUARIO")
	private Integer tipoUsuario;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(final String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(final String senha) {
		this.senha = senha;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(final Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("UsuarioEntity [")
				.append("id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append(", sobrenome=")
				.append(sobrenome)
				.append(", email=")
				.append(email)
				.append(", senha=")
				.append(senha)
				.append(", status=")
				.append(status)
				.append(", tipoUsuario=")
				.append(tipoUsuario)
				.append("]")
				.toString();
	}
}
