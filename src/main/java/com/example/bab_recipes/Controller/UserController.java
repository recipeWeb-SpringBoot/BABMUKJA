package com.example.bab_recipes.Controller;

import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String loginSuccess(Model model, HttpSession session) {
        // 세션에서 User 객체를 가져옴
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // 세션에 User 객체가 없으면 SecurityContext에서 사용자 정보를 가져옴
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                String userEmail = (String) oAuth2User.getAttributes().get("email");

                // 데이터베이스에서 사용자 정보 조회
                user = userRepository.findByUserEmail(userEmail).orElse(null);
                if (user != null) {
                    // 세션에 사용자 정보 저장
                    session.setAttribute("user", user);
                }
            }
        }

        if (user != null) {
            model.addAttribute("email", user.getUserEmail());
            System.out.println("userEmail: " + user.getUserEmail());
        } else {
            System.out.println("유저 로딩 실패");
        }

        return "main";
    }


    static boolean checkUser(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("email", user.getUserEmail());
            System.out.println("userEmail: " + user.getUserEmail());
        } else {
            System.out.println("유저 로딩 실패");
        }

        return user == null;
    }

}
