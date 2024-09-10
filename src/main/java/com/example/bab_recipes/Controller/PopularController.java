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

//    @GetMapping("/Popular")
//    public String Popular(Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//
//        // 가장 많이 북마크된 10개의 레시피 가져오기
//        List<MongoRecipe> popularRecipes = popularService.getTop10PopularRecipes();
//        model.addAttribute("popularRecipes", popularRecipes);
//
//        if (user != null) {
//            model.addAttribute("email", user.getUserEmail());
//            System.out.println("userEmail: " + user.getUserEmail());
//        } else {
//            System.out.println("유저 로딩 실패");
//        }
//
//        if (user == null) {
//            return "redirect:/login";
//        }
//
//        // RecipeDTO로 변환
//        List<RecipeDTO> recipeDTOList = popularRecipes.stream()
//                .map(recipe -> new RecipeDTO(
//                        recipe.getId(),
//                        recipe.getTitle(),
//                        recipe.getIngredients().entrySet().stream()
//                                .limit(5)
//                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
//                        recipe.getMediaUrl(),
//                        false  // 인기 레시피이므로 북마크 여부는 false로 설정
//                ))
//                .collect(Collectors.toList());
//
//        model.addAttribute("popularRecipes", recipeDTOList);
//
//        return "popular";
//    }
@GetMapping("/Popular")
public String Popular(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");

    // 가장 인기 있는 10개의 레시피 가져오기
    List<MongoRecipe> popularRecipes = popularService.getTop10PopularRecipes();
    model.addAttribute("popularRecipes", popularRecipes);

//    // "열무 비빔국수" 레시피 가져오기
//    MongoRecipe yeolmuRecipe = popularService.getYeolmuRecipe();
//
//    // 레시피가 있는 경우 recipeId를 콘솔에 출력하고 모델에 추가
//    if (yeolmuRecipe != null) {
//        System.out.println("열무 비빔국수 레시피 ID: " + yeolmuRecipe.getId());
//        model.addAttribute("yeolmuRecipeId", yeolmuRecipe.getId());
//    } else {
//        // 레시피를 찾지 못한 경우 기본 recipeId 설정
//        System.out.println("열무 비빔국수 레시피를 찾지 못했습니다. 기본 recipeId로 이동합니다.");
//        model.addAttribute("yeolmuRecipeId", "66c6dadc80de336b44bb2f85"); // 열무 recipeId 설정
//    }
    model.addAttribute("yeolmuRecipeId", "66c6dadc80de336b44bb2f85"); // 열무 recipeId 설정

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
