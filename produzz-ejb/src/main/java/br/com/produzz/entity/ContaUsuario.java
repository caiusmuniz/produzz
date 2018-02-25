package br.com.produzz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class ContaUsuario implements Serializable {
	private static final long serialVersionUID = -828988074811802671L;

	@Id
	@Column(name = "NR_PDZ005")
	private Long id;

	@NotNull
	@Column(name = "FK_USUARIO")
	private Long usuario;

	@NotNull
	@Column(name = "FK_CONTA")
	private Long conta;

	public ContaUsuario() { }

	public ContaUsuario(final Long usuario, final Long conta) {
		this.usuario = usuario;
		this.conta = conta;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(final Long usuario) {
		this.usuario = usuario;
	}

	public Long getConta() {
		return conta;
	}

	public void setConta(final Long conta) {
		this.conta = conta;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("ContaUsuario [")
				.append("id=")
				.append(id)
				.append(", usuario=")
				.append(usuario)
				.append(", conta=")
				.append(conta)
				.append("]")
				.toString();
	}
}
