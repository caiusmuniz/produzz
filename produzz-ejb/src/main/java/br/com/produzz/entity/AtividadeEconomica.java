package br.com.produzz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class AtividadeEconomica implements Serializable {
	private static final long serialVersionUID = -6835208620629849424L;

	@Id
	@Column(name = "NR_PDZ009")
	private Long id;

	@NotNull
	@Size(min = 5, max = 128)
	@Column(name = "NM_ATIVIDADE_ECONOMICA")
	private String nome;

	@Column(name = "NM_SEGMENTO")
	private String segmento;

	public AtividadeEconomica() { }

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

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(final String segmento) {
		this.segmento = segmento;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("AtividadeEconomica [")
				.append("id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append(", segmento=")
				.append(segmento)
				.append("]")
				.toString();
	}
}
