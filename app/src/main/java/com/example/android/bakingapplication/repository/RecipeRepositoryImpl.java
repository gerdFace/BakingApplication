package com.example.android.bakingapplication.repository;

import android.support.annotation.NonNull;

import com.example.android.bakingapplication.dagger.Local;
import com.example.android.bakingapplication.dagger.Network;
import com.example.android.bakingapplication.model.Ingredient;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.model.Step;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecipeRepositoryImpl implements RecipeRepository {

    private RecipeRepository recipeDatabaseSource;

    private RecipeRepository recipeNetworkSource;

    Map<Integer, RecipeData> cachedRecipes;
    private boolean cacheIsDirty = false;

    public RecipeRepositoryImpl(@Local RecipeRepository recipeDatabaseSource, @Network RecipeRepository recipeNetworkSource) {
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
                callback.onDataNotAvailable("Could not load recipe data from network");
            }
        });
    }

    @Override
    public void getRecipe(int recipeId, @NonNull GetRecipeCallback callback) {
        RecipeData cachedRecipe = getRecipeWithId(recipeId);

        if (cachedRecipe != null) {
            callback.onRecipeLoaded(cachedRecipe);
            return;
        }

        recipeDatabaseSource.getRecipe(recipeId, new GetRecipeCallback() {
            @Override
            public void onRecipeLoaded(RecipeData recipe) {
                callback.onRecipeLoaded(recipe);
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {
                callback.onDataNotAvailable("Failed to load recipe from network");
            }
        });
    }

    @Override
    public void getSteps(int recipeId, @NonNull final GetStepsCallback callback) {

        List<Step> cachedSteps = getRecipeWithId(recipeId).getSteps();

        if (cachedSteps != null) {
            callback.onStepsLoaded(cachedSteps);
            return;
        }

        recipeDatabaseSource.getSteps(recipeId, new GetStepsCallback() {
            @Override
            public void onStepsLoaded(List<Step> steps) {
                callback.onStepsLoaded(steps);
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {
                callback.onDataNotAvailable("Failed to load steps list from Realm database");
            }
        });
    }

    @Override
    public void getIngredients(int recipeId, @NonNull GetIngredientsCallback callback) {

        List<Ingredient> cachedIngredients = getRecipeWithId(recipeId).getIngredients();

        if (cachedIngredients != null) {
            callback.onIngredientsLoaded(cachedIngredients);
        }

        recipeDatabaseSource.getIngredients(recipeId, new GetIngredientsCallback() {
            @Override
            public void onIngredientsLoaded(List<Ingredient> ingredients) {

            }

            @Override
            public void onDataNotAvailable(String failureMessage) {

            }
        });
    }

    @Override
    public void refreshRecipes() {
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
            cachedRecipes.put(recipe.getId(), recipe);
        }

        cacheIsDirty = false;
    }

    private void refreshDatabase(List<RecipeData> recipes) {
        recipeDatabaseSource.saveRecipesToDatabase(recipes);
    }

    private RecipeData getRecipeWithId(int id) {
        if (cachedRecipes == null || cachedRecipes.isEmpty()) {
            return null;
        } else {
            return cachedRecipes.get(id);
        }
    }
}
