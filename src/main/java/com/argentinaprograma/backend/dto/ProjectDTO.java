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
	private String technology;
	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public ProjectDTO() {
	}

	public ProjectDTO(String project, String technology, String description, Image image) {
		this.project = project;
		this.technology = technology;
		this.description = description;
		this.image = image;
	}
}
