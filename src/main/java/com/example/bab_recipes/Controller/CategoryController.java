package com.example.bab_recipes.Controller;

import com.example.bab_recipes.DTO.RecipeDTO;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Repository.CategoryRepository;
import com.example.bab_recipes.Repository.MainRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CategoryController {

    private final MainRepository mainRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryController(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }


    @GetMapping("/Category")
    public String getRecipesByCategory(Model model, @RequestParam(defaultValue = "한식") String category, @RequestParam(defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 15);
        Page<MongoRecipe> recipesPage = categoryRepository.findByCategory(category, pageRequest);

        // 각 레시피의 재료 중에서 5개만 가져오는 로직 추가
        List<MongoRecipe> limitedRecipes = recipesPage.getContent().stream()
                .map(this::limitIngredients) // 각 레시피의 재료를 5개로 제한
                .collect(Collectors.toList());

        model.addAttribute("recipes", limitedRecipes);
        model.addAttribute("currentPage", page);
        model.addAttribute("category", category);
        model.addAttribute("hasNext", recipesPage.hasNext()); // 다음 페이지가 있는지 확인
        return "main";
    }

    @GetMapping("/Category/more")
    @ResponseBody
    public List<MongoRecipe> loadMoreRecipes(@RequestParam String category, @RequestParam int page) {
        PageRequest pageRequest = PageRequest.of(page, 15);
        Page<MongoRecipe> recipesPage = categoryRepository.findByCategory(category, pageRequest);

        // 각 레시피의 재료 중에서 5개만 가져오는 로직 추가
        return recipesPage.getContent().stream()
                .map(this::limitIngredients) // 각 레시피의 재료를 5개로 제한
                .collect(Collectors.toList()); // JSON 응답
    }

    // 각 레시피의 재료를 5개로 제한하는 메소드
    private MongoRecipe limitIngredients(MongoRecipe recipe) {
        // ingredients 필드가 Map<String, String>이라고 가정
        Map<String, String> limitedIngredients = recipe.getIngredients().entrySet().stream()
                .limit(5) // 최대 5개의 재료만 선택
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        recipe.setIngredients(limitedIngredients); // 제한된 재료로 설정
        return recipe;
    }

    @GetMapping("/Recipe/{recipeId}")
    public String recipeDetail(@PathVariable("recipeId") String recipeId, Model model, HttpSession session) {
        Optional<MongoRecipe> recipeOptional = mainRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            MongoRecipe recipe = recipeOptional.get();
            RecipeDTO recipeDTO = new RecipeDTO(
                    recipe.getId(),
                    recipe.getTitle(),
                    recipe.getIngredients(),
                    recipe.getMediaUrl(),
                    false
            );
            model.addAttribute("category", recipeDTO);
            return "Recipes_detail";
        }

        // user
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("email", user.getUserEmail());
            System.out.println("userEmail: " + user.getUserEmail());
        } else {
            System.out.println("유저 로딩 실패");
        }
        return "redirect:/main";
    }
}