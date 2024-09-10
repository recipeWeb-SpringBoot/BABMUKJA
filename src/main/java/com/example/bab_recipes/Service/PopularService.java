package com.example.bab_recipes.Service;

import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Repository.BookmarkRepository;
import com.example.bab_recipes.Repository.MongoRepository;
import com.example.bab_recipes.Repository.PopularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PopularService {

    @Autowired
    private PopularRepository popularRepository;

    @Autowired
    private MongoRepository mongoRepository;

    // 가장 북마크가 많이 된 10개의 레시피를 가져오는 메소드
    public List<MongoRecipe> getTop10PopularRecipes() {
        // 가장 많이 북마크 된 10개의 recipeId를 가져옴
        List<String> topRecipeIds = popularRepository.findTop5PopularRecipes().stream()
                .limit(10)  // 10개의 레시피로 제한
                .collect(Collectors.toList());

        // recipeId로 MongoDB에서 레시피 정보를 가져옴
        return mongoRepository.findAllById(topRecipeIds);
    }
}
