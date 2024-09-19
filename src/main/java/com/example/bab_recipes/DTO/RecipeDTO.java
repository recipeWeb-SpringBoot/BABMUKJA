package com.example.bab_recipes.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class RecipeDTO {

    private String id;

    private String title;

    private Map<String, String> ingredients;

    private String mediaUrl;

    private boolean isBookmarked;

    public RecipeDTO() {
    }

    public RecipeDTO(String id, String title, Map<String, String> ingredients, String mediaUrl, boolean isBookmarked) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.mediaUrl = mediaUrl;
        this.isBookmarked = isBookmarked;
    }


}
