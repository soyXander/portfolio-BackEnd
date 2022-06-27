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
public class ExperienceDTO {
	private String company;
	private String position;
	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public ExperienceDTO() {
	}

	public ExperienceDTO(String company, String position, String description, Image image) {
		this.company = company;
		this.position = position;
		this.description = description;
		this.image = image;
	}
}
