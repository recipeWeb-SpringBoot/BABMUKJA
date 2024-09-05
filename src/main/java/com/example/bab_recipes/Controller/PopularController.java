package com.example.bab_recipes.Controller;

import org.springframework.ui.Model;
import com.example.bab_recipes.Domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PopularController {
    @GetMapping("/Popular")
    public String Popular(Model model, HttpSession session) {
        // userEmail
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("email", user.getUserEmail());
            System.out.println("userEmail: " + user.getUserEmail());
        } else {
            System.out.println("유저 로딩 실패");
        }
        return "/popular";
    }
}
