package com.argentinaprograma.backend.dto;

/**
 * @author Xander.-
 */
public class JwtDTO {
	private String token;

	public JwtDTO() {
	}

	public JwtDTO(String accessToken) {
		this.token = accessToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}