package com.example.android.bakingapplication.activity;

import android.app.Application;

import com.example.android.bakingapplication.Dagger.AppComponent;
import com.example.android.bakingapplication.Dagger.AppModule;
import com.example.android.bakingapplication.Dagger.DaggerAppComponent;

public class BakingApplication extends Application {
    
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
        return DaggerAppComponent.builder().appModule(new AppModule(application)).build();
    }
}
