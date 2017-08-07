package com.example.android.bakingapplication.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module(includes = AppModule.class)
public class RealmModule {

    @Provides
    @Singleton
    Realm provideRealmInstance(Application application) {
        Realm.init(application);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder()
                .name("recipeDB2.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded();

        RealmConfiguration configuration = builder.build();
        return Realm.getInstance(configuration);
    }
}
