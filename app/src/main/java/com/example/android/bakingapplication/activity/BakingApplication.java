package com.example.android.bakingapplication.activity;

import android.app.Application;
import com.example.android.bakingapplication.Dagger.AppComponent;
import com.example.android.bakingapplication.Dagger.AppModule;
import com.example.android.bakingapplication.Dagger.DaggerAppComponent;
import com.example.android.bakingapplication.Dagger.NetworkModule;

public class BakingApplication extends Application {

    private static final String ROOT_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private AppComponent appComponent;
//    private NetworkComponent networkComponent;
//
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
//        networkComponent = initDaggerNetworkComponent();
    }

//    public NetworkComponent getNetworkComponent() {
//        return networkComponent;
//    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(BakingApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .networkModule(new NetworkModule(ROOT_URL))
                .build();
    }
//
//    protected NetworkComponent initDaggerNetworkComponent() {
//        return DaggerNetworkComponent.builder().networkModule(new NetworkModule(ROOT_URL)).build();
//    }
}
