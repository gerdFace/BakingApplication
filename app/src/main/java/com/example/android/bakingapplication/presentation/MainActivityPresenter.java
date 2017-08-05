package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.view.activity.MainActivityView;

public interface MainActivityPresenter {

    void loadRecipes();

    void setView(MainActivityView view);
}
