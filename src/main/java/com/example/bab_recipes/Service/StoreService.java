package com.example.bab_recipes.Service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StoreService {

    @Autowired
    private NaverApiService naverApiService;

    public List<Map<String, String>> fetchStoreData(String query) {
        int display = 100; // 네이버 api가 최대 100개로 한정
        String jsonResponse = naverApiService.searchStore(query, display);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray items = jsonObject.getJSONArray("items");

        List<Map<String, String>> storeData = new ArrayList<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);

            Map<String, String> store = Map.of(
                    "title", item.getString("title").replaceAll("<.*?>", ""),
                    "image", item.getString("image"),
                    "lprice", item.getString("lprice"),
                    "mallName", item.getString("mallName"),
                    "link", item.getString("link")
            );

            storeData.add(store);
        }

        return storeData;
    }
}
