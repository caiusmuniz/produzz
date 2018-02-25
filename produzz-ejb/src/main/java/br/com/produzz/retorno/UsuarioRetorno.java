package br.com.produzz.retorno;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class UsuarioRetorno extends Retorno {
	private static final long serialVersionUID = -1461173319414861140L;

	@XmlElementWrapper(name="usuarios")
	@XmlElement(name="usuario")
	private List<UsuarioWs> usuarios;

	public UsuarioRetorno() {
		this.temErro = Boolean.FALSE;
		usuarios = new ArrayList<UsuarioWs>();
	}

	public List<UsuarioWs> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(final List<UsuarioWs> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioRetorno [temInfo=")
				.append(temInfo)
				.append(", temErro=")
				.append(temErro)
				.append(", msgsErro=")
				.append(msgsErro)
				.append(", usuarios=")
				.append(usuarios)
				.append("]");
		return builder.toString();
	}
}
