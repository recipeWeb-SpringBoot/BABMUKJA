package com.example.bab_recipes.Service;

import com.example.bab_recipes.Domain.Bookmark;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Repository.BookmarkRepository;
import com.example.bab_recipes.Repository.MongoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookMarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private MongoRepository mongoRepository;

    //단일 북마크설정여부
    public Optional<Bookmark> statusMark(String id) {
        return bookmarkRepository.findByRecipeId(id);
    }

    //리스트 북마크 설정여부
    public List<Bookmark> getAllBookmarks(List<String> recipeIds) {
        return bookmarkRepository.findBookmarksByRecipeIds(recipeIds);
    }

    //북마크 추가
    public Bookmark addBookmark(Bookmark bookmark) {
        String recipeId = bookmark.getRecipeId();
        Optional<Bookmark> byRecipeId = bookmarkRepository.findByRecipeId(recipeId);
        if (byRecipeId.isPresent()) {
            return bookmark;
        }else{
            return bookmarkRepository.save(bookmark);
        }

    }

    //북마크 제거
    @Transactional
    public int DeleteBookmark(String recipeId) {
        return bookmarkRepository.deleteByRecipeId(recipeId);
    }


    //북마크 페이지 로딩 로직
    public List<MongoRecipe> searchAllRecipeForUserBookmark(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);
        List<MongoRecipe> mongoRecipeList = new ArrayList<>();

        for (Bookmark bookmark : bookmarks) {
            mongoRepository.findById(bookmark.getRecipeId()).ifPresent(mongoRecipeList::add);
        }

        return mongoRecipeList;
    }

}
