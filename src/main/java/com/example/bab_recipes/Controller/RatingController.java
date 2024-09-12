package com.example.bab_recipes.Controller;

import com.example.bab_recipes.DTO.RatingDTO;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Domain.Rating;
import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Service.RatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/addDifficulty")
    public ResponseEntity<Map<String, Object>> addDifficulty(@RequestBody Map<String, String> data, HttpSession session) {
        int difficulty = Integer.parseInt(data.get("difficulty"));
        MongoRecipe recipe = (MongoRecipe) session.getAttribute("recipe");
        User user = (User) session.getAttribute("user");

        // Rating 객체 생성 및 저장
        Rating rating = new Rating(user, recipe.getId(), difficulty);
        ratingService.addOrUpdateRating(rating);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getDifficulty")
    public ResponseEntity<Map<String, Double>> getDifficulty(@RequestParam("recipeId") String recipeId) {
        Map<String, Double> ratings = ratingService.getRatings(recipeId);
        return ResponseEntity.ok(ratings);
    }
}

