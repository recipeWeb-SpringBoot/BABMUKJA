package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bab_recipes.Domain.User;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByRecipeId(String recipeId);

    Rating findByUserAndRecipeId(User user, String recipeId);
}
