package com.example.bab_recipes.DTO;

import com.example.bab_recipes.Domain.MongoRecipe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {

    private String recipeId;
    private String difficulty;
    private MongoRecipe mongoRecipe;

    public RatingDTO(){}

    public RatingDTO(String recipeId, String difficulty, MongoRecipe mongoRecipe) {
        this.recipeId = recipeId;
        this.difficulty = difficulty;
        this.mongoRecipe = mongoRecipe;
    }
}
