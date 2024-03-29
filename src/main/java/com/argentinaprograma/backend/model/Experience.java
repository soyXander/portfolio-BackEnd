package com.argentinaprograma.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Xander.-
 */
@Entity
@Setter @Getter
public class Experience {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String company;
	private String position;
	private String description;
	private String startDate;
	private String endDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public Experience() {
	}
	public Experience(String company, String position, String description, String startDate, String endDate, Image image) {
		this.company = company;
		this.position = position;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.image = image;
	}
}
