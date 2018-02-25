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
public class VideoRetorno extends Retorno {
	private static final long serialVersionUID = -948083173591266957L;

	@XmlElementWrapper(name="videos")
	@XmlElement(name="video")
	private List<VideoWs> videos;

	public VideoRetorno() {
		this.temErro = Boolean.FALSE;
		videos = new ArrayList<VideoWs>();
	}

	public List<VideoWs> getVideos() {
		return videos;
	}

	public void setVideos(final List<VideoWs> videos) {
		this.videos = videos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VideoRetorno [temInfo=")
				.append(temInfo)
				.append(", temErro=")
				.append(temErro)
				.append(", msgsErro=")
				.append(msgsErro)
				.append(", videos=")
				.append(videos)
				.append("]");
		return builder.toString();
	}
}
