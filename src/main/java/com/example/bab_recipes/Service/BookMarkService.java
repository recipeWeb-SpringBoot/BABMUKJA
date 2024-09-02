package com.example.bab_recipes.Service;

import com.example.bab_recipes.Domain.Bookmark;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Repository.BookmarkRepository;
import com.example.bab_recipes.Repository.mongoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookMarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private mongoRepository mongoRepository;
    //단일 북마크설정여부
    public Optional<Bookmark> statusMark(String id) {
        return bookmarkRepository.findByRecipeId(id);
    }

    //리스트 북마크 설정여부
    public List<Bookmark> getAllBookmarks(List<String> recipeIds) {
        return bookmarkRepository.findBookmarksByRecipeIds(recipeIds);
    }

    public Bookmark addBookmark(Bookmark bookmark) {
        String recipeId = bookmark.getRecipeId();
        Optional<Bookmark> byRecipeId = bookmarkRepository.findByRecipeId(recipeId);
        if (byRecipeId.isPresent()) {
            return bookmark;
        }else{
            return bookmarkRepository.save(bookmark);
        }

    }

    @Transactional
    public int DeleteBookmark(String recipeId) {
        return bookmarkRepository.deleteByRecipeId(recipeId);
    }


    //사용자가 설정한 북마크 불러오기
    public List<Bookmark> getUserBookmarkRecipe(Long userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    //불러온 북마크 아이디로 몽고데이터 검색
    public List<MongoRecipe> searchAllRecipe(List<String> recipeIds) {
        List<MongoRecipe> mongoRecipeList = new ArrayList<>();

        for (String recipeId : recipeIds) {
            mongoRepository.findById(recipeId).ifPresent(mongoRecipeList::add);
        }

        return mongoRecipeList;
    }
}
