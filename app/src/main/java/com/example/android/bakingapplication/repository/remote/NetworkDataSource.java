package com.example.android.bakingapplication.repository.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.repository.RecipeRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NetworkDataSource implements RecipeRepository {

    private Retrofit retrofit;

    private List<RecipeData> recipeList;

    public NetworkDataSource(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public void getRecipes(@NonNull final LoadRecipesCallback callback) {

        Call<List<RecipeData>> recipeCall = retrofit.create(NetworkService.class).getRecipes();

        recipeCall.enqueue(new Callback<List<RecipeData>>() {

            @Override
            public void onResponse(Call<List<RecipeData>> call, Response<List<RecipeData>> response) {
                if (response.isSuccessful()) {
                    recipeList = response.body();
                    callback.onRecipesLoaded(recipeList);
                }
            }

            @Override
            public void onFailure(Call<List<RecipeData>> call, Throwable t) {
                callback.onDataNotAvailable("Could not load recipe data from network path" + t.toString());
            }
        });
    }

    @Override
    public void getRecipe(int recipeId, @NonNull GetRecipeCallback callback) {

    }

    @Override
    public void getSteps(int recipeId, @NonNull GetStepsCallback callback) {

    }

    @Override
    public void getIngredients(int recipeId, @NonNull GetIngredientsCallback callback) {

    }

    @Override
    public void refreshRecipes() {

    }

    @Override
    public void saveRecipesToDatabase(List<RecipeData> recipes) {

    }
}
