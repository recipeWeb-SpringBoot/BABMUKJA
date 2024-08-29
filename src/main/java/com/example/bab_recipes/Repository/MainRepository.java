package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.MongoRecipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainRepository extends MongoRepository<MongoRecipe, String> {

    @Query("{ $text: { $search: ?0 } }")
    List<MongoRecipe> searchByTitleOrIngredients(String searchText);
}