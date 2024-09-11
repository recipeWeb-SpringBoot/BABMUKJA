package com.example.bab_recipes.DTO;

import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {

    private User user;
    private String difficulty;
    private String RecipeId;

    public RatingDTO(){}

    public RatingDTO(User user, String difficulty, String RecipeId) {
        this.user = user;
        this.difficulty = difficulty;
        this.RecipeId = RecipeId;
    }
}
