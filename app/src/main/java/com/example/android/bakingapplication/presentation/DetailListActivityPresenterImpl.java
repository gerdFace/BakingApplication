package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.model.Step;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.activity.DetailListActivityView;

import java.util.List;

public class DetailListActivityPresenterImpl implements DetailListActivityPresenter {

    private DetailListActivityView view;
    private RecipeRepository recipeRepository;

    public DetailListActivityPresenterImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void loadSteps() {
        int recipeId = view.getRecipeId();

        recipeRepository.getSteps(recipeId, new RecipeRepository.GetStepsCallback() {
            @Override
            public void onStepsLoaded(List<Step> steps) {
                view.setSteps(steps);
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {
                view.showErrorMessage(failureMessage);
            }
        });

    }

    @Override
    public void setView(DetailListActivityView view) {
        this.view = view;
        loadSteps();
    }
}
