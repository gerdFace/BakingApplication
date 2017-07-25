package com.example.android.bakingapplication.Dagger;

import android.content.Context;
import android.util.Log;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class RealmModule {
    private static String TAG = RealmModule.class.getSimpleName();

    @Provides
//    @Singleton
    Realm provideRealmInstance(Context context) {
        Log.d(TAG, "provideRealmInstance: injecting Realm Instance");

        Realm.init(context);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
        builder.name("recipeDB.realm");

        RealmConfiguration configuration = builder.build();
        return Realm.getInstance(configuration);
    }

//    @Provides
//    RealmList<RecipeData> provideRecipeList() {
//
//    }

}
