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
public class ThumbnailRetorno extends Retorno {
	private static final long serialVersionUID = -3413410323537366091L;

	@XmlElementWrapper(name="thumbnails")
	@XmlElement(name="thumbnail")
	private List<ThumbnailWs> thumbnails;

	public ThumbnailRetorno() {
		this.temErro = Boolean.FALSE;
		thumbnails = new ArrayList<ThumbnailWs>();
	}

	public List<ThumbnailWs> getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(final List<ThumbnailWs> thumbnails) {
		this.thumbnails = thumbnails;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ThumbnailRetorno [temInfo=")
				.append(temInfo)
				.append(", temErro=")
				.append(temErro)
				.append(", msgsErro=")
				.append(msgsErro)
				.append(", thumbnails=")
				.append(thumbnails)
				.append("]");
		return builder.toString();
	}
}
