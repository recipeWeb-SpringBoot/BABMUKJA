package com.example.bab_recipes.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Rating")
@Entity
public class Rating {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Getter
    @Setter
    @Column(name = "recipeId", nullable = false)
    private String recipeId;

    @Getter
    @Setter
    @Column(name = "difficulty", nullable = false)
    private int difficulty; // {easy} or {hard}

    public Rating() {
    }

    public Rating(User user, String recipeId, int difficulty) {
        this.user = user;
        this.recipeId = recipeId;
        this.difficulty = difficulty;
    }

    public User getUser() {
        return this.user;
    }
}
