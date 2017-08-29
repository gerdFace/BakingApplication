package com.example.android.bakingapplication.dagger.component;

import com.example.android.bakingapplication.BakingApplicationWidget;
import com.example.android.bakingapplication.dagger.module.DetailListFragmentPresenterModule;
import com.example.android.bakingapplication.dagger.module.DetailPagerActivityPresenterModule;
import com.example.android.bakingapplication.dagger.module.IngredientsFragmentPresenterModule;
import com.example.android.bakingapplication.dagger.module.MainActivityPresenterModule;
import com.example.android.bakingapplication.dagger.module.RealmModule;
import com.example.android.bakingapplication.dagger.module.StepFragmentPresenterModule;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.activity.DetailPagerActivity;
import com.example.android.bakingapplication.view.activity.MainActivity;
import com.example.android.bakingapplication.view.fragment.DetailListFragment;
import com.example.android.bakingapplication.view.fragment.IngredientsFragment;
import com.example.android.bakingapplication.view.fragment.StepFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainActivityPresenterModule.class, DetailPagerActivityPresenterModule.class, IngredientsFragmentPresenterModule.class,
        DetailListFragmentPresenterModule.class, RealmModule.class, StepFragmentPresenterModule.class})
public interface ApplicationComponent {

    void inject(BakingApplicationWidget target);

    void inject(MainActivity target);

    void inject(DetailListFragment target);

    void inject(StepFragment target);

    void inject(DetailPagerActivity target);

    void inject(IngredientsFragment target);

    RecipeRepository getRecipeRepository();

}
