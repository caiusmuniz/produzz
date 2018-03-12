package br.com.produzz.ws.resource;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;

import br.com.produzz.entity.Video;
import br.com.produzz.enumeration.ECanal;
import br.com.produzz.exception.GeneralException;
import br.com.produzz.exception.ProduzzException;
import br.com.produzz.requisicao.PublicacaoRequisicao;
import br.com.produzz.retorno.Retorno;
import br.com.produzz.retorno.ThumbnailRetorno;
import br.com.produzz.retorno.VideoRetorno;
import br.com.produzz.service.ContaCanalService;
import br.com.produzz.service.VideoService;
import br.com.produzz.util.Util;
import br.com.produzz.youtube.Auth;
import br.com.produzz.youtube.UploadVideo;

@Path("/videos")
public class VideoResource extends GenericResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(VideoResource.class);

	@EJB
	private VideoService service;

	@EJB
	private ContaCanalService contaCanalService;

	public static final String UPLOADED_FILE_PARAMETER_NAME = "file";

	@POST
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/upload/{conta}")
    public Response upload(@PathParam("conta") Long conta, final MultipartFormDataInput req) {
		LOGGER.debug("upload(" + conta + ", " + req + ")");
		Response retorno = null;
		VideoRetorno video = new VideoRetorno();

		try {
			Map<String, List<InputPart>> uploadForm = req.getFormDataMap();
	        List<InputPart> inputParts = uploadForm.get(UPLOADED_FILE_PARAMETER_NAME);

	        for (InputPart inputPart : inputParts) {
	            MultivaluedMap<String, String> headers = inputPart.getHeaders();

                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                String filename = getFileName(headers);

                video = service.incluir(conta, filename, bytes);
	        }

            retorno = build(Response.Status.OK, video);

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}

        return retorno;
	}

	@POST
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/upload/thumbnail/{conta}/{idVideo}")
    public Response uploadThumbnail(@PathParam("conta") Long conta, @PathParam("idVideo") Long idVideo, final MultipartFormDataInput req) {
		LOGGER.debug("uploadThumbnail(" + conta + ", " + idVideo + ", " + req + ")");
		Response retorno = null;
		ThumbnailRetorno thumbnail = new ThumbnailRetorno();

		try {
			Map<String, List<InputPart>> uploadForm = req.getFormDataMap();
	        List<InputPart> inputParts = uploadForm.get(UPLOADED_FILE_PARAMETER_NAME);

	        for (InputPart inputPart : inputParts) {
	            MultivaluedMap<String, String> headers = inputPart.getHeaders();

                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                String filename = getFileName(headers);

                thumbnail = service.incluirThumbnail(conta, idVideo, filename, bytes);
	        }

            retorno = build(Response.Status.OK, thumbnail);

		} catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}

        return retorno;
	}

	private String getFileName(MultivaluedMap<String, String> headers) {
        String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");

                String finalFileName = sanitizeFilename(name[1]);
                return finalFileName;
            }
        }

        return "unknown";
    }

    private String sanitizeFilename(String s) {
        return s.trim().replaceAll("\"", "");
    }

    @POST @Path("/publish")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
    public Response publicar(final PublicacaoRequisicao req) throws ProduzzException {
    		LOGGER.info("publicar(" + req + ")");
    		Response retorno = null;

    		try {
    			Credential credential = Auth.autorizar("uploadvideo");

    			UploadVideo.upload(credential);

    			retorno = build(Response.Status.OK,
    					service.publicar(req,
    							contaCanalService.findByContaCanal(req.getId(), ECanal.GOOGLE.getCodigo())));

    		} catch (final Exception e) {
    			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
    		}
            
    		return retorno;
    }
    
    @GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/download/{filename}")
    public Response download(@PathParam("filename") String filename) {
		LOGGER.debug("download(" + filename + ")");

	    Response retorno = null;

        try {
        	//recuperar o video por nome
        	Video video = new Video();

            // No file found based on the supplied filename
            if (Util.isNull(video)) {
            	throw new ProduzzException(GeneralException.RESPONSE_CODE_ERROR_PARSER_FAIL);
            }

            // Generate the http headers with the file properties
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("content-disposition", "attachment; filename=" + video.getFilename());

            // Split the mimeType into primary and sub types
//            String primaryType = video.getMimeType().split("/")[0];
//            String subType = video.getMimeType().split("/")[1];

//            headers.setContentType( new org.springframework.http.MediaType(primaryType, subType) );

            retorno = build(Response.Status.OK, new Retorno());

        } catch (final Exception e) {
			retorno = build(Response.Status.BAD_REQUEST, "Ops!, Aconteceu algo de errado.");
		}

        return retorno;
	}
}
