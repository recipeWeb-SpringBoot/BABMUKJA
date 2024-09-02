package com.example.bab_recipes.Domain;

import jakarta.persistence.*;
import lombok.Setter;

@Table(name = "Rating")
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Setter
    @Column(nullable = false)
    private Double rating;

    public Long getRatingId() {
        return ratingId;
    }


    public User getUser() {
        return user;
    }

    public Double getRating() {
        return rating;
    }


}
