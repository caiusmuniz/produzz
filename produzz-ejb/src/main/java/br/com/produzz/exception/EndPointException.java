package br.com.produzz.exception;

import br.com.produzz.exception.GeneralException;

/**.
 * Projeto SISIT
 * EndPointException
 * @author Caius Muniz
 * @version 1.0
 */
public class EndPointException extends GeneralException {
	private static final long serialVersionUID = 981246072876995538L;

	public static final GeneralException RESPONSE_CODE_ERROR_GENERAL = new GeneralException(600, "Servidor momentaneamente inoperante");
	public static final GeneralException RESPONSE_CODE_ERROR_DATA_NOT_FOUND = new GeneralException(601, "Falha ao processar comando. Dados nao recebidos");
	public static final GeneralException RESPONSE_CODE_ERROR_COMMAND_NOT_DEFINED = new GeneralException(602, "Falha ao processar comando. Comando nao reconhecido: %s");
	public static final GeneralException RESPONSE_CODE_ERROR_INVALID_TOKEN = new GeneralException(603, "Usuario nao identificado nesta sessao.");
	public static final GeneralException RESPONSE_CODE_ERROR_PARAMETER_NOT_FOUND = new GeneralException(604, "Parametro nao recebido: %s");
	public static final GeneralException RESPONSE_CODE_ERROR_INVALID_PARAMETER = new GeneralException(605, "Parametro invalido.");

	public EndPointException(int code, String message) {
		super(code,message);
	}	

	public EndPointException(GeneralException general, String[]args) {
		super(general,args);
	}

	public EndPointException(GeneralException general, String arg) {
		super(general,arg);
	}	

	public EndPointException(GeneralException general) {
		super(general);
	}	
}
