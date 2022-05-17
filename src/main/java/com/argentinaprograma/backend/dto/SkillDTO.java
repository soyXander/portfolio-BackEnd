package com.argentinaprograma.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xander.-
 */
@Getter @Setter
public class SkillDTO {
	private String skill;
	private int percentage;

	public SkillDTO() {
	}

	public SkillDTO(String skill, int percentage) {
		this.skill = skill;
		this.percentage = percentage;
	}
}
