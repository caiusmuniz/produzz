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
 * UsuarioRequisicao
 * @author Caius Muniz
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class UsuarioRequisicao extends Requisicao implements Serializable {
	private static final long serialVersionUID = 1905826255926745870L;

	@Size(max = 11)
	protected Long id;

	@Size(max = 120)
	@Pattern(regexp = "[A-Za-z ]*", message = "deve conter apenas letras e espaços")
	protected String nome;

	@Size(max = 120)
	@Pattern(regexp = "[A-Za-z ]*", message = "deve conter apenas letras e espaços")
	protected String sobrenome;

	@NotNull
	@Email
	@Size(max = 255)
	protected String login;

	@NotNull
	@Size(max = 64)
	protected String senha;

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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(final String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(final String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("UsuarioRequisicao [id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append(", sobrenome=")
				.append(sobrenome)
				.append(", login=")
				.append(login)
				.append(", senha=")
				.append(senha)
				.append("]")
				.toString();
	}
}
