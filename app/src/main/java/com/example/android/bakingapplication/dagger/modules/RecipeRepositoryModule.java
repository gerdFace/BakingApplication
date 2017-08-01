package com.example.android.bakingapplication.dagger.modules;

import com.example.android.bakingapplication.dagger.qualifiers.Local;
import com.example.android.bakingapplication.dagger.qualifiers.Network;
import com.example.android.bakingapplication.repository.RecipeDataSource;
import com.example.android.bakingapplication.repository.local.RecipeDatabaseSource;
import com.example.android.bakingapplication.repository.remote.RecipeNetworkSource;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeRepositoryModule {

    @Singleton
    @Provides
    @Local
    RecipeDataSource provideRecipeDatabaseSource(RecipeDatabaseSource databaseSource) {
        return databaseSource;
    }


    @Singleton
    @Provides
    @Network
    public abstract RecipeDataSource provideRecipeNetworkSource(RecipeNetworkSource networkSource);
}
