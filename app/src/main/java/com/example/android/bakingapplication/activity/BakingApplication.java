package com.example.android.bakingapplication.activity;

import android.app.Application;

import com.example.android.bakingapplication.ApplicationComponent;

import dagger.android.DaggerActivity;
import dagger.android.DaggerApplication;
import dagger.android.DaggerApplication_MembersInjector;

public class BakingApplication extends Application {
    
    private ApplicationComponent component;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        component
    }
}
