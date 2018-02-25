package br.com.produzz.retorno;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.produzz.entity.ContaCanal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ContaCanalWs implements Serializable {
	private static final long serialVersionUID = -6912079473903748564L;

	private String id;
	private String link;
	private String foto;
	private String locale;
	private String nome;
	private String sobrenome;
	private String email;

	public ContaCanalWs(final ContaCanal item) {
		this.id = item.getIdUsuario();
		this.link = item.getPagina();
		this.foto = item.getFoto();
		this.locale = item.getLocale();
		this.nome = item.getNome();
		this.sobrenome = item.getSobrenome();
		this.email = item.getEmail();
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(final String foto) {
		this.foto = foto;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(final String locale) {
		this.locale = locale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(final String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContaCanalWs [id=")
				.append(id)
				.append(", link=")
				.append(link)
				.append(", foto=")
				.append(foto)
				.append(", locale=")
				.append(locale)
				.append(", nome=")
				.append(nome)
				.append(", sobrenome=")
				.append(sobrenome)
				.append(", email=")
				.append(email)
				.append("]");
		return builder.toString();
	}
}
