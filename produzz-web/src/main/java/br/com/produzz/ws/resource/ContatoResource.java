package br.com.produzz.ws.resource;

import java.text.MessageFormat;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.entity.Notificacao;
import br.com.produzz.exception.ProduzzException;
import br.com.produzz.requisicao.UsuarioRequisicao;
import br.com.produzz.retorno.UsuarioRetorno;
import br.com.produzz.service.ContatoService;
import br.com.produzz.util.Constantes;
import br.com.produzz.util.Util;

@Path("contatos")
public class ContatoResource extends GenericResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContatoResource.class);

	@EJB
	private ContatoService service;

	@GET @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public UsuarioRetorno findById(@PathParam("id") Long id) throws ProduzzException {
		LOGGER.info("findById(" + id + ")");
		return service.findById(id);
	}

	@GET @Path("{email}/{senha}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response login(@PathParam("email") String email, @PathParam("senha") String senha) throws ProduzzException {
		LOGGER.info("login(" + email + ", " + senha + ")");
        Response retorno = null;

        try {
			retorno = build(Response.Status.OK, service.findByLoginSenha(email, senha));

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}
        
        return retorno;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response register(final UsuarioRequisicao req) throws ProduzzException {
		LOGGER.info("register(" + req + ")");
		Response retorno = null;

		try {
			retorno = build(Response.Status.CREATED, service.incluir(req));

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}
        
        return retorno;
	}	

	@GET @Path("/forgot/{email}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response forgot(@PathParam("email") String email) throws ProduzzException {
		LOGGER.info("forgot(" + email + ")");
		Response retorno = null;

		try {
			UsuarioRetorno usuario = service.findByLogin(email);

			if (!Util.isNull(usuario.getUsuarios()) && usuario.getUsuarios().size() > 0) {
				String senha = Util.GenerateRandomString(Constantes.OITO, 
						  Constantes.DOZE,
						  Constantes.SEIS,
						  Constantes.UM,
						  Constantes.DOIS,
						  Constantes.UM);

				service.alterarSenha(usuario.getUsuarios().get(0).getId(), null, Util.sha1(senha));

				monitor.put(new Notificacao(email, Constantes.FORGOT_SUBJECT,
						MessageFormat.format(
						Constantes.FORGOT_CONTENT,
						usuario.getUsuarios().get(0).getNome() +
								(Util.isNull(usuario.getUsuarios().get(0).getSobrenome()) ? "" : " " +
										usuario.getUsuarios().get(0).getSobrenome()),
						senha)));

				usuario.getMsgsErro().set(0, Constantes.FORGOT_OK);
			}

			retorno = build(Response.Status.OK, usuario);

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}

        return retorno;
	}

	@PUT @Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response update(@PathParam("id") Long id, UsuarioRequisicao req) throws ProduzzException {
		LOGGER.info("update(" + id + ", " + req + ")");
		Response retorno = null;

		try {
			retorno = build(Response.Status.ACCEPTED, service.alterar(id, req));

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}
        
        return retorno;
	}

	@DELETE @Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("id") Long id) throws ProduzzException {
		LOGGER.info("delete(" + id + ")");
		Response retorno = null;

		try {
			retorno = build(Response.Status.ACCEPTED, service.excluir(id));

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}
        
        return retorno;
	}
}
