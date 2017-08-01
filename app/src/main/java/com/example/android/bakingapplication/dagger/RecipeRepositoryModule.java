package com.example.android.bakingapplication.dagger;

import com.example.android.bakingapplication.repository.Local;
import com.example.android.bakingapplication.repository.Network;
import com.example.android.bakingapplication.repository.RecipeDataSource;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.repository.local.RecipeDatabaseSource;
import com.example.android.bakingapplication.repository.remote.RecipeNetworkSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;

@Module(includes = {RealmModule.class, NetworkModule.class})
public class RecipeRepositoryModule {

    @Singleton
    @Provides
    RecipeRepository provideRecipeRepository(@Local RecipeDataSource databaseSource, @Network RecipeDataSource networkSource) {
        return new RecipeRepository(databaseSource, networkSource);
    }

    @Singleton
    @Provides
    @Local
    RecipeDataSource provideRecipeDatabaseSource(Realm realm) {
        return new RecipeDatabaseSource(realm);
    }

    @Singleton
    @Provides
    @Network
    RecipeDataSource provideRecipeNetworkSource(Retrofit retrofit) {
        return new RecipeNetworkSource(retrofit);
    }
}
