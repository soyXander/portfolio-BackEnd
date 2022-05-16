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
public class Experience {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String companyName;
	private String position;
	private String description;

	public Experience() {
	}
	public Experience(String companyName, String position, String description) {
		this.companyName = companyName;
		this.position = position;
		this.description = description;
	}
}
