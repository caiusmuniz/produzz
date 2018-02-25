package br.com.produzz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Segmento implements Serializable {
	private static final long serialVersionUID = -6835208620629849424L;

	@Id
	@Column(name = "NR_PDZ010")
	private Long id;

	@NotNull
	@Size(min = 5, max = 128)
	@Column(name = "NM_SEGMENTO")
	private String nome;

	public Segmento() { }

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("Segmento [")
				.append("id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append("]")
				.toString();
	}
}
