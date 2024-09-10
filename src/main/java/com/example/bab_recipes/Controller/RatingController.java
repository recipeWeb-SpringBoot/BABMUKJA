package com.example.bab_recipes.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RatingController {

    @PostMapping("/addDifficulty")
    public ResponseEntity<String> addDifficulty(@RequestBody Map<String, String> data) {
        String difficulty = data.get("difficulty");
        System.out.println("난이도는 : " + difficulty);
        return ResponseEntity.ok("수신");
    }

}
