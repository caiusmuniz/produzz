package br.com.produzz.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.entity.Conta;
import br.com.produzz.enumeration.EAtivo;
import br.com.produzz.exception.ProduzzException;
import br.com.produzz.requisicao.ContaRequisicao;
import br.com.produzz.retorno.ContaRetorno;
import br.com.produzz.retorno.ContaWs;
import br.com.produzz.retorno.Retorno;
import br.com.produzz.util.GenericService;
import br.com.produzz.util.Util;

@Stateless
@LocalBean
@Named
public class ContaService extends GenericService implements Serializable {
	private static final long serialVersionUID = 7659225099500759553L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ContaService.class);

	@PersistenceContext
	private EntityManager em;

	private static final String NENHUMA_CONTA_FOI_LOCALIZADA_PARA_O_USUARIO_INFORMADOS = "Nenhuma conta foi localizada para o usuario informado.";

	@SuppressWarnings("unchecked")
	public ContaRetorno findAll() throws Exception {
		LOGGER.info("findAll()");
		ContaRetorno retorno = new ContaRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT C.NR_PDZ003, C.IC_ATIVO, C.DE_CONTA, C.NM_EMPRESA")
				.append(", C.DE_ENDERECO, C.NR_ENDERECO, C.DE_COMPLEMENTO, C.NM_MUNICIPIO, C.NM_ESTADO, C.NM_PAIS, C.NR_CODIGO_POSTAL")
				.append(", C.NR_DDI, C.NR_DDD, C.NR_TELEFONE")
				.append(", C.QT_SALDO_DISPONIVEL, C.NR_HOLDING, C.FK_PLANO")
				.append(", A.NR_PDZ009, A.NM_ATIVIDADE_ECONOMICA, S.NM_SEGMENTO")
				.append(" FROM PDZTB003_CONTA C")
				.append(" LEFT JOIN PDZTB009_ATIVIDADE_ECONOMICA A ON A.NR_PDZ009 = C.FK_ATIVIDADE_ECONOMICA")
				.append(" LEFT JOIN PDZTB010_SEGMENTO S ON S.NR_PDZ010 = A.FK_SEGMENTO");

		try {
			Query query = em.createNativeQuery(sql.toString(), Conta.class);

			List<Conta> lista = query
					.getResultList();

			if (Util.isNull(lista) || lista.isEmpty()) {
				LOGGER.error(NENHUMA_CONTA_FOI_LOCALIZADA_PARA_O_USUARIO_INFORMADOS);
				msgsErro.add(NENHUMA_CONTA_FOI_LOCALIZADA_PARA_O_USUARIO_INFORMADOS);
				retorno.setTemErro(Boolean.TRUE);
				retorno.setMsgsErro(msgsErro);
				return retorno;

			} else {
				for (Conta item : lista) {
					retorno.getContas().add(new ContaWs(item));
				}
			}

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public ContaRetorno findById(final Long id) throws ProduzzException {
		LOGGER.info("findById(" + id + ")");
		ContaRetorno retorno = new ContaRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT C.NR_PDZ003, C.IC_ATIVO, C.DE_CONTA, C.NM_EMPRESA")
				.append(", C.DE_ENDERECO, C.NR_ENDERECO, C.DE_COMPLEMENTO, C.NM_MUNICIPIO, C.NM_ESTADO, C.NM_PAIS, C.NR_CODIGO_POSTAL")
				.append(", C.NR_DDI, C.NR_DDD, C.NR_TELEFONE")
				.append(", C.QT_SALDO_DISPONIVEL, C.NR_HOLDING, C.FK_PLANO")
				.append(", A.NR_PDZ009, A.NM_ATIVIDADE_ECONOMICA, S.NM_SEGMENTO")
				.append(" FROM PDZTB003_CONTA C")
				.append(" LEFT JOIN PDZTB009_ATIVIDADE_ECONOMICA A ON A.NR_PDZ009 = C.FK_ATIVIDADE_ECONOMICA")
				.append(" LEFT JOIN PDZTB010_SEGMENTO S ON S.NR_PDZ010 = A.FK_SEGMENTO")
				.append(" WHERE C.NR_PDZ003 = :conta");

		try {
			Query query = em.createNativeQuery(sql.toString(), Conta.class);

			Conta entity = (Conta) query
					.setParameter("conta", id)
					.getSingleResult();

			retorno.getContas().add(new ContaWs(entity));

		} catch (final NoResultException e) {
			LOGGER.error(NENHUMA_CONTA_FOI_LOCALIZADA_PARA_O_USUARIO_INFORMADOS, e.getMessage());
			msgsErro.add(NENHUMA_CONTA_FOI_LOCALIZADA_PARA_O_USUARIO_INFORMADOS);
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
			return retorno;

		} catch (final Exception e) {
			LOGGER.error("Exception: " + e);
			throw new ProduzzException(e);
		}		

		return retorno;
	}

	public Long findByDescricao(final String descricao) throws ProduzzException {
		LOGGER.info("findByDescricao(" + descricao + ")");
		Long retorno = null;

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT C.NR_PDZ003, C.IC_ATIVO, C.DE_CONTA, C.NM_EMPRESA")
				.append(", C.DE_ENDERECO, C.NR_ENDERECO, C.DE_COMPLEMENTO, C.NM_MUNICIPIO, C.NM_ESTADO, C.NM_PAIS, C.NR_CODIGO_POSTAL")
				.append(", C.NR_DDI, C.NR_DDD, C.NR_TELEFONE")
				.append(", C.QT_SALDO_DISPONIVEL, C.NR_HOLDING, C.FK_PLANO")
				.append(", A.NR_PDZ009, A.NM_ATIVIDADE_ECONOMICA, S.NM_SEGMENTO")
				.append(" FROM PDZTB003_CONTA C")
				.append(" LEFT JOIN PDZTB009_ATIVIDADE_ECONOMICA A ON A.NR_PDZ009 = C.FK_ATIVIDADE_ECONOMICA")
				.append(" LEFT JOIN PDZTB010_SEGMENTO S ON S.NR_PDZ010 = A.FK_SEGMENTO")
				.append(" WHERE C.DE_CONTA = :descricao");

		try {
			Query query = em.createNativeQuery(sql.toString(), Conta.class);

			Conta entity = (Conta) query
					.setParameter("descricao", descricao)
					.getSingleResult();

			retorno = entity.getId();

		} catch (final NoResultException e) {
			LOGGER.error(NENHUMA_CONTA_FOI_LOCALIZADA_PARA_O_USUARIO_INFORMADOS, e.getMessage());

		} catch (final Exception e) {
			LOGGER.error("Exception: " + e);
			throw new ProduzzException(e);
		}		

		return retorno;
	}

	public List<Conta> findByUsuario(final Long usuario, final String status) throws ProduzzException {
		LOGGER.info("findByUsuario(" + usuario + ", " + status + ")");
		List<Conta> retorno = null;

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT C.NR_PDZ003, C.IC_ATIVO, C.DE_CONTA, C.NM_EMPRESA")
				.append(", C.DE_ENDERECO, C.NR_ENDERECO, C.DE_COMPLEMENTO, C.NM_MUNICIPIO, C.NM_ESTADO, C.NM_PAIS, C.NR_CODIGO_POSTAL")
				.append(", C.NR_DDI, C.NR_DDD, C.NR_TELEFONE")
				.append(", C.QT_SALDO_DISPONIVEL, C.NR_HOLDING, C.FK_PLANO")
				.append(", A.NR_PDZ009, A.NM_ATIVIDADE_ECONOMICA, S.NM_SEGMENTO")
				.append(" FROM PDZTB003_CONTA C")
				.append(" INNER JOIN PDZTB005_CONTA_USUARIO CN ON CN.FK_CONTA = C.NR_PDZ003")
				.append(" LEFT JOIN PDZTB009_ATIVIDADE_ECONOMICA A ON A.NR_PDZ009 = C.FK_ATIVIDADE_ECONOMICA")
				.append(" LEFT JOIN PDZTB010_SEGMENTO S ON S.NR_PDZ010 = A.FK_SEGMENTO")
				.append(" WHERE CN.FK_USUARIO = :usuario")
				.append(" AND CN.IC_PRINCIPAL = :status");

		try {
			Query query = em.createNativeQuery(sql.toString(), Conta.class);

			retorno = query
					.setParameter("usuario", usuario)
					.setParameter("status", status)
					.getResultList();

		} catch (final Exception e) {
			LOGGER.error("Exception: " + e);
			throw new ProduzzException(e);
		}		

		return retorno;
	}

	public void vincularUsuario(final Long conta, final Long usuario) throws ProduzzException {
		LOGGER.info("vincularUsuario(" + conta + ", " + usuario + ")");
		StringBuilder sql = new StringBuilder("");
		sql.append("INSERT INTO PDZTB005_CONTA_USUARIO")
				.append(" (FK_CONTA, FK_USUARIO, IC_PRINCIPAL)")
				.append(" VALUES (:conta, :usuario, :status)");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("conta", conta)
					.setParameter("usuario", usuario)
					.setParameter("status", EAtivo.ATIVO.getCodigo());

			query.executeUpdate();

		} catch (final Exception e) {
			LOGGER.error("Exception: " + e);
			throw new ProduzzException(e);
		}		
	}

	public Retorno incluir(final Long usuario) throws ProduzzException {
		LOGGER.info("incluir(" + usuario + ")");
		Retorno retorno = new Retorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("INSERT INTO PDZTB003_CONTA")
				.append(" (DE_CONTA, IC_ATIVO, TS_CRIACAO)")
				.append(" VALUES (:nome, :status, CURRENT_TIMESTAMP)");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("nome", usuario)
					.setParameter("status", EAtivo.ATIVO.getCodigo());

			query.executeUpdate();

			msgsErro.add("Email enviado com sucesso. Verifique sua caixa de correio.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public Retorno alterar(final ContaRequisicao conta) throws ProduzzException {
		LOGGER.info("alterar(" + conta + ")");
		Retorno retorno = new Retorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("UPDATE PDZTB003_CONTA")
				.append(" SET TS_ATUALIZACAO = CURRENT_TIMESTAMP, NM_EMPRESA = :empresa, NR_DDI = :ddi, NR_DDD = :ddd, NR_TELEFONE = :telefone, NR_CODIGO_POSTAL = :cep, ")
				.append(" DE_ENDERECO = :endereco, NR_ENDERECO = :numero, DE_COMPLEMENTO = :complemento, NM_MUNICIPIO = :municipio, NM_ESTADO = :estado, NM_PAIS = :pais, ");

		if (Util.isNull(conta.getAtividadeEconomica())) {
			sql.append(" FK_ATIVIDADE_ECONOMICA = NULL");

		} else {
			sql.append(" FK_ATIVIDADE_ECONOMICA = :atividadeEconomica");
		}

		sql.append(" WHERE NR_PDZ003 = :id");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("id", conta.getId())
					.setParameter("empresa", conta.getEmpresa())
					.setParameter("ddi", conta.getDdi())
					.setParameter("ddd", conta.getDdd())
					.setParameter("telefone", conta.getTelefone())
					.setParameter("cep", conta.getCep())
					.setParameter("endereco", conta.getEndereco())
					.setParameter("numero", conta.getNumero())
					.setParameter("complemento", conta.getComplemento())
					.setParameter("municipio", conta.getMunicipio())
					.setParameter("estado", conta.getEstado())
					.setParameter("pais", conta.getPais());

			if (!Util.isNull(conta.getAtividadeEconomica())) {
				query.setParameter("atividadeEconomica", conta.getAtividadeEconomica());
			}

			query.executeUpdate();

			msgsErro.add("Conta atualizada com sucesso.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}
}
