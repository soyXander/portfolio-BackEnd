package com.argentinaprograma.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Xander.-
 */
@Entity
@Setter @Getter
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String skill;
	private int percentage;

	public Skill() {
	}

	public Skill(String skill, int percentage) {
		this.skill = skill;
		this.percentage = percentage;
	}
}
