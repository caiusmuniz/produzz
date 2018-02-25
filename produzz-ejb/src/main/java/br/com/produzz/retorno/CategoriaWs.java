package br.com.produzz.retorno;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.produzz.entity.Categoria;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriaWs implements Serializable {
	private static final long serialVersionUID = -5208237346876083603L;

	private Integer id;
	private String nome;

	public CategoriaWs() { }

	public CategoriaWs(final Integer id) {
		this.id = id;
	}

	public CategoriaWs(final Integer id, final String nome) {
		this.id = id;
		this.nome = nome;
	}

	public CategoriaWs(final Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}

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
		builder.append("CategoriaWs [id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append("]");
		return builder.toString();
	}
}
