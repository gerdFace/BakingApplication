package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.model.Ingredient;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.fragment.IngredientsFragmentView;

import java.util.List;

public class IngredientsFragmentPresenterImpl implements IngredientsFragmentPresenter {

    private RecipeRepository recipeRepository;
    private IngredientsFragmentView view;

    public IngredientsFragmentPresenterImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void loadIngredients() {
        int recipeId = view.getRecipeId();

        recipeRepository.getIngredients(recipeId, new RecipeRepository.GetIngredientsCallback() {
            @Override
            public void onIngredientsLoaded(List<Ingredient> ingredients) {
                view.showIngredients(ingredients);
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {
                view.showErrorMessage(failureMessage);
            }
        });
    }

    @Override
    public void setView(IngredientsFragmentView view) {
        this.view = view;
        loadIngredients();
    }
}
