package com.argentinaprograma.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xander.-
 */
@Getter @Setter
public class ProjectDTO {
	private String project;
	private String technology;
	private String description;

	public ProjectDTO() {
	}

	public ProjectDTO(String project, String technology, String description) {
		this.project = project;
		this.technology = technology;
		this.description = description;
	}
}
