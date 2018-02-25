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

import br.com.produzz.entity.Categoria;
import br.com.produzz.entity.ContaCanal;
import br.com.produzz.enumeration.ECanal;
import br.com.produzz.exception.ProduzzException;
import br.com.produzz.requisicao.RedeSocialRequisicao;
import br.com.produzz.retorno.CategoriaRetorno;
import br.com.produzz.retorno.CategoriaWs;
import br.com.produzz.retorno.ContaCanalRetorno;
import br.com.produzz.retorno.ContaCanalWs;
import br.com.produzz.util.GenericService;
import br.com.produzz.util.Util;

@Stateless
@LocalBean
@Named
public class ContaCanalService extends GenericService implements Serializable {
	private static final long serialVersionUID = -5063094999286629751L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ContaCanalService.class);

	@PersistenceContext
	private EntityManager em;

	private static final String NENHUMA_CATEGORIA_FOI_LOCALIZADA = "Nenhuma categoria foi localizado.";

	public CategoriaRetorno getCategoria() throws Exception {
		LOGGER.info("getCategoria()");
		CategoriaRetorno retorno = new CategoriaRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT NR_CATEGORIA, NM_CATEGORIA")
				.append(" FROM PDZTB018_CATEGORIA");

		try {
			Query query = em.createNativeQuery(sql.toString(), Categoria.class);

			List<Categoria> lista = query
					.getResultList();

			if (!Util.isNull(lista) && !lista.isEmpty()) {
				for (Categoria item : lista) {
					retorno.getCategorias().add(new CategoriaWs(item));
				}
			}

		} catch (final NoResultException e) {
			LOGGER.error(NENHUMA_CATEGORIA_FOI_LOCALIZADA, e.getMessage());
			msgsErro.add(NENHUMA_CATEGORIA_FOI_LOCALIZADA);
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
			return retorno;

		} catch (final Exception e) {
			LOGGER.error("Exception:\n", e);
			msgsErro.add(e.getMessage());
			retorno.setMsgsErro(msgsErro);
			retorno.setTemErro(Boolean.TRUE);
			throw new Exception(e);
		}

		return retorno;
	}

	public ContaCanalRetorno findByConta(final Long conta) throws ProduzzException {
		LOGGER.info("findByConta(" + conta + ")");
		ContaCanalRetorno retorno = new ContaCanalRetorno();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT CC.NR_PDZ012, CC.FK_CONTA, CC.FK_CANAL, CC.ID_USUARIO, CC.ED_PAGINA, CC.DE_FOTO, CC.DE_LOCALE")
				.append(" , U.DE_NOME, U.DE_SOBRENOME, U.ED_ELETRONICO")
				.append(" FROM PDZTB012_CONTA_CANAL CC")
				.append(" INNER JOIN PDZTB005_CONTA_USUARIO CU ON CU.FK_CONTA = CC.FK_CONTA")
				.append(" INNER JOIN PDZTB001_USUARIO U ON U.NR_PDZ001 = CU.FK_USUARIO")
				.append(" WHERE CC.FK_CONTA = :conta");

		try {
			Query query = em.createNativeQuery(sql.toString(), ContaCanal.class);

			query.setParameter("conta", conta);

			List<ContaCanal> lista = query
					.getResultList();

			if (!Util.isNull(lista) && !lista.isEmpty()) {
				for (ContaCanal item : lista) {
					if (ECanal.FACEBOOK.getCodigo() == item.getCanal().intValue()) {
						retorno.setFacebookUser(new ContaCanalWs(item));
					}
					if (ECanal.GOOGLE.getCodigo() == item.getCanal().intValue()) {
						retorno.setGoogleUser(new ContaCanalWs(item));
					}
				}
			}

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public void incluir(final Long idConta, final Integer idCanal, RedeSocialRequisicao req) throws ProduzzException {
		LOGGER.info("incluir(" + idConta + ", " + idCanal + ", " + req + ")");
		StringBuilder sql = new StringBuilder("");
		sql.append("INSERT INTO PDZTB012_CONTA_CANAL")
				.append(" (FK_CONTA, FK_CANAL, TS_CRIACAO");

		if (!Util.isBlankOrNull(req.getId())) {
			sql.append(", ID_USUARIO");
		}

		if (!Util.isBlankOrNull(req.getLink())) {
			sql.append(", ED_PAGINA");
		}

		if (!Util.isBlankOrNull(req.getFoto())) {
			sql.append(", DE_FOTO");
		}

		if (!Util.isBlankOrNull(req.getLocale())) {
			sql.append(", DE_LOCALE");
		}

		sql.append(") VALUES (:conta, :canal, CURRENT_TIMESTAMP");

		if (!Util.isBlankOrNull(req.getId())) {
			sql.append(", :usuario");
		}

		if (!Util.isBlankOrNull(req.getLink())) {
			sql.append(", :link");
		}

		if (!Util.isBlankOrNull(req.getFoto())) {
			sql.append(", :foto");
		}

		if (!Util.isBlankOrNull(req.getLocale())) {
			sql.append(", :locale");
		}

		sql.append(")");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("conta", idConta)
					.setParameter("canal", idCanal);

			if (!Util.isBlankOrNull(req.getId())) {
				query.setParameter("usuario", req.getId());
			}

			if (!Util.isBlankOrNull(req.getLink())) {
				query.setParameter("link", req.getLink());
			}

			if (!Util.isBlankOrNull(req.getFoto())) {
				query.setParameter("foto", req.getFoto());
			}

			if (!Util.isBlankOrNull(req.getLocale())) {
				query.setParameter("locale", req.getLocale());
			}

			query.executeUpdate();

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}
	}

	public void excluir(final Long idConta, final Integer idCanal) throws ProduzzException {
		LOGGER.info("excluir(" + idConta + ", " + idCanal + ")");
		StringBuilder sql = new StringBuilder("");
		sql.append("DELETE FROM PDZTB012_CONTA_CANAL")
				.append(" WHERE FK_CONTA = :conta")
				.append(" AND FK_CANAL = :canal");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("conta", idConta)
					.setParameter("canal", idCanal);

			query.executeUpdate();

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}
	}
}
