package br.com.produzz.ws.resource;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.service.ContaCanalService;

@Path("/categoria")
@Produces({ MediaType.APPLICATION_JSON })
public class CategoriaResource extends GenericResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaResource.class);

	@EJB
	private ContaCanalService service;

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCategoria() {
		LOGGER.info("getCategoria()");
        Response retorno = null;

		try {
			retorno = build(Response.Status.OK, service.getCategoria());

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}
        
        return retorno;
	}
}
