package com.example.bab_recipes.Controller;

import com.example.bab_recipes.DTO.RecipeDTO;
import com.example.bab_recipes.Domain.Bookmark;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Service.BookMarkService;
import com.example.bab_recipes.Service.TodayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TodayController {


    @Autowired
    private TodayService todayService;

    @Autowired
    private BookMarkService markService;

    private MockHttpSession session;
    private List<String> fridgeItems;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();

        // 실제 데이터로 세션 설정
        fridgeItems = Arrays.asList("감자", "양파");
        List<MongoRecipe> recipes = todayService.searchOne(fridgeItems.toArray(new String[0]));
        session.setAttribute("recipes", recipes);
        session.setAttribute("fridgeItems", fridgeItems);

        List<String> excludeItems = Arrays.asList("우유", "계란");
        session.setAttribute("excludedItems", excludeItems);
    }

    @Test
    void testRecipeDTOCreation() {
        // given
        List<MongoRecipe> recipes = (List<MongoRecipe>) session.getAttribute("recipes");
        assertNotNull(recipes, "레시피 목록 null X");
        assertFalse(recipes.isEmpty(), "레시피 목록 null X");

        // MongoDB에서 레시피 ID 목록 추출
        List<String> recipeIds = recipes.stream()
                .map(MongoRecipe::getId)
                .collect(Collectors.toList());

        // MySQL에서 북마크 정보 조회
        List<Bookmark> bookmarks = markService.getAllBookmarks(recipeIds);

        // 북마크된 레시피 ID 목록 생성
        List<String> bookmarkedRecipeIds = bookmarks.stream()
                .map(Bookmark::getRecipeId)
                .collect(Collectors.toList());

        // when
        List<RecipeDTO> recipeDTOs = recipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getIngredients(),
                        recipe.getMediaUrl(),
                        bookmarkedRecipeIds.contains(recipe.getId())
                ))
                .collect(Collectors.toList());

        // then
        assertFalse(recipeDTOs.isEmpty(), "RecipeDTO 목록 null X");

        for (RecipeDTO dto : recipeDTOs) {
            assertNotNull(dto.getId(), "레시피 ID null X");
            assertNotNull(dto.getTitle(), "레시피 제목 null X");
            assertNotNull(dto.getIngredients(), "레시피 재료 목록 null X");
            assertNotNull(dto.getMediaUrl(), "레시피 미디어 URL null X");

            // 북마크 여부 확인
            assertEquals(bookmarkedRecipeIds.contains(dto.getId()), dto.isBookmarked(),
                    "북마크 상태가 일치");
        }

        // fridgeItems 확인
        List<String> sessionFridgeItems = (List<String>) session.getAttribute("fridgeItems");
        assertEquals(fridgeItems, sessionFridgeItems, "세션의 냉장고 항목 일치");

        // excludeItems 확인
        List<String> excludeItems = (List<String>) session.getAttribute("excludedItems");
        assertEquals(Arrays.asList("우유", "계란"), excludeItems, "세션의 제외 항목 일치");
    }
}