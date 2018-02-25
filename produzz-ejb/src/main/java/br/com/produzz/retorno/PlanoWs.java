package br.com.produzz.retorno;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.produzz.entity.Plano;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanoWs implements Serializable {
	private static final long serialVersionUID = -8886899079197477596L;

	private Long id;
	private String nome;
	private Long qtVideo;

	public PlanoWs() { }

	public PlanoWs(final Long id) {
		this.id = id;
	}

	public PlanoWs(final Long id, final String nome, final Long qtVideo) {
		this.id = id;
		this.nome = nome;
		this.qtVideo = qtVideo;
	}

	public PlanoWs(final Plano plano) {
		this.id = plano.getId();
		this.nome = plano.getNome();
		this.qtVideo = plano.getQtVideo();
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

	public Long getQtVideo() {
		return qtVideo;
	}

	public void setQtVideo(final Long qtVideo) {
		this.qtVideo = qtVideo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlanoWs [id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append(", qtVideo=")
				.append(qtVideo)
				.append("]");
		return builder.toString();
	}
}
