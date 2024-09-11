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

    public void addOrUpdateRating(Rating rating) {
        // 사용자와 레시피에 대해 기존 평가를 찾아서 업데이트 또는 새로운 평가로 저장
        Rating existingRating = ratingRepository.findByUserAndRecipeId(rating.getUser(), rating.getRecipeId());

        if (existingRating != null) {
            existingRating.setDifficulty(rating.getDifficulty());
            ratingRepository.save(existingRating);
        } else {
            ratingRepository.save(rating);
        }
    }

    public Map<String, Double> getRatings(String recipeId) {
        List<Rating> ratings = ratingRepository.findByRecipeId(recipeId);

        int count = ratings.size();
        if (count == 0) {
            // 평가가 없을 경우 기본 값
            Map<String, Double> difficulty = new HashMap<>();
            difficulty.put("easy", 0.0); // 초기값
            difficulty.put("hard", 0.0);
            difficulty.put("count", 0.0);
            return difficulty;
        }

        // 쉬움과 어려움 가중치 합계
        double totalEasyPercentage = 0;
        double totalHardPercentage = 0;

        // 각 사용자의 점수에 대해 비율 계산 (0~10 점수 기준)
        for (Rating rating : ratings) {
            int difficulty = rating.getDifficulty();

            // 어려움 비율: (점수 / 10) * 100
            double hardPercentage = (difficulty / 10.0) * 100;
            // 쉬움 비율: 100에서 어려움 비율을 뺀 값
            double easyPercentage = 100 - hardPercentage;

            totalHardPercentage += hardPercentage;
            totalEasyPercentage += easyPercentage;
        }

        // 평균 계산
        double easyAvg = totalEasyPercentage / count;
        double hardAvg = totalHardPercentage / count;

        // 소수점 첫째 자리에서 반올림
        easyAvg = Math.round(easyAvg * 10) / 10.0;
        hardAvg = Math.round(hardAvg * 10) / 10.0;

        Map<String, Double> difficulty = new HashMap<>();
        difficulty.put("easy", easyAvg);
        difficulty.put("hard", hardAvg);
        difficulty.put("count", (double) count);

        return difficulty;
    }
}
