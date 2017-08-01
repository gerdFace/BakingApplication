package com.example.android.bakingapplication.repository.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.repository.RecipeDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
public class RecipeNetworkSource implements RecipeDataSource {

    private static final String TAG = RecipeNetworkSource.class.getSimpleName();

    private Retrofit retrofit;

    private List<RecipeData> recipeList;

    @Inject
    public RecipeNetworkSource(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public void getRecipes(@NonNull final LoadRecipesCallback callback) {

        Call<List<RecipeData>> recipeCall = retrofit.create(RecipeService.class).getRecipes();

        recipeCall.enqueue(new Callback<List<RecipeData>>() {

            @Override
            public void onResponse(Call<List<RecipeData>> call, Response<List<RecipeData>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    recipeList = response.body();
                    callback.onRecipesLoaded(recipeList);
                    Log.d(TAG, "Recipes loaded: " + recipeList);
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
