package com.example.android.bakingapplication.Dagger;

import com.example.android.bakingapplication.DetailListFragment;
import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.activity.DetailListActivity;
import com.example.android.bakingapplication.activity.DetailPagerActivity;
import com.example.android.bakingapplication.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, FakeRecipeDataModule.class})
public interface AppComponent {
    void inject(MainActivity target);

    void inject(DetailListActivity target);

    void inject(DetailListFragment target);

    void inject(DetailPagerActivity target);

    void inject(IngredientsFragment target);

    void inject(InstructionFragment target);
}