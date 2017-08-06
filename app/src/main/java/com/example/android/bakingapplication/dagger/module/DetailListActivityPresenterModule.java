package com.example.android.bakingapplication.dagger.module;

import com.example.android.bakingapplication.presentation.DetailListActivityPresenter;
import com.example.android.bakingapplication.presentation.DetailListActivityPresenterImpl;
import com.example.android.bakingapplication.repository.RecipeRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = RecipeRepositoryModule.class)
public class DetailListActivityPresenterModule {

    @Provides
    public DetailListActivityPresenter provideDetailListActivityPresenter(RecipeRepository recipeRepository) {
        return new DetailListActivityPresenterImpl(recipeRepository);
    }
}
