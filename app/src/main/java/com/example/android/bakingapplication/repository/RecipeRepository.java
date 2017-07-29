package com.example.android.bakingapplication.repository;

import android.support.annotation.NonNull;

import com.example.android.bakingapplication.model.RecipeData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeRepository implements RecipeDataSource {

    private final RecipeDataSource recipeDatabaseSource;

    private final RecipeDataSource recipeNetworkSource;

    Map<String, RecipeData> cachedRecipes;
    private boolean cacheIsDirty = false;

    @Inject
    RecipeRepository(@Network RecipeDataSource recipeDatabaseSource, @Local RecipeDataSource recipeNetworkSource) {
        this.recipeDatabaseSource = recipeDatabaseSource;
        this.recipeNetworkSource = recipeNetworkSource;
    }

    @Override
    public void getRecipes(@NonNull final LoadRecipesCallback callback) {
        if (cachedRecipes != null && !cacheIsDirty) {
            callback.onRecipesLoaded(new ArrayList<>(cachedRecipes.values()));
            return;
        }

        if (cacheIsDirty) {
            getRecipesFromNetworkSource(callback);
        } else {
            recipeDatabaseSource.getRecipes(new LoadRecipesCallback() {
                @Override
                public void onRecipesLoaded(List<RecipeData> recipes) {
                    refreshCache(recipes);
                    callback.onRecipesLoaded(new ArrayList<>(cachedRecipes.values()));
                }

                @Override
                public void onDataNotAvailable(String failureMessage) {
                    getRecipesFromNetworkSource(callback);
                }
            });
        }
    }

    private void getRecipesFromNetworkSource(@NonNull final LoadRecipesCallback callback) {
        recipeNetworkSource.getRecipes(new LoadRecipesCallback() {
            @Override
            public void onRecipesLoaded(List<RecipeData> recipes) {
                refreshCache(recipes);
                refreshDatabase(recipes);
                callback.onRecipesLoaded(new ArrayList<>(cachedRecipes.values()));
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {
                callback.onDataNotAvailable("Could not load recipe data from network path");
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
    public void refreshCache() {
        cacheIsDirty = true;
    }

    @Override
    public void saveRecipesToDatabase(List<RecipeData> recipes) {
    }

    private void refreshCache(List<RecipeData> recipes) {
        if (cachedRecipes == null) {
            cachedRecipes = new LinkedHashMap<>();
        }
        cachedRecipes.clear();
        for (RecipeData recipe : recipes) {
            cachedRecipes.put(recipe.getId().toString(), recipe);
        }

        cacheIsDirty = false;
    }

    private void refreshDatabase(List<RecipeData> recipes) {
        recipeDatabaseSource.saveRecipesToDatabase(recipes);
    }
}
