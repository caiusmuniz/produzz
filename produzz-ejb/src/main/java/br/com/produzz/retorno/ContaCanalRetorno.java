package br.com.produzz.retorno;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ContaCanalRetorno extends Retorno {
	private static final long serialVersionUID = -7252893439327568287L;

	private ContaCanalWs facebookUser;
	private ContaCanalWs googleUser;

	public ContaCanalWs getFacebookUser() {
		return facebookUser;
	}

	public void setFacebookUser(final ContaCanalWs facebookUser) {
		this.facebookUser = facebookUser;
	}

	public ContaCanalWs getGoogleUser() {
		return googleUser;
	}

	public void setGoogleUser(final ContaCanalWs googleUser) {
		this.googleUser = googleUser;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContaCanalRetorno [facebookUser=")
				.append(facebookUser)
				.append(", googleUser=")
				.append(googleUser)
				.append("]");
		return builder.toString();
	}
}
