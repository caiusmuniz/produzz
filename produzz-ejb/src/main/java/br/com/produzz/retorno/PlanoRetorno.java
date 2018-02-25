package br.com.produzz.retorno;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanoRetorno extends Retorno {
	private static final long serialVersionUID = -8886899079197477596L;

	@XmlElementWrapper(name="planos")
	@XmlElement(name="plano")
	private List<PlanoWs> planos;

	public PlanoRetorno() {
		planos = new ArrayList<PlanoWs>();
	}

	public List<PlanoWs> getPlanos() {
		return planos;
	}

	public void setPlanos(final List<PlanoWs> planos) {
		this.planos = planos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlanoRetorno [planos=")
				.append(planos)
				.append("]");
		return builder.toString();
	}
}
