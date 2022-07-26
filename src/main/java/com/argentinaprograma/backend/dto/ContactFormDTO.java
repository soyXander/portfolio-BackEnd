package com.argentinaprograma.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xander.-
 */
@Getter @Setter
public class ContactFormDTO {
    private String name;
    private String email;
    private String message;

    public ContactFormDTO() { }

    public ContactFormDTO(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }
}
