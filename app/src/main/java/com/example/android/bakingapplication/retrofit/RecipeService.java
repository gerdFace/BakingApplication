package com.example.android.bakingapplication.retrofit;

import com.example.android.bakingapplication.model.RecipeDatum;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("/baking.json")
    Call<RecipeDatum> getMyJSON();
}
