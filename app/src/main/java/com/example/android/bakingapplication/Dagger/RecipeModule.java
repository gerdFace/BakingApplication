package com.example.android.bakingapplication.Dagger;

import com.example.android.bakingapplication.model.RecipeDatum;
import com.example.android.bakingapplication.retrofit.NetworkCall;
import java.util.List;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class RecipeModule {

//    @Provides
//    @Singleton
//    RecipeDatum provideSelectedRecipe(String nameOfFoodItemSelected) {
//
//    }

    @Provides
    @Singleton
    List<RecipeDatum> provideRecipeList() {
        return new NetworkCall().getRecipeList();
    }
}
