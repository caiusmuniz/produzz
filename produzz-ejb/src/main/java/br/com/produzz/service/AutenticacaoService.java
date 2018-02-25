package br.com.produzz.service;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.exception.ProduzzException;
import br.com.produzz.util.GenericService;
import br.com.produzz.util.Util;

@Stateless
@LocalBean
@Named
public class AutenticacaoService extends GenericService implements Serializable {
	private static final long serialVersionUID = -2165952664703117111L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoService.class);

	@PersistenceContext
	private EntityManager em;

	public void incluir(final Long idUsuario, final Integer sistema, final String idToken, final String tokenAcesso) throws ProduzzException {
		LOGGER.info("incluir(" + idUsuario + ", " + sistema + ", " + idToken + ", " + tokenAcesso +")");
		StringBuilder sql = new StringBuilder("");
		sql.append("INSERT INTO PDZTB017_AUTENTICACAO")
				.append(" (FK_USUARIO, FK_SISTEMA_AUTENTICADOR, TS_AUTENTICACAO");

		if (!Util.isBlankOrNull(idToken)) {
			sql.append(", ID_TOKEN");
		}

		if (!Util.isBlankOrNull(tokenAcesso)) {
			sql.append(", DE_TOKEN_ACESSO");
		}

		sql.append(") VALUES (:usuario, :sistema, CURRENT_TIMESTAMP");

		if (!Util.isBlankOrNull(idToken)) {
			sql.append(", :idToken");
		}

		if (!Util.isBlankOrNull(tokenAcesso)) {
			sql.append(", :tokenAcesso");
		}

		sql.append(")");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("usuario", idUsuario)
					.setParameter("sistema", sistema);

			if (!Util.isBlankOrNull(idToken)) {
				query.setParameter("idToken", idToken);
			}

			if (!Util.isBlankOrNull(tokenAcesso)) {
				query.setParameter("tokenAcesso", tokenAcesso);
			}

			query.executeUpdate();

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}
	}
}
