package com.example.bab_recipes.Service;

import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Repository.mongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodayService {

    @Autowired
    mongoRepository mongoRepository;

    public List<MongoRecipe> searchRecipes(String[] fridgeItems, String[] excludeItems) {
        StringBuilder searchText = new StringBuilder();
        // 포함할 재료 추가
        for (String item : fridgeItems) {
            searchText.append("\"").append(item).append("\" ");
        }

        // 제외할 재료 추가
        for (String item : excludeItems) {
            searchText.append("-\"").append(item).append("\" ");
        }

        return mongoRepository.findByIngredientsText(searchText.toString().trim());
    }

    public List<MongoRecipe> searchOne(String[] fridgeItems) {
        StringBuilder searchText = new StringBuilder();
        for (String item : fridgeItems) {
            searchText.append("\"").append(item).append("\" ");
        }

        return mongoRepository.findByIngredientsText(searchText.toString().trim());
    }

    public Optional<MongoRecipe> detailRecipe(String recipeId) {
        return mongoRepository.findById(recipeId);
    }

}
