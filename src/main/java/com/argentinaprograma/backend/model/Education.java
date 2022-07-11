package com.argentinaprograma.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public Education() {
	}

	public Education(String institute, String certification, String description, Image image) {
		this.institute = institute;
		this.certification = certification;
		this.description = description;
		this.image = image;
	}
}
