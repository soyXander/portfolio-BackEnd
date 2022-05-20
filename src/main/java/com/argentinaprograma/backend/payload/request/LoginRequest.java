package com.argentinaprograma.backend.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Xander.-
 */
@Getter @Setter
public class LoginRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
