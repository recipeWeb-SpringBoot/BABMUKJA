package com.example.bab_recipes.Domain;


import jakarta.persistence.*;

@Table(name = "Search")
@Entity
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;


}
