package br.com.produzz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Categoria implements Serializable {
	private static final long serialVersionUID = 2335707834888468069L;

	@Id
	@Column(name = "NR_CATEGORIA")
	private Integer id;

	@Column(name = "NM_CATEGORIA")
	private String nome;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("Categoria [id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append("]")
				.toString();
	}
}
