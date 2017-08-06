package com.example.android.bakingapplication.dagger.module;

import com.example.android.bakingapplication.presentation.DetailPagerActivityPresenter;
import com.example.android.bakingapplication.presentation.DetailPagerActivityPresenterImpl;
import com.example.android.bakingapplication.repository.RecipeRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = RecipeRepositoryModule.class)
public class DetailPagerActivityPresenterModule {

    @Provides
    DetailPagerActivityPresenter provideDetailPagerActivityPresenter(RecipeRepository recipeRepository) {
        return new DetailPagerActivityPresenterImpl(recipeRepository);
    }
}
