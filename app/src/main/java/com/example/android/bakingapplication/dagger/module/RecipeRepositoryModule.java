package com.example.android.bakingapplication.dagger.module;

import com.example.android.bakingapplication.dagger.qualifier.Local;
import com.example.android.bakingapplication.dagger.qualifier.Network;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.repository.RecipeRepositoryImpl;
import com.example.android.bakingapplication.repository.local.LocalDataSource;
import com.example.android.bakingapplication.repository.remote.NetworkDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;

@Module(includes = {RealmModule.class, NetworkModule.class})
public class RecipeRepositoryModule {

    @Singleton
    @Provides
    RecipeRepository provideRecipeRepository(@Local RecipeRepository databaseSource, @Network RecipeRepository networkSource) {
        return new RecipeRepositoryImpl(databaseSource, networkSource);
    }

    @Singleton
    @Provides
    @Local
    RecipeRepository provideRecipeDatabaseSource(Realm realm) {
        return new LocalDataSource(realm);
    }

    @Singleton
    @Provides
    @Network
    RecipeRepository provideRecipeNetworkSource(Retrofit retrofit) {
        return new NetworkDataSource(retrofit);
    }
}
