package com.example.android.bakingapplication.repository;

import com.example.android.bakingapplication.model.RecipeData;
import io.realm.RealmResults;

public interface RealmRecipeRepository {

    RealmResults<RecipeData> getAllRecipes();

    RecipeData getSelectedRecipe();

    void setSelectedRecipe(RecipeData recipe);
}
