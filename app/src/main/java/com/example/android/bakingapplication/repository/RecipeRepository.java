package com.example.android.bakingapplication.repository;

import android.support.annotation.NonNull;

public class RecipeRepository implements RecipeDataSource {
    @Override
    public void getRecipes(@NonNull LoadRecipesCallback callback) {

    }

    @Override
    public void getRecipe(int recipeId, @NonNull GetRecipeCallback callback) {

    }

    @Override
    public void getSteps(int recipeId, @NonNull GetStepsCallback callback) {

    }
}
