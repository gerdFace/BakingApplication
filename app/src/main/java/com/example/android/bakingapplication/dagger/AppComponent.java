package com.example.android.bakingapplication.dagger;

import com.example.android.bakingapplication.dagger.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
//    void inject(RecipeDatabaseSource target);

//    void inject(RecipeNetworkSource target);

}
