package br.com.produzz.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
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
import br.com.produzz.entity.Plano;
import br.com.produzz.enumeration.EAtivo;
import br.com.produzz.retorno.ContaRetorno;
import br.com.produzz.retorno.ContaWs;
import br.com.produzz.retorno.PlanoRetorno;
import br.com.produzz.retorno.PlanoWs;
import br.com.produzz.util.GenericService;
import br.com.produzz.util.Util;

@Stateless
@LocalBean
@Named
public class DashboardService extends GenericService implements Serializable {
	private static final long serialVersionUID = 4180367105891100366L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardService.class);

	@PersistenceContext
	private EntityManager em;

	@EJB
	private ContaService contaService;

	private static final String NENHUM_PLANO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS = "Nenhum plano foi localizado para os parametros informados.";

	public PlanoRetorno getPlano(final Long conta) throws Exception {
		LOGGER.info("getDashboard(" + conta + ")");
		PlanoRetorno retorno = new PlanoRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT P.NR_PDZ008, P.NM_PLANO, P.VR_PLANO, P.VR_LIQUIDO, P.VR_VIDEO, P.QT_VIDEO")
				.append(" FROM PDZTB003_CONTA C")
				.append(" INNER JOIN PDZTB008_PLANO P ON P.NR_PDZ008 = C.FK_PLANO")
				.append(" WHERE C.NR_PDZ003 = :conta");

		try {
			Query query = em.createNativeQuery(sql.toString(), Plano.class);

			Plano entity = (Plano) query
					.setParameter("conta", conta)
					.getSingleResult();

			retorno.getPlanos().add(new PlanoWs(entity));

		} catch (final NoResultException e) {
			LOGGER.error(NENHUM_PLANO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS, e.getMessage());
			msgsErro.add(NENHUM_PLANO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS);
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

	public ContaRetorno getContas(final Long idUsuario) throws Exception {
		LOGGER.info("getContas(" + idUsuario + ")");
		ContaRetorno retorno = new ContaRetorno();
		List<String> msgsErro = new ArrayList<String>();

		try {
			List<Conta> lista = contaService.findByUsuario(idUsuario, EAtivo.INATIVO.getCodigo());

			if (!Util.isNull(lista) && lista.size() > 0) {
				for (Conta item : lista) {
					retorno.getContas().add(new ContaWs(item));
				}
			}

		} catch (final Exception e) {
			LOGGER.error("Exception:\n", e);
			msgsErro.add(e.getMessage());
			retorno.setMsgsErro(msgsErro);
			retorno.setTemErro(Boolean.TRUE);
			throw new Exception(e);
		}

		return retorno;
	}
}
