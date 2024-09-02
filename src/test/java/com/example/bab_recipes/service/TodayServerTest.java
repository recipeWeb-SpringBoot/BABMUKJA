package com.example.bab_recipes.service;

import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Repository.mongoRepository;
import com.example.bab_recipes.Service.TodayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TodayServerTest {

    @Autowired
    private TodayService todayService;

    @Autowired
    private mongoRepository mongoRepository;

    private long totalRecipes;

    @BeforeEach
    public void setup() {
        totalRecipes = mongoRepository.count();
        System.out.println("Total recipes in database: " + totalRecipes);
        assertTrue(totalRecipes > 0, "데이터베이스에 레시피가 존재해야 합니다.");
    }

    @Test
    public void testSearchRecipesWithCommonIngredients() {
        // Given: 일반적인 재료로 검색하는 상황
        String[] fridgeItems = {"고춧가루", "대파"};
        String[] excludeItems = {};

        // When: 레시피 검색을 수행할 때
        List<MongoRecipe> results = todayService.searchRecipes(fridgeItems, excludeItems);

        // Then: 검색 결과가 있어야 함
        assertFalse(results.isEmpty(), "일반적인 재료로 검색 시 결과가 있어야 합니다.");
        results.forEach(recipe -> {
            assertTrue(recipe.getIngredients_text().contains("고춧가루") || recipe.getIngredients_text().contains("대파"),
                    "검색된 레시피는 고춧가루 또는 대파를 포함해야 합니다.");
        });
        System.out.println("Found " + results.size() + " recipes with 고춧가루 or 대파");
    }

    @Test
    public void testSearchRecipesWithExcludedIngredients() {
        // Given: 특정 재료를 제외하고 검색하는 상황
        String[] fridgeItems = {"식용유"};
        String[] excludeItems = {"고기"};

        // When: 레시피 검색을 수행할 때
        List<MongoRecipe> results = todayService.searchRecipes(fridgeItems, excludeItems);

        // Then: 검색 결과가 조건을 만족해야 함
        assertFalse(results.isEmpty(), "식용유를 포함하고 고기를 제외한 레시피가 있어야 합니다.");
        results.forEach(recipe -> {
            assertTrue(recipe.getIngredients_text().contains("식용유"), "검색된 레시피는 식용유를 포함해야 합니다.");
            assertFalse(recipe.getIngredients_text().contains("고기"), "검색된 레시피는 고기를 포함하지 않아야 합니다.");
        });
        System.out.println("Found " + results.size() + " recipes with 식용유 and without 고기");
    }

    @Test
    public void testSearchRecipesWithMultipleIngredients() {
        // Given: 여러 재료로 검색하는 상황
        String[] fridgeItems = {"식용유", "고춧가루", "대파"};
        String[] excludeItems = {};

        // When: 레시피 검색을 수행할 때
        List<MongoRecipe> results = todayService.searchRecipes(fridgeItems, excludeItems);

        // Then: 검색 결과가 있어야 하며, 최소한 하나의 지정된 재료를 포함해야 함
        assertFalse(results.isEmpty(), "지정된 재료 중 하나 이상을 포함하는 레시피가 있어야 합니다.");
        results.forEach(recipe -> {
            assertTrue(recipe.getIngredients_text().contains("식용유") ||
                            recipe.getIngredients_text().contains("고춧가루") ||
                            recipe.getIngredients_text().contains("대파"),
                    "검색된 레시피는 식용유, 고춧가루, 대파 중 하나 이상을 포함해야 합니다.");
        });
        System.out.println("Found " + results.size() + " recipes with 식용유, 고춧가루, or 대파");
    }

    @Test
    public void testSearchRecipesWithNonExistentIngredients() {
        // Given: 데이터베이스에 없을 것 같은 재료로 검색하는 상황
        String[] fridgeItems = {"유니콘의 눈물", "용의 비늘"};
        String[] excludeItems = {};

        // When: 레시피 검색을 수행할 때
        List<MongoRecipe> results = todayService.searchRecipes(fridgeItems, excludeItems);

        // Then: 검색 결과가 비어있어야 함
        assertTrue(results.isEmpty(), "존재하지 않는 재료로 검색 시 결과가 비어있어야 합니다.");
        System.out.println("Found " + results.size() + " recipes with non-existent ingredients");
    }
}