package com.example.android.bakingapplication.repository;

import com.example.android.bakingapplication.Dagger.AppModule;
import com.example.android.bakingapplication.Dagger.NetworkModule;
import com.example.android.bakingapplication.Dagger.RealmModule;
import com.example.android.bakingapplication.Dagger.RecipeRepositoryModule;
import com.example.android.bakingapplication.DetailListFragment;
import com.example.android.bakingapplication.activity.DetailListActivity;
import com.example.android.bakingapplication.activity.DetailPagerActivity;
import com.example.android.bakingapplication.activity.MainActivity;
import com.example.android.bakingapplication.repository.local.RecipeDatabaseSource;
import com.example.android.bakingapplication.repository.remote.RecipeNetworkSource;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RealmModule.class, NetworkModule.class, RecipeRepositoryModule.class, AppModule.class})
public interface RecipeRepositoryComponent {

    void inject(RecipeDatabaseSource target);

    void inject(RecipeNetworkSource target);

    void inject(MainActivity target);

    void inject(DetailPagerActivity target);

    void inject(DetailListActivity target);

    void inject(DetailListFragment target);

    RecipeRepository getRecipeRepository();
}
