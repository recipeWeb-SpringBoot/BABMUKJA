package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByRecipeId(String id);

    int deleteByRecipeId(String recipeId);

    @Query("SELECT b FROM Bookmark b where b.recipeId IN :recipeIds")
    List<Bookmark> findBookmarksByRecipeIds(@Param("recipeIds") List<String> recipeIds);

    @Query("SELECT b FROM Bookmark b WHERE b.user.userId = :userId")
    List<Bookmark> findByUserId(@Param("userId") Long userId);
}
