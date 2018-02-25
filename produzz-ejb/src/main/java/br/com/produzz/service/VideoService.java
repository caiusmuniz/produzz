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

import br.com.produzz.entity.Video;
import br.com.produzz.exception.ProduzzException;
import br.com.produzz.retorno.VideoRetorno;
import br.com.produzz.retorno.VideoWs;
import br.com.produzz.util.Constantes;
import br.com.produzz.util.GenericService;
import br.com.produzz.util.Util;

@Stateless
@LocalBean
@Named
public class VideoService extends GenericService implements Serializable {
	private static final long serialVersionUID = -6930924049657264044L;

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoService.class);

	@PersistenceContext
	private EntityManager em;

	private static final String NENHUM_VIDEO_FOI_LOCALIZADO = "Nenhum video foi localizado.";

	public VideoRetorno incluir(final Long idConta, final String filename, final byte[] imagem) throws ProduzzException {
		LOGGER.info("incluir(" + idConta + ", " + filename + ", " + imagem.length + ")");
		VideoRetorno retorno = new VideoRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("INSERT INTO PDZTB019_VIDEO")
				.append(" (NM_VIDEO, IM_VIDEO, FK_CONTA, TS_CRIACAO)")
				.append(" VALUES (:nome, :imagem, :conta, CURRENT_TIMESTAMP)");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("nome", filename)
					.setParameter("imagem", imagem)
					.setParameter("conta", idConta);

			query.executeUpdate();

			retorno = this.findByNomeConta(filename, idConta);

			msgsErro.add("Upload de video com sucesso.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public VideoRetorno findByNomeConta(final String nome, final Long idConta) throws Exception {
		LOGGER.info("findByNomeConta(" + nome + ", " + idConta + ")");
		VideoRetorno retorno = new VideoRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT NR_PDZ019, NM_VIDEO, IM_VIDEO")
				.append(" FROM PDZTB019_VIDEO")
				.append(" WHERE FK_CONTA = :conta")
				.append(" AND NM_VIDEO = :nome")
				.append(" ORDER BY 1 DESC");

		try {
			Query query = em.createNativeQuery(sql.toString(), Video.class);

			List<Video> lista = query
					.setParameter("conta", idConta)
					.setParameter("nome", nome)
					.getResultList();

			if (Util.isNull(lista) || lista.isEmpty()) {
				LOGGER.error(NENHUM_VIDEO_FOI_LOCALIZADO);
				msgsErro.add(NENHUM_VIDEO_FOI_LOCALIZADO);
				retorno.setTemErro(Boolean.TRUE);
				retorno.setMsgsErro(msgsErro);
				return retorno;

			} else {
				retorno.getVideos().add(new VideoWs(lista.get(0)));
			}

			msgsErro.add(Constantes.OPERACAO_OK);
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public VideoRetorno incluirThumbnail(final Long idConta, final String filename, final byte[] imagem) throws ProduzzException {
		LOGGER.info("incluir(" + idConta + ", " + filename + ", " + imagem.length + ")");
		VideoRetorno retorno = new VideoRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("INSERT INTO PDZTB020_THUMBNAIL")
				.append(" (IM_THUMBNAIL, FK_CONTA, TS_CRIACAO)")
				.append(" VALUES (:imagem, :conta, CURRENT_TIMESTAMP)");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("imagem", imagem)
					.setParameter("conta", idConta);

			query.executeUpdate();

			msgsErro.add("Upload de thumbnail com sucesso.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}
}
