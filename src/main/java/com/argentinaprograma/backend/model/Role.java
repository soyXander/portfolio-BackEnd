package com.argentinaprograma.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Xander.-
 */
@Entity
@Table(name = "roles")
@Getter @Setter
public class Role {
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role() {
	}

	public Role(ERole name) {
		this.name = name;
	}
}
