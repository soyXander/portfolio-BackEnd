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
public class EducationDTO {
	private String institute;
	private String certification;
	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public EducationDTO() {
	}

	public EducationDTO(String institute, String certification, String description, Image image) {
		this.institute = institute;
		this.certification = certification;
		this.description = description;
		this.image = image;
	}
}
