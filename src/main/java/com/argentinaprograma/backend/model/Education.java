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
public class Education {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String institute;
	private String certification;
	private String description;

	public Education() {
	}

	public Education(String institute, String certification, String description) {
		this.institute = institute;
		this.certification = certification;
		this.description = description;
	}
}
