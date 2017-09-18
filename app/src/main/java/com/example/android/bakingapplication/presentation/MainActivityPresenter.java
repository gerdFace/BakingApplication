package com.example.android.bakingapplication.presentation;

import android.content.Context;

import com.example.android.bakingapplication.view.activity.MainActivityView;

public interface MainActivityPresenter {

    void loadRecipes();

    boolean isDeviceOnline(Context context);

    void setView(MainActivityView view);
}
