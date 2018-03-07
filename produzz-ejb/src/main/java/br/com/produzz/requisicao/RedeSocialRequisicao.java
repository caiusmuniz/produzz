package br.com.produzz.requisicao;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

/**.
 * Projeto PRODUZZ
 * RedeSocialRequisicao
 * @author Caius Muniz
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class RedeSocialRequisicao implements Serializable {
	private static final long serialVersionUID = 6426382083544751432L;

	protected Long idUsuario;

	protected Long idConta;

	@NotNull
	@Size(max = 64)
	protected String id;

	@NotNull
	@Size(max = 120)
	@Pattern(regexp = "[A-Za-z ]*", message = "deve conter apenas letras e espaços")
	protected String nome;

	@NotNull
	@Size(max = 120)
	@Pattern(regexp = "[A-Za-z ]*", message = "deve conter apenas letras e espaços")
	protected String sobrenome;

	@NotNull
	@Email
	@Size(max = 120)
	protected String email;

	@Size(max = 256)
	protected String link;

	@Size(max = 256)
	protected String foto;

	@Size(max = 16)
	protected String locale;

	@Size(max = 1024)
	protected String idToken;

	@Size(max = 2048)
	protected String tokenAcesso;

	@Size(max = 2048)
	protected String tokenRenovacao;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(final Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(final Long idConta) {
		this.idConta = idConta;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
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

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(final String idToken) {
		this.idToken = idToken;
	}

	public String getTokenAcesso() {
		return tokenAcesso;
	}

	public void setTokenAcesso(final String tokenAcesso) {
		this.tokenAcesso = tokenAcesso;
	}

	public String getTokenRenovacao() {
		return tokenRenovacao;
	}

	public void setTokenRenovacao(final String tokenRenovacao) {
		this.tokenRenovacao = tokenRenovacao;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("RedeSocialRequisicao [idUsuario=")
				.append(idUsuario)
				.append(", idConta=")
				.append(idConta)
				.append(", id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append(", sobrenome=")
				.append(sobrenome)
				.append(", email=")
				.append(email)
				.append(", link=")
				.append(link)
				.append(", foto=")
				.append(foto)
				.append(", locale=")
				.append(locale)
				.append(", idToken=")
				.append(idToken)
				.append(", tokenAcesso=")
				.append(tokenAcesso)
				.append(", tokenRenovacao=")
				.append(tokenRenovacao)
				.append("]")
				.toString();
	}
}
