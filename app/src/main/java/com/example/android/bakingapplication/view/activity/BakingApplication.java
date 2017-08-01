package com.example.android.bakingapplication.view.activity;

import android.app.Application;

import com.example.android.bakingapplication.dagger.modules.AppModule;
import com.example.android.bakingapplication.repository.ApplicationComponent;
import com.example.android.bakingapplication.repository.DaggerApplicationComponent;

public class BakingApplication extends Application {

    private ApplicationComponent recipeRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        recipeRepositoryComponent = initRecipeRepository();
    }

    protected ApplicationComponent initRecipeRepository() {
        return DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent getRecipeRepositoryComponent() {
        return recipeRepositoryComponent;
    }
}
