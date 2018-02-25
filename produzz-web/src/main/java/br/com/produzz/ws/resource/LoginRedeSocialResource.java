package br.com.produzz.ws.resource;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.enumeration.ECanal;
import br.com.produzz.enumeration.ESistemaAutenticador;
import br.com.produzz.exception.ProduzzException;
import br.com.produzz.requisicao.RedeSocialRequisicao;
import br.com.produzz.requisicao.UsuarioRequisicao;
import br.com.produzz.retorno.Retorno;
import br.com.produzz.retorno.UsuarioRetorno;
import br.com.produzz.service.AutenticacaoService;
import br.com.produzz.service.ContaCanalService;
import br.com.produzz.service.ContatoService;
import br.com.produzz.util.Constantes;
import br.com.produzz.util.Util;

@Path("plataforma")
public class LoginRedeSocialResource extends GenericResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginRedeSocialResource.class);

	@EJB
	private ContatoService service;

	@EJB
	private ContaCanalService contaCanalService;

	@EJB
	private AutenticacaoService autenticacaoService;

	@GET
	@Path("{conta}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getContaCanal(@PathParam("conta") Long conta) {
		LOGGER.info("getContaCanal(" + conta + ")");
        Response retorno = null;

		try {
			retorno = build(Response.Status.OK, contaCanalService.findByConta(conta));

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}

        return retorno;
	}

	@POST @Path("login/facebook")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginFacebook(RedeSocialRequisicao req) throws ProduzzException {
		LOGGER.info("loginFacebook(" + req + ")");
		return this.login(req, ESistemaAutenticador.FACEBOOK, ECanal.FACEBOOK);
	}

	@POST @Path("login/google")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginGoogle(RedeSocialRequisicao req) throws ProduzzException {
		LOGGER.info("loginGoogle(" + req + ")");
		return this.login(req, ESistemaAutenticador.GOOGLE, ECanal.GOOGLE);
	}

	private Response login(RedeSocialRequisicao req, final ESistemaAutenticador sistema, final ECanal canal) {
        Response retorno = null;

        try {
        		UsuarioRetorno ret = service.findByLogin(req.getEmail());

        		if (ret.isTemErro()) {
        			UsuarioRequisicao usuario = new UsuarioRequisicao();
        			usuario.setLogin(req.getEmail());
        			usuario.setNome(Util.isBlankOrNull(req.getNome()) ? req.getEmail() : req.getNome());
        			usuario.setSobrenome(req.getSobrenome());

        			String senha = Util.GenerateRandomString(Constantes.OITO, 
      					  Constantes.DOZE,
      					  Constantes.SEIS,
      					  Constantes.UM,
      					  Constantes.DOIS,
      					  Constantes.UM);

        			usuario.setSenha(Util.sha1(senha));

        			LOGGER.debug("Chamada a incluir usuario");
        			ret = service.incluir(usuario);

        			if (ECanal.FACEBOOK.getCodigo() == canal.getCodigo()) {
        				LOGGER.debug("Chamada a incluir conta canal");
        				contaCanalService.incluir(ret.getUsuarios().get(0).getContas().get(0).getId(), canal.getCodigo(), req);
        			}
        		}

    			LOGGER.debug("Chamada a incluir autenticacao");
        		autenticacaoService.incluir(ret.getUsuarios().get(0).getId(), sistema.getCodigo(), req.getIdToken(), req.getTokenAcesso());

        		retorno = build(Response.Status.OK, ret);

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}

        return retorno;
	}

	@POST @Path("conectar/facebook")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response conectarFacebook(RedeSocialRequisicao req) throws ProduzzException {
		LOGGER.info("conectarFacebook(" + req + ")");
		return this.conectar(req, ECanal.FACEBOOK);
	}

	@POST @Path("conectar/google")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response conectarGoogle(RedeSocialRequisicao req) throws ProduzzException {
		LOGGER.info("conectarGoogle(" + req + ")");
		return this.conectar(req, ECanal.GOOGLE);
	}

	private Response conectar(RedeSocialRequisicao req, final ECanal canal) {
        Response retorno = null;

        try {
        		contaCanalService.incluir(req.getIdConta(), canal.getCodigo(), req);

        		retorno = build(Response.Status.OK, new Retorno());

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}

        return retorno;
	}

	@GET @Path("desconectar/facebook/{conta}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response desconectarFacebook(@PathParam("conta") Long conta) throws ProduzzException {
		LOGGER.info("desconectarFacebook(" + conta + ")");
		return this.desconectar(conta, ECanal.FACEBOOK);
	}

	@GET @Path("desconectar/google/{conta}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response desconectarGoogle(@PathParam("conta") Long conta) throws ProduzzException {
		LOGGER.info("desconectarGoogle(" + conta + ")");
		return this.desconectar(conta, ECanal.GOOGLE);
	}

	private Response desconectar(final Long conta, final ECanal canal) {
        Response retorno = null;

        try {
        		contaCanalService.excluir(conta, canal.getCodigo());

        		retorno = build(Response.Status.OK, new Retorno());

		} catch (final Exception e) {
			LOGGER.error("Exception: ", e);
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}

        return retorno;
	}
}
