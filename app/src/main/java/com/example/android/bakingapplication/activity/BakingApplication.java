package com.example.android.bakingapplication.activity;

import android.app.Application;
import com.example.android.bakingapplication.Dagger.AppComponent;
import com.example.android.bakingapplication.Dagger.AppModule;
import com.example.android.bakingapplication.Dagger.DaggerAppComponent;
import com.example.android.bakingapplication.Dagger.NetworkModule;
import com.example.android.bakingapplication.Dagger.RecipeModule;

public class BakingApplication extends Application {

    private static final String ROOT_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(BakingApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .networkModule(new NetworkModule(ROOT_URL))
                .recipeModule(new RecipeModule())
                .build();
    }
}
