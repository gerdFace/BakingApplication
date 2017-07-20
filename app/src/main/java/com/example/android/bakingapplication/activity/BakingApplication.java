package com.example.android.bakingapplication.activity;

import android.app.Application;
import android.app.usage.ConfigurationStats;
import android.content.Context;

import com.example.android.bakingapplication.Dagger.AppComponent;
import com.example.android.bakingapplication.Dagger.AppModule;
import com.example.android.bakingapplication.Dagger.DaggerAppComponent;
import com.example.android.bakingapplication.Dagger.NetworkModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public abstract class BakingApplication extends Application {

    private static final String ROOT_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private AppComponent appComponent;
//    private RefWatcher refWatcher;

//    public static RefWatcher getRefWatcher(Context context) {
//        BakingApplication bakingApplication = (BakingApplication) context.getApplicationContext();
//        return bakingApplication.refWatcher;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);

//        refWatcher = LeakCanary.install(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(BakingApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .networkModule(new NetworkModule(ROOT_URL))
                .build();
    }
}
