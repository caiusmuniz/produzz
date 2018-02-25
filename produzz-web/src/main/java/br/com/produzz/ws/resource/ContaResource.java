package br.com.produzz.ws.resource;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.exception.ProduzzException;
import br.com.produzz.requisicao.ContaRequisicao;
import br.com.produzz.retorno.AtividadeEconomicaRetorno;
import br.com.produzz.retorno.ContaRetorno;
import br.com.produzz.service.AtividadeEconomicaService;
import br.com.produzz.service.ContaService;

@Path("/contas")
public class ContaResource extends GenericResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContaResource.class);

	@EJB
	ContaService service;

	@EJB
	AtividadeEconomicaService ativEconService;

	@GET @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ContaRetorno findById(@PathParam("id") Long id) throws ProduzzException {
		LOGGER.info("findById(" + id + ")");
		return service.findById(id);
	}

	@GET @Path("/atividades")
	@Produces({ MediaType.APPLICATION_JSON})
	public AtividadeEconomicaRetorno listAllAtividadeEconomica() throws ProduzzException {
		LOGGER.info("listAllAtividadeEconomica()");
		return ativEconService.listAll();
	}
	
	@PUT @Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response update(@PathParam("id") Long id, ContaRequisicao req) throws ProduzzException {
		LOGGER.info("update(" + id + "," + req + ")");
		Response retorno = null;

		try {
			retorno = build(Response.Status.ACCEPTED, service.alterar(req));

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}
        
        return retorno;
	}
}
