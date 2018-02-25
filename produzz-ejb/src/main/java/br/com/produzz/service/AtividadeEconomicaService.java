package br.com.produzz.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.entity.AtividadeEconomica;
import br.com.produzz.exception.ProduzzException;
import br.com.produzz.retorno.AtividadeEconomicaRetorno;
import br.com.produzz.retorno.AtividadeEconomicaWs;
import br.com.produzz.util.GenericService;
import br.com.produzz.util.Util;

@Stateless
@LocalBean
@Named
public class AtividadeEconomicaService extends GenericService implements Serializable {
	private static final long serialVersionUID = 7510818478802463226L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AtividadeEconomicaService.class);

	@PersistenceContext
	private EntityManager em;

	private static final String NENHUMA_ATIVIDADE_ECONOMICA_FOI_LOCALIZADA = "Nenhuma atividade economica foi localizada.";

	public AtividadeEconomicaRetorno listAll() throws ProduzzException {
		LOGGER.info("listAll()");
		AtividadeEconomicaRetorno retorno = new AtividadeEconomicaRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT AE.NR_PDZ009, AE.NM_ATIVIDADE_ECONOMICA, S.NM_SEGMENTO")
				.append(" FROM PDZTB009_ATIVIDADE_ECONOMICA AE")
				.append(" INNER JOIN PDZTB010_SEGMENTO S ON S.NR_PDZ010 = AE.FK_SEGMENTO")
				.append(" ORDER BY AE.FK_SEGMENTO, AE.NR_PDZ009");

		try {
			Query query = em.createNativeQuery(sql.toString(), AtividadeEconomica.class);

			List<AtividadeEconomica> lista = query
					.getResultList();

			if (Util.isNull(lista) || lista.isEmpty()) {
				LOGGER.error(NENHUMA_ATIVIDADE_ECONOMICA_FOI_LOCALIZADA);
				msgsErro.add(NENHUMA_ATIVIDADE_ECONOMICA_FOI_LOCALIZADA);
				retorno.setTemErro(Boolean.TRUE);
				retorno.setMsgsErro(msgsErro);
				return retorno;

			} else {
				for (AtividadeEconomica item : lista) {
					retorno.getAtividades().add(new AtividadeEconomicaWs(item));
				}
			}

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}
}
