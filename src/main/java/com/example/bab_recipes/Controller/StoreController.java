package com.example.bab_recipes.Controller;

import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Service.StoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/store")
    public String searchStore(
            @RequestParam(value = "query", required = false) String query,
            Model model, HttpSession session) {

        if (query != null && !query.isEmpty()) {
            List<Map<String, String>> stores = storeService.fetchStoreData(query);
            model.addAttribute("stores", stores);
            model.addAttribute("currentQuery", query);
            model.addAttribute("isDefaultSearch", false);  // 기본 검색이 아님

        } else {
            query = "밀키트";  // 기본 검색어 설정
            List<Map<String, String>> stores = storeService.fetchStoreData(query);
            model.addAttribute("stores", stores);
            model.addAttribute("currentQuery", query);
            model.addAttribute("isDefaultSearch", true);  // 기본 검색임
        }

        // userEmail
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("email", user.getUserEmail());
            System.out.println("userEmail: " + user.getUserEmail());
        } else {
            System.out.println("유저 로딩 실패");
        }

        return "store";
    }
}
