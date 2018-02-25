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
public class CategoriaRetorno extends Retorno {
	private static final long serialVersionUID = 8296556013316751843L;

	@XmlElementWrapper(name="categorias")
	@XmlElement(name="categoria")
	private List<CategoriaWs> categorias;

	public CategoriaRetorno() {
		categorias = new ArrayList<CategoriaWs>();
	}

	public List<CategoriaWs> getCategorias() {
		return categorias;
	}

	public void setCategorias(final List<CategoriaWs> categorias) {
		this.categorias = categorias;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategoriaRetorno [categorias=")
				.append(categorias)
				.append("]");
		return builder.toString();
	}
}
