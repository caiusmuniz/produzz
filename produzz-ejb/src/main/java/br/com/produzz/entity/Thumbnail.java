package br.com.produzz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Thumbnail implements Serializable {
	private static final long serialVersionUID = -602487552623168561L;

	@Id
	@Column(name = "NR_PDZ020")
	private Long id;

	@Column(name = "IM_THUMBNAIL")
	private byte[] file;

    public Thumbnail() { }

    public Thumbnail(final byte[] file) {
        this.file = file;
    }

    public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
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
		return builder.append("Thumbnail [id=")
				.append(id)
				.append(", file=")
				.append(file)
				.append("]")
				.toString();
	}
}
