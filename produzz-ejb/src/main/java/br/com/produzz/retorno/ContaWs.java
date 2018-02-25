package br.com.produzz.retorno;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.produzz.entity.Conta;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ContaWs implements Serializable {
	private static final long serialVersionUID = 4167807985925729711L;

	private Long id;
	private String status;
	private String empresa;
	private Integer ddi;
	private Integer ddd;
	private Long telefone;
	private Long cep;
	private String endereco;
	private Integer numero;
	private String complemento;
	private String municipio;
	private String estado;
	private String pais;
	private Long saldoDisponivel;
	private PlanoWs plano;
	private AtividadeEconomicaWs atividadeEconomica;

	public ContaWs() { }

	public ContaWs(final Long id) {
		this.id = id;
	}

	public ContaWs(final Conta conta) {
		this.id = conta.getId();
		this.status = conta.getStatus();
		this.empresa = conta.getEmpresa();
		this.ddi = conta.getDdi();
		this.ddd = conta.getDdd();
		this.telefone = conta.getTelefone();
		this.cep = conta.getCodigoPostal();
		this.endereco = conta.getEndereco();
		this.numero = conta.getNumero();
		this.complemento = conta.getComplemento();
		this.municipio = conta.getMunicipio();
		this.estado = conta.getEstado();
		this.pais = conta.getPais();
		this.saldoDisponivel = conta.getSaldoDisponivel();
		this.plano = new PlanoWs(conta.getPlano());
		this.atividadeEconomica = new AtividadeEconomicaWs(conta.getNrAtividadeEconomica(), conta.getNmAtividadeEconomica(), conta.getSegmento());
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(final String empresa) {
		this.empresa = empresa;
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

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(final Long telefone) {
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

	public Long getSaldoDisponivel() {
		return saldoDisponivel;
	}

	public void setSaldoDisponivel(final Long saldoDisponivel) {
		this.saldoDisponivel = saldoDisponivel;
	}

	public PlanoWs getPlano() {
		return plano;
	}

	public void setPlano(final PlanoWs plano) {
		this.plano = plano;
	}

	public AtividadeEconomicaWs getAtividadeEconomica() {
		return atividadeEconomica;
	}

	public void setAtividade(final AtividadeEconomicaWs atividadeEconomica) {
		this.atividadeEconomica = atividadeEconomica;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContaWs [id=")
				.append(id)
				.append(", status=")
				.append(status)
				.append(", empresa=")
				.append(empresa)
				.append(", ddi=")
				.append(ddi)
				.append(", ddd=")
				.append(ddd)
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
				.append(", saldoDisponivel=")
				.append(saldoDisponivel)
				.append(", plano=")
				.append(plano)
				.append(", atividadeEconomica=")
				.append(atividadeEconomica)
				.append("]");
		return builder.toString();
	}
}
