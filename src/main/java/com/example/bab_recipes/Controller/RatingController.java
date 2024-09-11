package com.example.bab_recipes.Controller;

import com.example.bab_recipes.DTO.RatingDTO;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Domain.Rating;
import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Service.RatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

        System.out.println("레시피 : " + recipe.getId());
        System.out.println("유저 : " + user.getUserId());
        System.out.println("난이도는 : " + difficulty);
        Rating rating = new Rating(user, recipe.getId(),difficulty);

        Map<String, Object> response = new HashMap<>();
        ratingService.addRating(rating);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

}
