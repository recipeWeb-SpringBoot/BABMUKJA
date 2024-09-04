package com.example.bab_recipes.Controller;

import com.example.bab_recipes.Domain.Bookmark;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Repository.BookmarkRepository;
import com.example.bab_recipes.Repository.MongoRepository;
import com.example.bab_recipes.Service.BookMarkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookmarkServiceUnitTest {

    @Mock
    private MongoRepository mongoRepository;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @InjectMocks
    private BookMarkService bookMarkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSearchAllRecipeForUserBookmark() {
        // Given
        Long userId = 1L;
        List<Bookmark> bookmarks = Arrays.asList(
                new Bookmark(new User(userId), "recipe1", 1),
                new Bookmark(new User(userId), "recipe2", 1)
        );
        MongoRecipe recipe1 = new MongoRecipe("recipe1", "Recipe1", "Category1", null, null, "url1", "ingredients_text1");
        MongoRecipe recipe2 = new MongoRecipe("recipe2", "Recipe2", "Category2", null, null, "url2", "ingredients_text2");

        when(bookmarkRepository.findByUserId(userId)).thenReturn(bookmarks);
        when(mongoRepository.findById("recipe1")).thenReturn(Optional.of(recipe1));
        when(mongoRepository.findById("recipe2")).thenReturn(Optional.of(recipe2));

        // When
        List<MongoRecipe> result = bookMarkService.searchAllRecipeForUserBookmark(userId);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(recipe1));
        assertTrue(result.contains(recipe2));
        verify(bookmarkRepository).findByUserId(userId);
        verify(mongoRepository, times(2)).findById(anyString());
    }


    @Test
    public void testStatusMark_WhenBookmarkExists(){
        //Given
        String recipeId = "recipe1";
        Bookmark bookmark = new Bookmark(recipeId);

        when(bookmarkRepository.findByRecipeId(recipeId)).thenReturn(Optional.of(bookmark));

        //When
        Optional<Bookmark> result = bookMarkService.statusMark(recipeId);

        //Then
        assertEquals(Optional.of(bookmark), result);
    }

    @Test
    public void testStatusMark_WhenBookmarkNotExists(){
        //Given
        String recipeId = "recipe1";
        when(bookmarkRepository.findByRecipeId(recipeId)).thenReturn(Optional.empty());

        //When
        Optional<Bookmark> result = bookMarkService.statusMark(recipeId);

        //Then
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testGetAllBookmarks(){
        //Given
        List<String> recipeIds = Arrays.asList("recipe1", "recipe2");
        Bookmark mark1 = new Bookmark("recipe1");
        Bookmark mark2 = new Bookmark("recipe2");

        List<Bookmark> bookmarks = Arrays.asList(mark1, mark2);

        when(bookmarkRepository.findBookmarksByRecipeIds(recipeIds)).thenReturn(bookmarks);

        //When
        List<Bookmark> result = bookMarkService.getAllBookmarks(recipeIds);

        //Then
        assertEquals(bookmarks, result);
    }

    @Test
    public void testAddBookmark_AlreadyExists(){
        //Given
        String recipeId = "recipe1";
        Bookmark bookmark = new Bookmark(recipeId);

        when(bookmarkRepository.findByRecipeId(recipeId)).thenReturn(Optional.of(bookmark));

        //When
        Bookmark result = bookMarkService.addBookmark(bookmark);

        //Then
        assertEquals(bookmark, result);
        verify(bookmarkRepository, never()).save(any(Bookmark.class));
    }

    @Test
    public void testAddBookmark_BookmarkNotExists(){
        //Given
        String recipeId = "recipe1";
        Bookmark bookmark = new Bookmark(recipeId);

        when(bookmarkRepository.findByRecipeId(recipeId)).thenReturn(Optional.empty());
        when(bookmarkRepository.save(bookmark)).thenReturn(bookmark);

        //When
        Bookmark result = bookMarkService.addBookmark(bookmark);

        //Then
        assertEquals(bookmark, result);
        verify(bookmarkRepository).save(bookmark);
    }
}

