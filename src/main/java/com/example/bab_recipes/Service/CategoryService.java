package com.example.bab_recipes.Service;

import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Map<String, List<MongoRecipe>> getAllRecipesGroupedByCategory() {
        Map<String, List<MongoRecipe>> categorizedRecipes = new HashMap<>();

        String[] categories = {"베이킹", "중식", "다이어트", "분식", "퓨전", "일식", "한식", "밑반찬", "채식", "양식"};

        for (String category : categories) {
            List<MongoRecipe> recipes = categoryRepository.findByCategory(category);
            categorizedRecipes.put(category, recipes);
        }

        return categorizedRecipes;
    }
}