package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
