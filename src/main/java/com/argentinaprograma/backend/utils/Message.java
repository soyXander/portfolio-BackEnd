package com.argentinaprograma.backend.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xander.-
 */
@Getter @Setter
public class Message {

	private String message;

	public Message(String message) {
		this.message = message;
	}
}
