package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PopularRepository extends JpaRepository<Bookmark, Long> {
    @Query("SELECT b.recipeId FROM Bookmark b GROUP BY b.recipeId ORDER BY COUNT(b.recipeId) DESC")
    List<String> findTop5PopularRecipes();
}
