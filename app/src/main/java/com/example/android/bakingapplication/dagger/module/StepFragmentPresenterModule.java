package com.example.android.bakingapplication.dagger.module;

import android.app.Application;

import com.example.android.bakingapplication.presentation.StepFragmentPresenterImpl;
import com.example.android.bakingapplication.repository.RecipeRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModule.class, RecipeRepositoryModule.class})
public class StepFragmentPresenterModule {

    @Provides
    StepFragmentPresenterImpl provideStepFragmentPresenter(Application application, RecipeRepository recipeRepository) {
        return new StepFragmentPresenterImpl(application, recipeRepository);
    }
}