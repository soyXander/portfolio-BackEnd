package com.argentinaprograma.backend.model;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;

import javax.persistence.*;

/**
 * @author Xander.-
 */
@Entity
@Setter @Getter
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String project;
	private String technology;
	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public Project() {
	}

	public Project(String project, String technology, String description, Image image) {
		this.project = project;
		this.technology = technology;
		this.description = description;
		this.image = image;
	}
}
