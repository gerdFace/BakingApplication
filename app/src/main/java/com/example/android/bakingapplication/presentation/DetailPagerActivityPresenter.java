package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.view.activity.DetailPagerActivityView;

public interface DetailPagerActivityPresenter {

    void loadSteps();

    void setView(DetailPagerActivityView view);
}
