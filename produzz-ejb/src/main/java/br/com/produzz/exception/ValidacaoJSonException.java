package br.com.produzz.exception;

public class ValidacaoJSonException extends Exception {
	private static final long serialVersionUID = 1801382931749374488L;

	public ValidacaoJSonException() {
		super();
	}

	public ValidacaoJSonException(final String msg) {
		super(msg);
	}

	public ValidacaoJSonException(final Throwable erro) {
		super(erro);
	}

	public ValidacaoJSonException(final String msg, final Throwable erro) {
		super(msg, erro);
	}
}
