package br.com.produzz.entity;

import java.io.Serializable;

public class Notificacao implements Serializable {
	private static final long serialVersionUID = 5382546488122647416L;

	private String email;
	private String subject;
	private String content;

	public Notificacao(final String email, final String subject, final String content) {
		this.email = email;
		this.subject = subject;
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("Notificacao [email=")
				.append(email)
				.append(", subject=")
				.append(subject)
				.append(", content=")
				.append(content)
				.append("]")
				.toString();
	}

}
