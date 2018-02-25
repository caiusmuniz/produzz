package br.com.produzz.retorno;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.produzz.entity.AtividadeEconomica;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AtividadeEconomicaWs implements Serializable {
	private static final long serialVersionUID = 5987914275751514697L;

	private Long id;
	private String nome;
	private String segmento;

	public AtividadeEconomicaWs() { }

	public AtividadeEconomicaWs(final Long id, final String nome, final String segmento) {
		this.id = id;
		this.nome = nome;
		this.segmento = segmento;
	}

	public AtividadeEconomicaWs(final AtividadeEconomica atividade) {
		this.id = atividade.getId();
		this.nome = atividade.getNome();
		this.segmento = atividade.getSegmento();
	}

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
		builder.append("AtividadeEconomicaWs [id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append(", segmento=")
				.append(segmento)
				.append("]");
		return builder.toString();
	}
}
