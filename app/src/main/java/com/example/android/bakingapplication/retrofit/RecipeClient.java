package com.example.android.bakingapplication.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeClient {
    private static final String ROOT_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/5907926b_baking";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RecipeService getRecipeService() {
        return getRetrofitInstance().create(RecipeService.class);
    }
}
