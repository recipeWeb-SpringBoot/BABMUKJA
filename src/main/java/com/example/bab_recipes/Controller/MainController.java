package com.example.bab_recipes.Controller;

import jakarta.servlet.http.HttpSession;
import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Repository.MainRepository;
import org.springframework.ui.Model;
import com.example.bab_recipes.DTO.RecipeDTO;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private MainService mainService;
    @Autowired
    private MainRepository mainRepository;


    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            System.out.println("userEmail: " + user.getUserEmail());
            model.addAttribute("email", user.getUserEmail());
        } else {
            System.out.println("로그인 실패");
        }

        return "main";
    }

    @GetMapping("/popular")
    public String popular() {
        return "popular";
    }

    @GetMapping("/mainSearch")
    public String mainSearch(@RequestParam(value = "query", required = false) String query, HttpSession session, Model model) {
        if (query == null || query.trim().isEmpty()) {
            model.addAttribute("recipes", Collections.emptyList());
            return "/main";
        }

        List<MongoRecipe> recipes = mainService.searchRecipes(query);
        List<RecipeDTO> recipeDTO = recipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getIngredients().entrySet().stream()
                                .limit(5) // 5개 재료만 추출
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                        recipe.getMediaUrl(),
                        false))
                .collect(Collectors.toList());

        model.addAttribute("recipes", recipeDTO);

        User user = (User) session.getAttribute("user");

        if (user != null) {
            System.out.println("userEmail: " + user.getUserEmail());
            model.addAttribute("email", user.getUserEmail());
        } else {
            System.out.println("로그인 실패");
        }

        return "/main";
    }

//    @GetMapping("/recipe/{recipeId}")
//    public String recipeDetail(@PathVariable("recipeId") String recipeId, Model model, HttpSession session) {
//        Optional<MongoRecipe> recipeOptional = mainRepository.findById(recipeId);
//        if (recipeOptional.isPresent()) {
//            MongoRecipe recipe = recipeOptional.get();
//            RecipeDTO recipeDTO = new RecipeDTO(
//                    recipe.getId(),
//                    recipe.getTitle(),
//                    recipe.getIngredients(),
//                    recipe.getMediaUrl(),
//                    false
//            );
//            model.addAttribute("recipe", recipeDTO);
//            return "Recipes_detail";
//        }
//
//        // user
//        User user = (User) session.getAttribute("user");
//
//        if (user != null) {
//            model.addAttribute("email", user.getUserEmail());
//            System.out.println("userEmail: " + user.getUserEmail());
//        } else {
//            System.out.println("유저 로딩 실패");
//        }
//
//        return "redirect:/main";
//    }
}
