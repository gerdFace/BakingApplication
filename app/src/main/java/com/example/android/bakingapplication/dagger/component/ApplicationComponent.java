package com.example.android.bakingapplication.dagger.component;

import com.example.android.bakingapplication.dagger.module.MainActivityPresenterModule;
import com.example.android.bakingapplication.dagger.module.RecipeRepositoryModule;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.activity.DetailListActivity;
import com.example.android.bakingapplication.view.activity.DetailPagerActivity;
import com.example.android.bakingapplication.view.activity.MainActivity;
import com.example.android.bakingapplication.view.fragment.DetailListFragment;
import com.example.android.bakingapplication.view.fragment.IngredientsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RecipeRepositoryModule.class, MainActivityPresenterModule.class})
public interface ApplicationComponent {

    void inject(MainActivity target);

    void inject(DetailPagerActivity target);

    void inject(DetailListActivity target);

    void inject(DetailListFragment target);

    void inject(IngredientsFragment target);

    RecipeRepository getRecipeRepository();
}
