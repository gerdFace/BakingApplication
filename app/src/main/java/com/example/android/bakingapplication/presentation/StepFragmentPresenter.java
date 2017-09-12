package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.view.fragment.StepFragmentView;

public interface StepFragmentPresenter {

    void initializeVideoPlayer();

    void updateUI();

    void pauseVideoPlayer();

    void releaseVideoPlayer();

    void loadStep();

    long getPlayerPosition();

    void setView(StepFragmentView view);
}
