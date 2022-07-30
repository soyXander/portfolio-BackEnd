package com.argentinaprograma.backend.dto;

import com.argentinaprograma.backend.model.Image;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Xander.-
 */
@Getter @Setter
public class ProjectDTO {
	private String project;
	private String creationDate;
	private String description;
	private String link;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public ProjectDTO() {
	}

	public ProjectDTO(String project, String creationDate, String description, String link, Image image) {
		this.project = project;
		this.creationDate = creationDate;
		this.description = description;
		this.link = link;
		this.image = image;
	}
}
