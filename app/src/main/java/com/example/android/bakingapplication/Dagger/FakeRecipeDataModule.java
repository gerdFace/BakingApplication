package com.example.android.bakingapplication.Dagger;

import com.example.android.bakingapplication.model.FakeRecipeData;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class FakeRecipeDataModule {
    @Provides
    @Singleton
    FakeRecipeData provideFakeRecipeData() {
        return new FakeRecipeData();
    }
}
