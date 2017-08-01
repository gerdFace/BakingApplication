package com.example.android.bakingapplication.dagger;

import com.example.android.bakingapplication.repository.Local;
import com.example.android.bakingapplication.repository.Network;
import com.example.android.bakingapplication.repository.RecipeDataSource;
import com.example.android.bakingapplication.repository.local.RecipeDatabaseSource;
import com.example.android.bakingapplication.repository.remote.RecipeNetworkSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RecipeRepositoryModule {

    @Singleton
    @Binds
    @Local
    public abstract RecipeDataSource provideRecipeDatabaseSource(RecipeDatabaseSource databaseSource);

    @Singleton
    @Binds
    @Network
    public abstract RecipeDataSource provideRecipeNetworkSource(RecipeNetworkSource networkSource);
}
