package com.example.bab_recipes.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Map;

@Document(collection = "recipes")
public class MongoRecipe {
    @Id
    private String id;

    @TextIndexed
    private String title;

    @TextIndexed
    private String category;

    private Map<String, String> ingredients;

    private Map<String, String> steps;
    @Field("media_url")
    private String mediaUrl;

    @TextIndexed
    private String ingredients_text;

    @LastModifiedDate
    private Date lastModifiedDate;

    // Getters and Setters

    public MongoRecipe(String id, String title, String category, Map<String, String> ingredients, Map<String, String> steps, String mediaUrl, String ingredients_text) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.ingredients = ingredients;
        this.steps = steps;
        this.mediaUrl = mediaUrl;
        this.ingredients_text = ingredients_text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<String, String> getSteps() {
        return steps;
    }

    public void setSteps(Map<String, String> steps) {
        this.steps = steps;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getIngredients_text() {
        return ingredients_text;
    }

    public void setIngredients_text(String ingredients_text) {
        this.ingredients_text = ingredients_text;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "MongoRecipe{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                ", ingredients_text='" + ingredients_text + '\'' +
                '}';
    }
}