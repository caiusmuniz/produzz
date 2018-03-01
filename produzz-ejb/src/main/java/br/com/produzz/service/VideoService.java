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

import br.com.produzz.entity.Thumbnail;
import br.com.produzz.entity.Video;
import br.com.produzz.exception.ProduzzException;
import br.com.produzz.requisicao.PublicacaoRequisicao;
import br.com.produzz.retorno.Retorno;
import br.com.produzz.retorno.ThumbnailRetorno;
import br.com.produzz.retorno.ThumbnailWs;
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
	private static final String NENHUM_THUMBNAIL_FOI_LOCALIZADO = "Nenhum thumbnail foi localizado.";

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

			retorno = this.findByNomeConta(filename, idConta, 1);

			msgsErro.add("Upload de video com sucesso.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public VideoRetorno findByNomeConta(final String nome, final Long idConta, final Integer limite) throws Exception {
		LOGGER.info("findByNomeConta(" + nome + ", " + idConta + ")");
		VideoRetorno retorno = new VideoRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT NR_PDZ019, NM_VIDEO, IM_VIDEO")
				.append(" FROM PDZTB019_VIDEO")
				.append(" WHERE FK_CONTA = :conta")
				.append(" AND NM_VIDEO = :nome")
				.append(" ORDER BY 1 DESC");

		if (Util.isBlankOrNull(limite)) {
			sql.append(" LIMIT " + limite);
		}

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

	public ThumbnailRetorno incluirThumbnail(final Long idConta, final Long idVideo, final String filename, final byte[] imagem) throws ProduzzException {
		LOGGER.info("incluir(" + idConta + ", " + idVideo + ", " + filename + ", " + imagem.length + ")");
		ThumbnailRetorno retorno = new ThumbnailRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("INSERT INTO PDZTB020_THUMBNAIL")
				.append(" (IM_THUMBNAIL, FK_CONTA, FK_VIDEO, TS_CRIACAO)")
				.append(" VALUES (:imagem, :conta, :video, CURRENT_TIMESTAMP)");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("imagem", imagem)
					.setParameter("conta", idConta)
					.setParameter("video", idVideo);

			query.executeUpdate();

			retorno = this.findThumbnailByContaVideo(idConta, idVideo);

			msgsErro.add("Upload de thumbnail com sucesso.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public ThumbnailRetorno findThumbnailByContaVideo(final Long idConta, final Long idVideo) throws Exception {
		LOGGER.info("findThumbnailByContaVideo(" + idConta + ", " + idVideo + ")");
		ThumbnailRetorno retorno = new ThumbnailRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT NR_PDZ020, IM_THUMBNAIL")
				.append(" FROM PDZTB020_THUMBNAIL")
				.append(" WHERE FK_CONTA = :conta")
				.append(" AND FK_VIDEO = :video")
				.append(" ORDER BY 1 DESC");

		try {
			Query query = em.createNativeQuery(sql.toString(), Thumbnail.class);

			List<Thumbnail> lista = query
					.setParameter("conta", idConta)
					.setParameter("video", idVideo)
					.getResultList();

			if (Util.isNull(lista) || lista.isEmpty()) {
				LOGGER.error(NENHUM_THUMBNAIL_FOI_LOCALIZADO);
				msgsErro.add(NENHUM_THUMBNAIL_FOI_LOCALIZADO);
				retorno.setTemErro(Boolean.TRUE);
				retorno.setMsgsErro(msgsErro);
				return retorno;

			} else {
				retorno.getThumbnails().add(new ThumbnailWs(lista.get(0)));
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

	public Retorno publicar(final PublicacaoRequisicao req, final Long contaCanal) throws ProduzzException {
		LOGGER.info("publicar(" + req + ", " + contaCanal + ")");
		Retorno retorno = new Retorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("INSERT INTO PDZTB021_PUBLICACAO")
				.append(" (NM_TITULO, IC_PRIVACIDADE, FK_VIDEO, FK_THUMBNAIL, FK_CONTA_CANAL, FK_CATEGORIA, TS_CRIACAO");

		if (!Util.isBlankOrNull(req.getDescricao())) {
			sql.append(" , DE_VIDEO");
		}

		if (!Util.isBlankOrNull(req.getTags())) {
			sql.append(" , DE_TAGS");
		}

		if (!Util.isBlankOrNull(req.getLocale())) {
			sql.append(" , CO_LOCALE");
		}

		sql.append(" ) VALUES (:titulo, :privacidade, :video, :thumbnail, :contacanal, :categoria, CURRENT_TIMESTAMP");

		if (!Util.isBlankOrNull(req.getDescricao())) {
			sql.append(", :descricao");
		}

		if (!Util.isBlankOrNull(req.getTags())) {
			sql.append(", :tags");
		}

		if (!Util.isBlankOrNull(req.getLocale())) {
			sql.append(", :locale");
		}

		sql.append(")");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("titulo", req.getTitulo())
					.setParameter("privacidade", req.getPrivacidade())
					.setParameter("video", req.getIdVideo())
					.setParameter("thumbnail", req.getIdThumbnail())
					.setParameter("contacanal", contaCanal)
					.setParameter("categoria", req.getCategoria());

			if (!Util.isBlankOrNull(req.getDescricao())) {
				query.setParameter("descricao", req.getDescricao());
			}

			if (!Util.isBlankOrNull(req.getTags())) {
				query.setParameter("tags", req.getTags());
			}

			if (!Util.isBlankOrNull(req.getLocale())) {
				query.setParameter("locale", req.getLocale());
			}

			query.executeUpdate();

			msgsErro.add("Publicacao efetuada com sucesso.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}
}
