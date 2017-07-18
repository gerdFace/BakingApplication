package com.example.android.bakingapplication.retrofit;

import com.example.android.bakingapplication.model.RecipeDatum;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("/topher/2017/May/5907926b_baking/baking.json")
    Call<List<RecipeDatum>> getRecipes();
}
