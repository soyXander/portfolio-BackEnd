package com.argentinaprograma.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xander.-
 */
@Getter @Setter
public class ExperienceDTO {
	private String company;
	private String position;
	private String description;

	public ExperienceDTO() {
	}

	public ExperienceDTO(String company, String position, String description) {
		this.company = company;
		this.position = position;
		this.description = description;
	}
}
