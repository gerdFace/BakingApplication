package com.example.android.bakingapplication.presentation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.activity.MainActivityView;

import java.util.List;

public class MainActivityPresenterImpl implements MainActivityPresenter{

    private MainActivityView view;
    private RecipeRepository recipeRepository;

    public MainActivityPresenterImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void loadRecipes() {
        recipeRepository.getRecipes(new RecipeRepository.LoadRecipesCallback() {
            @Override
            public void onRecipesLoaded(List<RecipeData> recipes) {
                view.showRecipes(recipes);
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {
                view.showErrorMessage(failureMessage);
            }
        });
    }

    @Override
    public boolean isDeviceOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void setView(MainActivityView view) {
        this.view = view;
        loadRecipes();
    }
}
