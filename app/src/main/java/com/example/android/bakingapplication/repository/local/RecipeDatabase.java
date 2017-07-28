package com.example.android.bakingapplication.repository.local;

import android.support.annotation.NonNull;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.model.Step;
import com.example.android.bakingapplication.repository.RecipeDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.realm.Realm;

public class RecipeDatabase implements RecipeDataSource {

    @Inject
    private
    Realm realm;

    @Override
    public void getRecipes(@NonNull LoadRecipesCallback callback) {
        List<RecipeData> recipes = new ArrayList<>();
        recipes = realm.where(RecipeData.class).findAll();

        realm.close();
    }

    @Override
    public void getRecipe(int recipeId, @NonNull GetRecipeCallback callback) {
        RecipeData recipe;
        recipe = realm.where(RecipeData.class)
                .equalTo("id", recipeId)
                .findFirst();

        realm.close();
    }

    @Override
    public void getSteps(int recipeId, @NonNull GetStepsCallback callback) {
        List<Step> steps = new ArrayList<>();
        steps = realm.where(RecipeData.class)
                .equalTo("id", recipeId)
                .findFirst().getSteps();

        realm.close();
    }
}
