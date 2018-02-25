package br.com.produzz.exception;

/**.
 * Objetivo: Classe responsavel por encapsular todas as exceptions da aplicacao para posterior tratamento
 * Nome: ProduzzException
 * @author Caius Muniz
 * @since 23/09/2014
 * @version 1.0
 */
public class ProduzzException extends Exception {
	private static final long serialVersionUID = -3433897442707716153L;

	public ProduzzException() {
		super();
	}

	public ProduzzException(final String msg) {
		super(msg);
		
	}

	public ProduzzException(final Throwable erro) {
		super(erro);
		
	}

	public ProduzzException(final String msg, final Throwable erro) {
		super(msg, erro);
		
	}
}
