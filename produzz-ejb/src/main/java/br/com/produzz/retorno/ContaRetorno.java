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
public class ContaRetorno extends Retorno {
	private static final long serialVersionUID = 4167807985925729711L;

	@XmlElementWrapper(name="contas")
	@XmlElement(name="conta")
	private List<ContaWs> contas;

	public ContaRetorno() {
		contas = new ArrayList<ContaWs>();
	}

	public List<ContaWs> getContas() {
		return contas;
	}

	public void setContas(final List<ContaWs> contas) {
		this.contas = contas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContaRetorno [contas=")
				.append(contas)
				.append("]");
		return builder.toString();
	}
}
