package br.com.produzz.retorno;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Retorno implements Serializable {
	private static final long serialVersionUID = 6039191949860267373L;

	@XmlElement
	protected boolean temErro;

	@XmlElement
	protected boolean temInfo;

	@XmlElement
	protected List<String> msgsErro;

	public Retorno() {
		this.temInfo = Boolean.FALSE;
		this.temErro = Boolean.FALSE;
	}

	public boolean isTemErro() {
		return temErro;
	}

	public void setTemErro(final boolean temErro) {
		this.temErro = temErro;
	}

	public boolean isTemInfo() {
		return temInfo;
	}

	public void setTemInfo(final boolean temInfo) {
		this.temInfo = temInfo;
	}

	public List<String> getMsgsErro() {
		return msgsErro;
	}

	public void setMsgsErro(final List<String> msgsErro) {
		this.msgsErro = msgsErro;
	}
}
