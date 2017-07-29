package com.example.android.bakingapplication.repository;

import com.example.android.bakingapplication.Dagger.NetworkModule;
import com.example.android.bakingapplication.Dagger.RealmModule;
import com.example.android.bakingapplication.repository.local.RecipeDatabaseSource;
import com.example.android.bakingapplication.repository.remote.RecipeNetworkSource;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RealmModule.class, NetworkModule.class, RecipeRepositoryModule.class})
public interface RecipeRepositoryComponent {

    void inject(RecipeDatabaseSource target);

    void inject(RecipeNetworkSource target);

    RecipeRepository getRecipeRepository();
}
