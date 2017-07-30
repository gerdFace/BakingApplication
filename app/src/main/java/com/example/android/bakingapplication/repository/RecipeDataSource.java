package com.example.android.bakingapplication.repository;

import android.support.annotation.NonNull;

import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.model.Step;

import java.util.List;

public interface RecipeDataSource {

    interface LoadRecipesCallback {

        void onRecipesLoaded(List<RecipeData> recipes);

        void onDataNotAvailable(String failureMessage);
    }

    interface GetRecipeCallback {

        void onRecipeLoaded(RecipeData recipe);

        void onDataNotAvailable(String failureMessage);
    }

    interface GetStepsCallback {

        void onStepsLoaded(List<Step> steps);

        void onDataNotAvailable();
    }

    void getRecipes(@NonNull LoadRecipesCallback callback);

    void getRecipe(int recipeId, @NonNull GetRecipeCallback callback);

    void getSteps(int recipeId, @NonNull GetStepsCallback callback);

    void refreshCache();

    void saveRecipesToDatabase(List<RecipeData> recipes);

}
