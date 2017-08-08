package com.example.android.bakingapplication.view.activity;

import android.app.Application;

import com.example.android.bakingapplication.dagger.component.ApplicationComponent;
import com.example.android.bakingapplication.dagger.component.DaggerApplicationComponent;
import com.example.android.bakingapplication.dagger.module.AppModule;

public class BakingApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = initDagger();
    }

    protected ApplicationComponent initDagger() {
        return DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
