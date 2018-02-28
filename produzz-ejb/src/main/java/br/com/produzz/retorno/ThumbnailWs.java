package br.com.produzz.retorno;

import java.io.Serializable;

import br.com.produzz.entity.Thumbnail;

public class ThumbnailWs implements Serializable {
	private static final long serialVersionUID = 4856824189678267202L;

	private Long id;
	private byte[] imagem;

	public ThumbnailWs() { }

	public ThumbnailWs(final Thumbnail entity) {
		this.id = entity.getId();
		this.imagem = entity.getFile();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(final byte[] imagem) {
		this.imagem = imagem;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ThumbnailWs [id=")
				.append(id)
				.append(", imagem=")
				.append(imagem)
				.append("]");
		return builder.toString();
	}
}
