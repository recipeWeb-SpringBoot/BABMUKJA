package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.MongoRecipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<MongoRecipe, String> {
    List<MongoRecipe> findByCategory(String category);
}