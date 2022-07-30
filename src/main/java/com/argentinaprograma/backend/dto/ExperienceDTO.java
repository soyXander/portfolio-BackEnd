package com.argentinaprograma.backend.dto;

import com.argentinaprograma.backend.model.Image;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Xander.-
 */
@Getter @Setter
public class ExperienceDTO {
	private String company;
	private String position;
	private String description;
	private String startDate;
	private String endDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public ExperienceDTO() {
	}

	public ExperienceDTO(String company, String position, String description, String startDate, String endDate , Image image) {
		this.company = company;
		this.position = position;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.image = image;
	}
}
