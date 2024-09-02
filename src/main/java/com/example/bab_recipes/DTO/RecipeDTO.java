package com.example.bab_recipes.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class RecipeDTO {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private Map<String, String> ingredients;

    @Getter
    @Setter
    private String mediaUrl;

    @Getter
    @Setter
    private boolean isBookmarked;

    public RecipeDTO(String id, String title, Map<String, String> ingredients, String mediaUrl, boolean isBookmarked) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.mediaUrl = mediaUrl;
        this.isBookmarked = isBookmarked;
    }


}
