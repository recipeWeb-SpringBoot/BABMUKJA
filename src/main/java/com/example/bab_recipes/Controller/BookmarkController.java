package com.example.bab_recipes.Controller;

import com.example.bab_recipes.DTO.RecipeDTO;
import com.example.bab_recipes.Domain.Bookmark;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Service.BookMarkService;
import com.example.bab_recipes.Service.TodayService;
import com.example.bab_recipes.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BookmarkController {

    @Autowired
    private BookMarkService bookMarkService;
    @Autowired
    private UserService userService;

    @Autowired
    private TodayService todayService;


    //북마크 생성 / 제거
    @PostMapping("/addBookmark")
    public ResponseEntity<Map<String, Object>> addBookmark(@RequestBody Bookmark bookmark, HttpSession session) {
        String recipeId = bookmark.getRecipeId();
        int isBookMark = bookmark.getIsBookmark();
        User user = (User) session.getAttribute("user");
        System.out.println("isBookMark = " + isBookMark);
        System.out.println("recipeId = " + recipeId);
        Bookmark newMark = new Bookmark(user, recipeId, isBookMark);

        Map<String, Object> response = new HashMap<>();  // 변경: Map<String, String> -> Map<String, Object>

        try {
            Bookmark savedBookmark = bookMarkService.addBookmark(newMark);
            if (savedBookmark != null) {
                response.put("success", true);  // success 필드 추가
                response.put("message", "북마크 추가 완료");
                System.out.println("북마크추가완료");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);  // success 필드 추가
                response.put("message", "북마크 추가 중 오류 발생");
                System.out.println("북마크 추가 실패");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (DataIntegrityViolationException e) {
            // 중복 데이터 예외 처리
            response.put("success", false);  // success 필드 추가
            response.put("message", "이미 존재하는 북마크입니다.");
            System.out.println("북마크 중복");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            // 예외 발생 시 로그 기록
            e.printStackTrace();
            System.out.println("통신오류");
            System.out.println(e.getMessage());
            response.put("success", false);  // success 필드 추가
            response.put("message", "서버 오류 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/removeBookmark")
    public ResponseEntity<Map<String,Object>> removeBookmark(@RequestBody Bookmark bookmark) {
        String recipeId = bookmark.getRecipeId();
        Map<String, Object> response = new HashMap<>();

        try {
            int result = bookMarkService.DeleteBookmark(recipeId);
            if (result > 0) {
                response.put("success", true);  // success 필드 추가
                response.put("message", "북마크제거완료");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);  // success 필드 추가
                response.put("message", "서버 오류발생");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            response.put("success", false);  // success 필드 추가
            response.put("message", "Exception 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }


    @GetMapping("/bookmark")
    public String bookmark(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        try{
            if(user == null) {
                return "redirect:/login";
            }else{
                // MySQL 데이터베이스에서 북마크 설정된 레시피 가져오기
                List<Bookmark> userBookmarkRecipe = bookMarkService.getUserBookmarkRecipe(user.getUserId());

                // 가져온 북마크에서 레시피 아이디 추출
                List<String> recipeIds = userBookmarkRecipe.stream()
                        .map(Bookmark::getRecipeId)
                        .toList();

                // 추출한 레시피 아이디로 MongoDB 검색
                List<MongoRecipe> searchRecipe = bookMarkService.searchAllRecipe(recipeIds);

                // MongoRecipe 리스트를 Map으로 변환 (recipeId를 키로 사용)
                Map<String, MongoRecipe> recipeMap = searchRecipe.stream()
                        .collect(Collectors.toMap(MongoRecipe::getId, recipe -> recipe));

                // RecipeDTO 리스트 생성
                List<RecipeDTO> recipeDTOList = userBookmarkRecipe.stream()
                        .map(bookmark -> {
                            MongoRecipe mongoRecipe = recipeMap.get(bookmark.getRecipeId());
                            if (mongoRecipe != null) {
                                return new RecipeDTO(
                                        mongoRecipe.getId(),
                                        mongoRecipe.getTitle(),
                                        mongoRecipe.getIngredients(),
                                        mongoRecipe.getMediaUrl(),
                                        bookmark.getIsBookmark() == 1
                                );
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                // 이후 DTO 리스트를 뷰로 전달
                model.addAttribute("recipes", recipeDTOList);

                return "bookmark";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }

    }
}
