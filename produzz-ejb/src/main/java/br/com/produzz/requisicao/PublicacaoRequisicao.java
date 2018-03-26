package br.com.produzz.requisicao;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**.
 * Projeto PRODUZZ
 * PublicacaoRequisicao
 * @author Caius Muniz
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class PublicacaoRequisicao extends Requisicao implements Serializable {
	private static final long serialVersionUID = -9027565042028834887L;

	@Size(max = 11)
	protected Long id;

	@NotNull
	@Size(max = 11)
	protected Long idVideo;

	@Size(max = 11)
	protected Long idThumbnail;

	@NotNull
	@Size(max = 120)
	protected String titulo;

	@Size(max = 1024)
	protected String descricao;

	@Size(max = 1024)
	protected String tags;

	@Size(max = 10)
	protected String locale;

	@Size(max = 7)
	protected String privacidade;

	@Size(max = 11)
	protected Long categoria;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(final Long idVideo) {
		this.idVideo = idVideo;
	}

	public Long getIdThumbnail() {
		return idThumbnail;
	}

	public void setIdThumbnail(final Long idThumbnail) {
		this.idThumbnail = idThumbnail;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(final String locale) {
		this.locale = locale;
	}

	public String getPrivacidade() {
		return privacidade;
	}

	public void setPrivacidade(final String privacidade) {
		this.privacidade = privacidade;
	}

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(final Long categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("PublicacaoRequisicao [id=")
				.append(id)
				.append(", idVideo=")
				.append(idVideo)
				.append(", idThumbnail=")
				.append(idThumbnail)
				.append(", titulo=")
				.append(titulo)
				.append(", descricao=")
				.append(descricao)
				.append(", tags=")
				.append(tags)
				.append(", locale=")
				.append(locale)
				.append(", privacidade=")
				.append(privacidade)
				.append(", categoria=")
				.append(categoria)
				.append("]")
				.toString();
	}
}
