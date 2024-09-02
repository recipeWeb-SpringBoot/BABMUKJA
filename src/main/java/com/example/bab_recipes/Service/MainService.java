package com.example.bab_recipes.Service;

import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Repository.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    @Autowired
    private MainRepository mainRepository;

    public List<MongoRecipe> searchRecipes(String searchText) {
        return mainRepository.searchByTitleOrIngredients(searchText);
    }
}