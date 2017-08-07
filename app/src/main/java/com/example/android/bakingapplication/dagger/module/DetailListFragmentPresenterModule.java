package com.example.android.bakingapplication.dagger.module;

import com.example.android.bakingapplication.presentation.DetailListFragmentPresenter;
import com.example.android.bakingapplication.presentation.DetailListFragmentPresenterImpl;
import com.example.android.bakingapplication.repository.RecipeRepository;
import dagger.Module;
import dagger.Provides;

@Module(includes = RecipeRepositoryModule.class)
public class DetailListFragmentPresenterModule {

    @Provides
    DetailListFragmentPresenter provideDetailListFragmentPresenter(RecipeRepository recipeRepository) {
        return new DetailListFragmentPresenterImpl(recipeRepository);
    }
}
