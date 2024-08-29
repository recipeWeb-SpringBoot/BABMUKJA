package com.example.bab_recipes.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Bookmark")
@Entity
public class Bookmark {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    //fetch = FetchType -> 지연로딩
    @ManyToOne(fetch = FetchType.LAZY) //Many = bookmark, one = user ->한명의 유저가 많은 북마크를 남길수 있다.
    @JoinColumn(name="userId") //foreign key(userId) reference User (UserId)
    private User user;

    //mongo
    @Getter
    @Setter
    @Column(name = "recipeId", unique = true)
    private String recipeId;

    @Getter
    @Setter
    @Column(name = "isBookmark", columnDefinition = "int default 1")
    private int isBookmark;



    public Bookmark() {
    }

    public Bookmark(User user, String recipeId, int isBookmark) {
        this.user = user;
        this.recipeId = recipeId;
        this.isBookmark = isBookmark;
    }

    public Bookmark(int isBookmark) {
        this.isBookmark = isBookmark;
    }

    public Bookmark(String id) {
        this.recipeId = id;
    }
}
