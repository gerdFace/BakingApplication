package com.example.android.bakingapplication.presentation;

import android.util.Log;

import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.fragment.DetailListFragmentView;

public class DetailListFragmentPresenterImpl implements DetailListFragmentPresenter {

    private static String TAG = DetailListFragmentPresenterImpl.class.getSimpleName();

    private DetailListFragmentView view;
    private RecipeRepository recipeRepository;

    public DetailListFragmentPresenterImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void loadSteps() {
        int recipeId = view.getRecipeId();

        recipeRepository.getRecipe(recipeId, new RecipeRepository.GetRecipeCallback() {
            @Override
            public void onRecipeLoaded(RecipeData recipe) {
                view.showSteps(recipe.getSteps());
                view.updateWidgets();
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {
                Log.d(TAG, "Could not load steps " + failureMessage);
            }
        });
    }

    @Override
    public void setView(DetailListFragmentView view) {
        this.view = view;
        loadSteps();
    }
}
