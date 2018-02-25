package br.com.produzz.retorno;

import java.io.Serializable;

import br.com.produzz.entity.Video;

public class VideoWs implements Serializable {
	private static final long serialVersionUID = 7734744683960013116L;

	private Long id;
	private String nome;
	private byte[] imagem;

	public VideoWs() { }

	public VideoWs(final Video entity) {
		this.id = entity.getId();
		this.nome = entity.getFilename();
		this.imagem = entity.getFile();
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

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(final byte[] imagem) {
		this.imagem = imagem;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VideoWs [id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append(", imagem=")
				.append(imagem)
				.append("]");
		return builder.toString();
	}
}
