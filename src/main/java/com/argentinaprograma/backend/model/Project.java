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
	private String creationDate;
	private String description;
	private String link;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public Project() {
	}

	public Project(String project, String creationDate, String description, String link, Image image) {
		this.project = project;
		this.creationDate = creationDate;
		this.description = description;
		this.link = link;
		this.image = image;
	}
}
