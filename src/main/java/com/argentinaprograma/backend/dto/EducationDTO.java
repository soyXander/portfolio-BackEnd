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
	private String degree;
	private String description;
	private String startDate;
	private String endDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public EducationDTO() {
	}

	public EducationDTO(String institute, String degree, String description, String startDate, String endDate, Image image) {
		this.institute = institute;
		this.degree = degree;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.image = image;
	}
}
