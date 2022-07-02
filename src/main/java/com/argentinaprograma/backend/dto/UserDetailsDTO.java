package com.argentinaprograma.backend.dto;

import com.argentinaprograma.backend.model.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Xander.-
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserDetailsDTO {
    private String firstName;
    private String lastname;
    private String location;
    private String title;
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;
    private String facebookId;
    private String instagramId;
    private String githubId;
    private String linkedinId;
    private String twitterId;
}
