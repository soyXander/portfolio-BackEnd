package com.argentinaprograma.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Xander.-
 */
@Entity
@Getter @Setter
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    public Banner() {
    }

    public Banner(Image image) {
        this.image = image;
    }
}
