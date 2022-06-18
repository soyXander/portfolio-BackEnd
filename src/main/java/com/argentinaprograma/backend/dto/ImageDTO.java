package com.argentinaprograma.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xander.-
 */
@Getter @Setter
public class ImageDTO {
    private Long id;
    private String name;
    private String type;
    private byte[] image;

    public ImageDTO() {
    }

    public ImageDTO(Long id, String name, String type, byte[] image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.image = image;
    }
}
