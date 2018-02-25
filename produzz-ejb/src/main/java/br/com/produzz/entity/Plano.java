package br.com.produzz.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Plano implements Serializable {
	private static final long serialVersionUID = 4114419209920959390L;

	@Id
	@Column(name = "NR_PDZ008")
	private Long id;

	@Column(name = "NM_PLANO")
	private String nome;

	@Column(name = "VR_PLANO", length = 10, precision = 2)
	private BigDecimal vrPlano;

	@Column(name = "VR_LIQUIDO", length = 10, precision = 2)
	private BigDecimal vrLiquido;

	@Column(name = "VR_VIDEO", length = 4, precision = 3)
	private BigDecimal vrVideo;

	@Column(name = "QT_VIDEO", length = 11)
	private Long qtVideo;

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

	public BigDecimal getVrPlano() {
		return vrPlano;
	}

	public void setVrPlano(final BigDecimal vrPlano) {
		this.vrPlano = vrPlano;
	}

	public BigDecimal getVrLiquido() {
		return vrLiquido;
	}

	public void setVrLiquido(final BigDecimal vrLiquido) {
		this.vrLiquido = vrLiquido;
	}

	public BigDecimal getVrVideo() {
		return vrVideo;
	}

	public void setVrVideo(final BigDecimal vrVideo) {
		this.vrVideo = vrVideo;
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
		return builder.append("Plano [id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append(", vrPlano=")
				.append(vrPlano)
				.append(", vrLiquido=")
				.append(vrLiquido)
				.append(", vrVideo=")
				.append(vrVideo)
				.append(", qtVideo=")
				.append(qtVideo)
				.append("]")
				.toString();
	}
}
