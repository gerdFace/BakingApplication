package com.example.android.bakingapplication.dagger;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
//    void inject(RecipeDatabaseSource target);

//    void inject(RecipeNetworkSource target);

}
