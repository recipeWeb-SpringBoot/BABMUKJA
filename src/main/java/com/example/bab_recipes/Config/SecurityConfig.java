package com.example.bab_recipes.Config;

import com.example.bab_recipes.Service.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // security에서 제공하는 로그인창 제거
public class SecurityConfig {

    private final UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 개발 중에는 CSRF 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/webjars/**", "/login").permitAll() // 로그인 경로 및 리소스 경로에 대한 접근 허용
                        .requestMatchers("/main", "/mainSearch", "/recipes", "/Recipe/**", "/Popular",
                                "/store", "/Category", "/Category/more").permitAll() // 게스트도 접근 가능하도록 설정
                        .requestMatchers("/board/**").hasRole("USER") // /board 경로는 USER 권한 필요
                        .anyRequest().authenticated() // 그 외의 요청은 인증 필요
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // /login 경로로 이동 시 login.html이 보이도록 설정
                        .defaultSuccessUrl("/main", true) // 로그인 성공 시 main 페이지로 이동
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                );

        return http.build();
    }

}
