package com.example.android.bakingapplication.view.activity;

import android.app.Application;
import android.content.Context;

import com.example.android.bakingapplication.dagger.component.ApplicationComponent;
import com.example.android.bakingapplication.dagger.component.DaggerApplicationComponent;
import com.example.android.bakingapplication.dagger.component.StepFragmentComponent;
import com.example.android.bakingapplication.dagger.module.AppModule;
import com.example.android.bakingapplication.dagger.module.StepFragmentPresenterModule;

public class BakingApplication extends Application {

    private ApplicationComponent applicationComponent;
    private StepFragmentComponent stepFragmentComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = initDagger();
    }

    private ApplicationComponent initDagger() {
        return DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public StepFragmentComponent createStepFragmentComponent(Context stepFragmentContext) {
        stepFragmentComponent = applicationComponent.plus(new StepFragmentPresenterModule(stepFragmentContext));
        return stepFragmentComponent;
    }

    public void releaseStepFragmentComponent() {
        stepFragmentComponent = null;
    }
}
