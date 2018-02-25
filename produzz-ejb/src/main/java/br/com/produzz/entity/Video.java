package br.com.produzz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Video implements Serializable {
	private static final long serialVersionUID = -7734061382784101270L;

	@Id
	@Column(name = "NR_PDZ019")
	private Long id;

	@Column(name = "NM_VIDEO")
	private String filename;

	@Column(name = "IM_VIDEO")
	private byte[] file;

    public Video() { }

    public Video(final String filename, final byte[] file) {
        this.file = file;
        this.filename = filename;
    }

    public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getFilename() {
        return filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(final byte[] file) {
        this.file = file;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("Video [id=")
				.append(id)
				.append(", filename=")
				.append(filename)
				.append(", file=")
				.append(file)
				.append("]")
				.toString();
	}
}
