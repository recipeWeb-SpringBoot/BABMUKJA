package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.MongoRecipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface mongoRepository extends MongoRepository<MongoRecipe, String> {
    @Query("{ $text: { $search: ?0 } }")
    List<MongoRecipe> findByIngredientsText(String searchText);

}
