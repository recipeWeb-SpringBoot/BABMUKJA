package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.MongoRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<MongoRecipe, String> {
    List<MongoRecipe> findByCategory(String category);

    @Query("{ 'category': ?0 }")
    Page<MongoRecipe> findByCategory(String category, Pageable pageable);
}