package com.example.android.bakingapplication.dagger.module;

import android.content.Context;

import com.example.android.bakingapplication.presentation.StepFragmentPresenter;
import com.example.android.bakingapplication.presentation.StepFragmentPresenterImpl;
import com.example.android.bakingapplication.repository.RecipeRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class StepFragmentPresenterModule {

    private Context stepFragmentContext;

    public StepFragmentPresenterModule(Context stepFragmentContext) {

        this.stepFragmentContext = stepFragmentContext;
    }

    @Provides
    StepFragmentPresenter provideStepFragmentPresenter(RecipeRepository recipeRepository) {
        return new StepFragmentPresenterImpl(stepFragmentContext, recipeRepository);
    }
}
