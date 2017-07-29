package com.example.android.bakingapplication.Dagger;

import com.example.android.bakingapplication.repository.Local;
import com.example.android.bakingapplication.repository.Network;
import com.example.android.bakingapplication.repository.RecipeDataSource;
import com.example.android.bakingapplication.repository.local.RecipeDatabaseSource;
import com.example.android.bakingapplication.repository.remote.RecipeNetworkSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
abstract class RecipeRepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract RecipeDataSource provideRecipeDatabaseSource(RecipeDatabaseSource dataSource);

    @Singleton
    @Binds
    @Network
    abstract RecipeDataSource provideRecipeNetworkSource(RecipeNetworkSource dataSource);
}
