package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.Bookmark;
import com.example.bab_recipes.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByRecipeId(String id);

    int deleteByRecipeId(String recipeId);

    @Query("SELECT b FROM Bookmark b WHERE b.recipeId IN :recipeIds AND b.user = :user")
    List<Bookmark> findBookmarksByRecipeIdsAndUserId(@Param("recipeIds") List<String> recipeIds, @Param("user") User user);

    @Query("SELECT b FROM Bookmark b WHERE b.user.userId = :userId")
    List<Bookmark> findByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Bookmark b WHERE b.recipeId = :id AND b.user.id = :userId")
    Optional<Bookmark> findByRecipeIdAndUserId(@Param("id") String id, @Param("userId") Long userId);
}
