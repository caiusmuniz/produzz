package br.com.produzz.retorno;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ContaUsuarioRetorno extends Retorno {
	private static final long serialVersionUID = 1208840414648388267L;

	private Long idConta;

	public ContaUsuarioRetorno() { }

	public ContaUsuarioRetorno(final Long idConta) {
		this.idConta = idConta;
	}

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(final Long idConta) {
		this.idConta = idConta;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContaUsuarioRetorno [idConta=")
				.append(idConta)
				.append("]");
		return builder.toString();
	}
}
