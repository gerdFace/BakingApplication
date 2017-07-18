package com.example.android.bakingapplication.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeClient {
    private static final String ROOT_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/5907926b_baking";

//    private static Retrofit provideRetrofitInstance(String baseUrl) {
//        return new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    public static RecipeService provideRecipeService() {
//        return provideRetrofitInstance(ROOT_URL).create(RecipeService.class);
//    }
}
