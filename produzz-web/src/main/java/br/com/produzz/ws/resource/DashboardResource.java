package br.com.produzz.ws.resource;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.service.ContaService;
import br.com.produzz.service.DashboardService;

@Path("/dashboard")
@Produces({ MediaType.APPLICATION_JSON })
public class DashboardResource extends GenericResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardResource.class);

	@EJB
	private DashboardService service;

	@EJB
	private ContaService contaService;

	@GET
	@Path("plano/{conta}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPlano(@PathParam("conta") Long conta) {
		LOGGER.info("getPlano(" + conta + ")");
        Response retorno = null;

		try {
			retorno = build(Response.Status.OK, service.getPlano(conta));

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}
        
        return retorno;
	}

	@GET
	@Path("contas/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getContas(@PathParam("id") Long idUsuario) {
		LOGGER.info("getContas(" + idUsuario + ")");
        Response retorno = null;

		try {
			retorno = build(Response.Status.OK, service.getContas(idUsuario));

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}

        return retorno;
	}
}
