package com.argentinaprograma.backend.model;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	public Project() {
	}

	public Project(String project, String technology, String description) {
		this.project = project;
		this.technology = technology;
		this.description = description;
	}
}
