package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.view.activity.DetailListActivityView;

public interface DetailListActivityPresenter {

    void loadStep();

    void setView(DetailListActivityView view);
}
