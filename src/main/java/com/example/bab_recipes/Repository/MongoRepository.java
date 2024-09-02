package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.MongoRecipe;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<MongoRecipe, String> {
    @Query("{ $text: { $search: ?0 } }")
    List<MongoRecipe> findByIngredientsText(String searchText);

}
