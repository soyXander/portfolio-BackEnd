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
	private String degree;
	private String description;
	private String startDate;
	private String endDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public Education() {
	}

	public Education(String institute, String degree, String description, String startDate, String endDate, Image image) {
		this.institute = institute;
		this.degree = degree;
		this.description = description;
		this.image = image;
	}
}
