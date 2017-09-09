package com.example.android.bakingapplication.view.activity;

import com.example.android.bakingapplication.model.RecipeData;

import java.util.List;

public interface MainActivityView {

    void showRecipes(List<RecipeData> recipeList);

    void showErrorMessage(String errorMessage);

}
