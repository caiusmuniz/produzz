package br.com.produzz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class ContaCanal implements Serializable {
	private static final long serialVersionUID = -828988074811802671L;

	@Id
	@Column(name = "NR_PDZ012")
	private Long id;

	@NotNull
	@Column(name = "FK_CONTA")
	private Long conta;

	@NotNull
	@Column(name = "FK_CANAL")
	private Long canal;

	@Column(name = "ID_USUARIO")
	private String idUsuario;

	@Column(name = "ED_PAGINA")
	private String pagina;

	@Column(name = "DE_FOTO")
	private String foto;

	@Column(name = "DE_LOCALE")
	private String locale;

	@Column(name = "DE_NOME")
	private String nome;

	@Column(name = "DE_SOBRENOME")
	private String sobrenome;

	@Column(name = "ED_ELETRONICO")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getConta() {
		return conta;
	}

	public void setConta(final Long conta) {
		this.conta = conta;
	}

	public Long getCanal() {
		return canal;
	}

	public void setCanal(final Long canal) {
		this.canal = canal;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(final String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(final String pagina) {
		this.pagina = pagina;
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
		return builder.append("ContaCanal [")
				.append("id=")
				.append(id)
				.append(", conta=")
				.append(conta)
				.append(", canal=")
				.append(canal)
				.append(", idUsuario=")
				.append(idUsuario)
				.append(", pagina=")
				.append(pagina)
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
				.append("]")
				.toString();
	}
}
