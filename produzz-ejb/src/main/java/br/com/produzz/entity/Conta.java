package br.com.produzz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Conta implements Serializable {
	private static final long serialVersionUID = -6835208620629849424L;

	@Id
	@Column(name = "NR_PDZ003")
	private Long id;

	@NotNull
	@Size(min = 1, max = 1)
	@Column(name = "IC_ATIVO")
	private String status;

	@Column(name = "DE_CONTA")
	private String descricao;

	@Column(name = "NM_EMPRESA")
	private String empresa;

	@Column(name = "DE_ENDERECO")
	private String endereco;

	@Column(name = "NR_ENDERECO")
	private Integer numero;

	@Column(name = "DE_COMPLEMENTO")
	private String complemento;

	@Column(name = "NM_MUNICIPIO")
	private String municipio;

	@Column(name = "NM_ESTADO")
	private String estado;

	@Column(name = "NM_PAIS")
	private String pais;

	@Column(name = "NR_CODIGO_POSTAL")
	private Long codigoPostal;

	@Column(name = "NR_DDI")
	private Integer ddi;

	@Column(name = "NR_DDD")
	private Integer ddd;

	@Column(name = "NR_TELEFONE")
	private Long telefone;

	@Column(name = "QT_SALDO_DISPONIVEL")
	private Long saldoDisponivel;

	@Column(name = "NR_HOLDING")
	private Long holding;

	@Column(name = "FK_PLANO")
	private Long plano;

	@Column(name = "NR_PDZ009")
	private Long nrAtividadeEconomica;

	@Column(name = "NM_ATIVIDADE_ECONOMICA")
	private String nmAtividadeEconomica;

	@Column(name = "NM_SEGMENTO")
	private String segmento;

	public Conta() { }

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(final String empresa) {
		this.empresa = empresa;
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

	public Long getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(final Long codigoPostal) {
		this.codigoPostal = codigoPostal;
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

	public Long getSaldoDisponivel() {
		return saldoDisponivel;
	}

	public void setSaldoDisponivel(final Long saldoDisponivel) {
		this.saldoDisponivel = saldoDisponivel;
	}

	public Long getHolding() {
		return holding;
	}

	public void setHolding(final Long holding) {
		this.holding = holding;
	}

	public Long getPlano() {
		return plano;
	}

	public void setPlano(final Long plano) {
		this.plano = plano;
	}

	public Long getNrAtividadeEconomica() {
		return nrAtividadeEconomica;
	}

	public void setNrAtividadeEconomica(final Long nrAtividadeEconomica) {
		this.nrAtividadeEconomica = nrAtividadeEconomica;
	}

	public String getNmAtividadeEconomica() {
		return nmAtividadeEconomica;
	}

	public void setNmAtividadeEconomica(final String nmAtividadeEconomica) {
		this.nmAtividadeEconomica = nmAtividadeEconomica;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(final String segmento) {
		this.segmento = segmento;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("Conta [")
				.append("id=")
				.append(id)
				.append(", descricao=")
				.append(descricao)
				.append(", empresa=")
				.append(empresa)
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
				.append(", codigoPostal=")
				.append(codigoPostal)
				.append(", ddi=")
				.append(ddi)
				.append(", ddd=")
				.append(ddd)
				.append(", telefone=")
				.append(telefone)
				.append(", saldoDisponivel=")
				.append(saldoDisponivel)
				.append(", holding=")
				.append(holding)
				.append(", plano=")
				.append(plano)
				.append(", nrAtividadeEconomica=")
				.append(nrAtividadeEconomica)
				.append(", nmAtividadeEconomica=")
				.append(nmAtividadeEconomica)
				.append(", segmento=")
				.append(segmento)
				.append("]")
				.toString();
	}
}
