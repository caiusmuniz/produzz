package br.com.produzz.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.entity.Conta;
import br.com.produzz.entity.Usuario;
import br.com.produzz.enumeration.EAtivo;
import br.com.produzz.enumeration.ESistemaAutenticador;
import br.com.produzz.enumeration.ETipoUsuario;
import br.com.produzz.exception.ProduzzException;
import br.com.produzz.requisicao.UsuarioRequisicao;
import br.com.produzz.retorno.ContaWs;
import br.com.produzz.retorno.Retorno;
import br.com.produzz.retorno.UsuarioRetorno;
import br.com.produzz.retorno.UsuarioWs;
import br.com.produzz.util.Constantes;
import br.com.produzz.util.GenericService;
import br.com.produzz.util.Util;

@Stateless
@LocalBean
@Named
public class ContatoService extends GenericService implements Serializable {
	private static final long serialVersionUID = 5105918512957032398L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ContatoService.class);

	@PersistenceContext
	private EntityManager em;

	@EJB
	AutenticacaoService autenticacaoService;

	@EJB
	ContaService contaService;

	private static final String NENHUM_CONTATO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS = "E-mail e/ou senha de usuário incorretos.";
	private static final String NENHUMA_CONTA_FOI_LOCALIZADA_PARA_O_USUARIO_INFORMADOS = "Nenhuma conta foi localizada para o usuário informado.";

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioRetorno findByLoginSenha(final String login, final String senha) throws ProduzzException {
		LOGGER.info("findByLoginSenha(" + login + ", " + senha + ")");
		UsuarioRetorno retorno = new UsuarioRetorno();
		UsuarioWs usuario = new UsuarioWs();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT U.NR_PDZ001, U.DE_NOME, U.DE_SOBRENOME, U.ED_ELETRONICO, U.DE_SENHA, U.IC_ATIVO, U.FK_TIPO_USUARIO")
				.append(" FROM PDZTB001_USUARIO U")
				.append(" WHERE U.ED_ELETRONICO = :email")
				.append(" AND (U.DE_SENHA = :senha")
				.append(" OR U.DE_SENHA_TMP = :senhaTmp)")
				.append(" AND U.IC_ATIVO = :status");

		try {
			Query query = em.createNativeQuery(sql.toString(), Usuario.class);

			Usuario entity = (Usuario) query
					.setParameter("email", login)
					.setParameter("senha", senha)
					.setParameter("senhaTmp", senha)
					.setParameter("status", EAtivo.ATIVO.getCodigo())
					.getSingleResult();

			usuario.setId(entity.getId());
			usuario.setNome(entity.getNome());
			usuario.setSobrenome(entity.getSobrenome());
			usuario.setLogin(entity.getEmail());
			usuario.setIndicador(entity.getStatus());

		} catch (final NoResultException e) {
			LOGGER.error(NENHUM_CONTATO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS, e.getMessage());
			msgsErro.add(NENHUM_CONTATO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS);
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
			return retorno;
		}

		try {
			autenticacaoService.incluir(usuario.getId(), ESistemaAutenticador.EMAIL.getCodigo(), null, null);

			List<Conta> contas = contaService.findByUsuario(usuario.getId(), EAtivo.ATIVO.getCodigo());

			if (Util.isNull(contas) || contas.isEmpty() || contas.size() <= 0) {
				LOGGER.error(NENHUMA_CONTA_FOI_LOCALIZADA_PARA_O_USUARIO_INFORMADOS);
				msgsErro.add(NENHUMA_CONTA_FOI_LOCALIZADA_PARA_O_USUARIO_INFORMADOS);
				retorno.setTemErro(Boolean.TRUE);
				retorno.setMsgsErro(msgsErro);
				return retorno;

			} else {
				usuario.getContas().add(new ContaWs(contas.get(0)));
			}

			retorno.getUsuarios().add(usuario);

			msgsErro.add(Constantes.OPERACAO_OK);
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public UsuarioRetorno findById(final Long id) throws ProduzzException {
		LOGGER.info("findById(" + id + ")");
		UsuarioRetorno retorno = new UsuarioRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT NR_PDZ001, DE_NOME, DE_SOBRENOME, ED_ELETRONICO, DE_SENHA, IC_ATIVO, FK_TIPO_USUARIO")
				.append(" FROM PDZTB001_USUARIO U")
				.append(" WHERE NR_PDZ001 = :id");

		try {
			Query query = em.createNativeQuery(sql.toString(), Usuario.class);

			Usuario entity = (Usuario) query
					.setParameter("id", id)
					.getSingleResult();

			retorno.getUsuarios().add(new UsuarioWs(entity));

			msgsErro.add(Constantes.OPERACAO_OK);
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final NoResultException e) {
			LOGGER.error(NENHUM_CONTATO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS, e.getMessage());
			msgsErro.add(NENHUM_CONTATO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS);
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
			return retorno;

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public UsuarioRetorno findByLogin(final String email) throws Exception {
		LOGGER.info("findByLogin(" + email + ")");
		UsuarioRetorno retorno = new UsuarioRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT U.NR_PDZ001, U.DE_NOME, U.DE_SOBRENOME, U.ED_ELETRONICO, U.DE_SENHA, U.IC_ATIVO, U.FK_TIPO_USUARIO")
				.append(" FROM PDZTB001_USUARIO U")
				.append(" WHERE U.ED_ELETRONICO = :email");

		try {
			Query query = em.createNativeQuery(sql.toString(), Usuario.class);

			Usuario entity = (Usuario) query
					.setParameter("email", email)
					.getSingleResult();

			UsuarioWs usuario = new UsuarioWs(entity);

			List<Conta> contas = contaService.findByUsuario(entity.getId(), EAtivo.ATIVO.getCodigo());

			if (!Util.isNull(contas) && !contas.isEmpty() && contas.size() > 0) {
				usuario.getContas().add(new ContaWs(contas.get(0)));
			}

			retorno.getUsuarios().add(usuario);

			msgsErro.add(Constantes.OPERACAO_OK);
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final NoResultException e) {
			LOGGER.error(NENHUM_CONTATO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS, e.getMessage());
			msgsErro.add(NENHUM_CONTATO_FOI_LOCALIZADO_PARA_OS_PARAMETROS_INFORMADOS);
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public UsuarioRetorno incluir(final UsuarioRequisicao req) throws ProduzzException {
		LOGGER.info("incluir(" + req + ")");
		UsuarioRetorno retorno = new UsuarioRetorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("INSERT INTO PDZTB001_USUARIO")
				.append(" (DE_NOME, DE_SOBRENOME, ED_ELETRONICO, DE_SENHA, IC_ATIVO, FK_TIPO_USUARIO, TS_CRIACAO)")
				.append(" VALUES (:nome, :sobrenome, :email, :senha, :status, :tpUsuario, CURRENT_TIMESTAMP)");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("nome", req.getNome())
					.setParameter("sobrenome", req.getSobrenome())
					.setParameter("email", req.getLogin())
					.setParameter("senha", req.getSenha())
					.setParameter("status", EAtivo.ATIVO.getCodigo())
					.setParameter("tpUsuario", ETipoUsuario.USUARIO.getCodigo());

			query.executeUpdate();

			retorno = this.findByLogin(req.getLogin());

			contaService.incluir(retorno.getUsuarios().get(0).getId());

			Long idConta = contaService.findByDescricao(String.valueOf(retorno.getUsuarios().get(0).getId()));
			retorno.getUsuarios().get(0).getContas().add(new ContaWs(idConta));

			contaService.vincularUsuario(idConta, retorno.getUsuarios().get(0).getId());

			msgsErro.add("Usuario cadastrado com sucesso.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final javax.persistence.PersistenceException e) {
			msgsErro.add("Usuario ja cadastrado.");
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public Retorno alterar(final Long id, final UsuarioRequisicao req) throws ProduzzException {
		LOGGER.info("alterar(" + id + ", " + req + ")");
		Retorno retorno = new Retorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("UPDATE PDZTB001_USUARIO")
				.append(" SET DE_NOME = :nome, DE_SOBRENOME = :sobrenome, ED_ELETRONICO = :email, DE_SENHA = :senha, TS_ATUALIZACAO = CURRENT_TIMESTAMP")
				.append(" WHERE NR_PDZ001 = :id");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("nome", req.getNome())
					.setParameter("sobrenome", req.getSobrenome())
					.setParameter("email", req.getLogin())
					.setParameter("senha", req.getSenha())
					.setParameter("id", id);

			query.executeUpdate();

			msgsErro.add("Usuario atualizado com sucesso.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}

	public void alterarSenha(final Long id, final String senha, final String senhaTmp) throws ProduzzException {
		LOGGER.info("alterarSenha(" + id + ", " + senha + ", " + senhaTmp + ")");
		StringBuilder sql = new StringBuilder("");
		sql.append("UPDATE PDZTB001_USUARIO")
				.append(" SET DE_SENHA_TMP = :senhaTmp, TS_ATUALIZACAO = CURRENT_TIMESTAMP");

		if (!Util.isBlankOrNull(senha)) {
			sql.append(", DE_SENHA = :senha");
		}

		sql.append(" WHERE NR_PDZ001 = :id");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("senhaTmp", senhaTmp)
					.setParameter("id", id);

			if (!Util.isBlankOrNull(senha)) {
				query.setParameter("senha", senha);
			}

			query.executeUpdate();

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}
	}

	public Retorno excluir(final Long id) throws ProduzzException {
		LOGGER.info("excluir(" + id + ")");
		Retorno retorno = new Retorno();
		List<String> msgsErro = new ArrayList<String>();

		StringBuilder sql = new StringBuilder("");
		sql.append("UPDATE PDZTB001_USUARIO")
				.append(" SET IC_ATIVO = :status, TS_ATUALIZACAO = CURRENT_TIMESTAMP")
				.append(" WHERE NR_PDZ001 = :id");

		try {
			Query query = em.createNativeQuery(sql.toString());

			query.setParameter("status", EAtivo.INATIVO.getCodigo())
					.setParameter("id", id);

			query.executeUpdate();

			msgsErro.add("Usuario excluido com sucesso.");
			retorno.setMsgsErro(msgsErro);
			retorno.setTemInfo(Boolean.TRUE);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			throw new ProduzzException(e);
		}

		return retorno;
	}
}
