package com.example.bab_recipes.Controller;

import com.example.bab_recipes.DTO.RecipeDTO;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Repository.MongoRepository;
import com.example.bab_recipes.Service.PopularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.example.bab_recipes.Domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PopularController {

    @Autowired
    private MongoRepository mongoRepository;

    @Autowired
    private PopularService popularService;

    @GetMapping("/Popular")
    public String Popular(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        // 가장 인기 있는 10개의 레시피 가져오기
        List<MongoRecipe> popularRecipes = popularService.getTop10PopularRecipes();
        model.addAttribute("popularRecipes", popularRecipes);
        model.addAttribute("yeolmuRecipeId", "66c71a67d7342b8480a16441"); // 열무 recipeId 설정

        if (user != null) {
            model.addAttribute("email", user.getUserEmail());
        } else {
            return "redirect:/login";
        }

        // 레시피 리스트를 RecipeDTO로 변환
        List<RecipeDTO> recipeDTOList = popularRecipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getIngredients().entrySet().stream()
                                .limit(5)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                        recipe.getMediaUrl(),
                        false  // 인기 레시피이므로 북마크 여부는 false
                ))
                .collect(Collectors.toList());

        model.addAttribute("popularRecipes", recipeDTOList);

        return "popular";
    }
}
