package com.argentinaprograma.backend.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author Xander.-
 */
@Entity
@NoArgsConstructor
@Getter @Setter
public class UserDetails {
    /*@JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    private User user;*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
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

    public UserDetails(String firstName, String lastName, String location, String title, String description, Image image, String facebookId, String instagramId, String githubId, String linkedinId, String twitterId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.title = title;
        this.description = description;
        this.image = image;
        this.facebookId = facebookId;
        this.instagramId = instagramId;
        this.githubId = githubId;
        this.linkedinId = linkedinId;
        this.twitterId = twitterId;
    }
}
