package br.com.produzz.ws.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.produzz.exception.EndPointException;
import br.com.produzz.exception.ValidacaoJSonException;
import br.com.produzz.retorno.Retorno;
import br.com.produzz.util.Util;
import br.com.produzz.worker.Monitor;

/**.
 * Projeto PRODUZZ
 * GenericResource
 * @author Caius Muniz
 * @version 1.0
 */
public class GenericResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericResource.class);

	@Context
	protected HttpHeaders headers;

	@Context
	protected HttpServletRequest request;

	@Resource(lookup = "java:jboss/mail/gmail")
    protected Session mailSession;

	private static ValidatorFactory factory;

	protected static Monitor monitor;

	static {
		factory = Validation.buildDefaultValidatorFactory();

		monitor = new Monitor();

		try {
			monitor.start();

		} catch (final Exception e) { }
	}

	@OPTIONS
	@Path("{path : .*}")
	public Response options() {
		LOGGER.debug("options()");
	    return Response.ok("")
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
	            .header("Access-Control-Max-Age", "1209600")
	            .build();
	}

	protected Response build(Status status, Object object) {
		return Response.status(status)
				.entity(object)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "Origin, X-Request-Width, Content-Type, Accept")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
				.header("Access-Control-Max-Age", "1209600")
				.header("Cache-Control", "no-cache, no-store, must-revalidate")
				.header("Expires", 0)
				.header("Pragma", "no-cache")
				.header("X-Frame-Options", "DENY")
				.header("X-XSS-Protection", "1")
                .build();
	}

	protected void validarJSon(final Object obj) throws ValidacaoJSonException {
		LOGGER.debug("validarJSon(" + obj + ")");
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Object>> lista = validator.validate(obj);

		if (!lista.isEmpty()) {
			for (ConstraintViolation<Object> item : lista) {
				LOGGER.info("Propriedade [" + item.getPropertyPath() + "]: " + item.getMessage());
			}

			throw new ValidacaoJSonException(EndPointException.RESPONSE_CODE_ERROR_INVALID_PARAMETER.getMessage());
		}
	}

	protected Retorno prepararRetorno(final String mensagem) {
		Retorno retorno = new Retorno();
		List<String> msgErro = new ArrayList<String>();

		msgErro.add(mensagem);
		retorno.setTemErro(Boolean.TRUE);
		retorno.setMsgsErro(msgErro);

		return retorno;
	}

	protected void enviarEmail(final String destinatario, final String subject, final String content) {
		LOGGER.debug("enviarEmail()");
		try {
			MimeMessage msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress("servidor.produzz@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(destinatario) });
			msg.setSubject(Util.isNull(subject) ? "JBoss EAP Mail" : subject);
			msg.setSentDate(new Date());
			msg.setContent(Util.isNull(content) ? "Mail sent from JBoss EAP" : content, "text/plain");

			Transport.send(msg);
			LOGGER.debug("E-mail enviado.");

		} catch (final MessagingException e) {
			LOGGER.error("MessagingException: " + e.getMessage());
		}
	}

	protected void enviarEmailSSL(final String destinatario, final String subject, final String content) {
		LOGGER.debug("enviarEmailSSL()");
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(
					props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("servidor.produzz@gmail.com","GmailPrdzz");
						}
					});

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("servidor.produzz@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(destinatario) });
			msg.setSubject(Util.isNull(subject) ? "JBoss EAP Mail" : subject);
			msg.setSentDate(new Date());
			msg.setContent(Util.isNull(content) ? "Mail sent from JBoss EAP" : content, "text/html charset=UTF-8");

			Transport.send(msg);
			LOGGER.debug("E-mail enviado.");

		} catch (final MessagingException e) {
			LOGGER.error("MessagingException: " + e.getMessage());
		}
	}
}
