package br.com.produzz.requisicao;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**.
 * Projeto PRODUZZ
 * ContaRequisicao
 * @author Caius Muniz
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ContaRequisicao extends Requisicao implements Serializable {
	private static final long serialVersionUID = 5794449486701810860L;

	@Size(max = 11)
	protected Long id;

	@Size(max = 128)
	protected String empresa;

	@Size(max = 11)
	protected Long atividadeEconomica;

	@Size(max = 2)
	protected Integer ddi;

	@Size(max = 2)
	protected Integer ddd;

	@Size(max = 9)
	protected String telefone;

	@NotNull
	@Size(max = 8)
	protected Long cep;

	@NotNull
	@Size(max = 128)
	protected String endereco;

	@Size(max = 8)
	protected Integer numero;

	@Size(max = 64)
	protected String complemento;

	@Size(max = 128)
	protected String municipio;

	@Size(max = 64)
	protected String estado;

	@Size(max = 45)
	protected String pais;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(final String empresa) {
		this.empresa = empresa;
	}

	public Long getAtividadeEconomica() {
		return atividadeEconomica;
	}

	public void setAtividadeEconomica(final Long atividadeEconomica) {
		this.atividadeEconomica = atividadeEconomica;
	}

	public Integer getDdi() {
		return ddi;
	}

	public void setDdi(final Integer ddi) {
		this.ddi = ddi;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(final Integer ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(final String telefone) {
		this.telefone = telefone;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(final Long cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(final String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(final Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(final String complemento) {
		this.complemento = complemento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(final String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(final String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(final String pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("ContaRequisicao [id=")
				.append(id)
				.append(", empresa=")
				.append(empresa)
				.append(", atividadeEconomica=")
				.append(atividadeEconomica)
				.append(", telefone=")
				.append(telefone)
				.append(", cep=")
				.append(cep)
				.append(", endereco=")
				.append(endereco)
				.append(", numero=")
				.append(numero)
				.append(", complemento=")
				.append(complemento)
				.append(", municipio=")
				.append(municipio)
				.append(", estado=")
				.append(estado)
				.append(", pais=")
				.append(pais)
				.append("]")
				.toString();
	}
}
