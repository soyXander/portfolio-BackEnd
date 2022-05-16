package com.argentinaprograma.backend.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * @author Xander.-
 */
@Getter @Setter
public class ExperienceDTO {
	private String companyName;
	private String position;
	private String description;

	public ExperienceDTO() {
	}

	public ExperienceDTO(String companyName, String position, String description) {
		this.companyName = companyName;
		this.position = position;
		this.description = description;
	}
}
