package com.example.bab_recipes.Domain;

import com.nimbusds.oauth2.sdk.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Table(name = "User")
@Entity
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Getter
    @Setter
    @Column(name = "userEmail", nullable = false, length = 100)
    private String userEmail;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    public String getRoleKey(){
        return this.role.toString();
    }

    @Getter
    @RequiredArgsConstructor
    public enum Role {

        GUEST("ROLE_GUEST", "비회원"),
        USER("ROLE_USER", "회원");

        private final String key;
        private final String value;
    }

    public static User setUser(Long userId, String userEmail, Role role) {
        User user = new User();
        user.userId = userId;
        user.userEmail = userEmail;
        user.role = role;
        return user;

    }
}
