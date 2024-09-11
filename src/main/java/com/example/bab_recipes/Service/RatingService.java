package com.example.bab_recipes.Service;

import com.example.bab_recipes.Domain.Rating;
import com.example.bab_recipes.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;


    public void addRating(Rating rating) {
        ratingRepository.save(rating);
    }

    public Map<String, Double> getRatings(String recipeId) {
        List<Rating> ratings = ratingRepository.findByRecipeId(recipeId);

        double count = ratings.size();
        if (count == 0) {
            Map<String, Double> difficulty = new HashMap<>();
            difficulty.put("easy", 0.0);
            difficulty.put("hard", 0.0);
            difficulty.put("count", 0.0);
            return difficulty;
        }

        //분류
        Map<Boolean, List<Integer>> ratingPart = ratings.stream()
                .map(Rating::getDifficulty)
                .collect(Collectors.partitioningBy(difficulty -> difficulty < 0));

        //쉬움  0 ~ -10
        double easy = ratingPart.get(true).stream()
                .mapToInt(Math::abs)
                .sum();

        //어려움 0 ~ 10
        double hard = ratingPart.get(false).stream()
                .mapToInt(Math::abs)
                .sum();

        // 쉬움과 어려움 각각의 평균 계산 (합 / 전체 수)
        double easyAvg = ratingPart.get(true).isEmpty() ? 0.0 : (easy / (double) ratingPart.get(true).size()) / 10.0;
        double hardAvg = ratingPart.get(false).isEmpty() ? 0.0 : (hard / (double) ratingPart.get(false).size()) / 10.0 ;

        Map<String, Double> difficulty = new HashMap<>();
        difficulty.put("easy", easyAvg);
        difficulty.put("hard", hardAvg);
        difficulty.put("count", count);
        return difficulty;
    }

}
