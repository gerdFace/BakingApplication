package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.view.fragment.DetailListFragmentView;

public interface DetailListFragmentPresenter {

    void loadSteps();

    void setView(DetailListFragmentView view);
}
