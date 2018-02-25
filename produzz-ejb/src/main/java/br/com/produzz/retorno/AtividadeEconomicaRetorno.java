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
public class AtividadeEconomicaRetorno extends Retorno {
	private static final long serialVersionUID = 4831322564014390960L;

	@XmlElementWrapper(name="AtividadeEconomicaRetorno")
	@XmlElement(name="atividade")
	private List<AtividadeEconomicaWs> atividades;

	public AtividadeEconomicaRetorno() {
		atividades = new ArrayList<AtividadeEconomicaWs>();
	}

	public List<AtividadeEconomicaWs> getAtividades() {
		return atividades;
	}

	public void setAtividades(final List<AtividadeEconomicaWs> atividades) {
		this.atividades = atividades;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtividadeEconomicaRetorno [atividades=")
				.append(atividades)
				.append("]");
		return builder.toString();
	}
}
