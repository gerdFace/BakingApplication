package com.example.android.bakingapplication.Dagger;

import com.example.android.bakingapplication.repository.Local;
import com.example.android.bakingapplication.repository.Network;
import com.example.android.bakingapplication.repository.RecipeDataSource;
import com.example.android.bakingapplication.repository.local.RecipeDatabaseSource;
import com.example.android.bakingapplication.repository.remote.RecipeNetworkSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class RecipeRepositoryModule {

    @Singleton
    @Provides
    @Local
    RecipeDataSource provideRecipeDatabaseSource() {
        return new RecipeDatabaseSource();
    }

    @Singleton
    @Provides
    @Network
    RecipeDataSource provideRecipeNetworkSource() {
        return new RecipeNetworkSource();
    }
}
