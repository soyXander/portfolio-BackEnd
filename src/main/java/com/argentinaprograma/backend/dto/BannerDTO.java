package com.argentinaprograma.backend.dto;

import com.argentinaprograma.backend.model.Image;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Xander.-
 */
@Getter @Setter
public class BannerDTO {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;
}
