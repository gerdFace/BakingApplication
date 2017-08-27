package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.view.fragment.StepFragmentView;

public interface StepFragmentPresenter {

    void initializeVideoPlayer();

    void setVideoPlayerState();

    void releaseVideoPlayer();

    void loadStep();

    void setView(StepFragmentView view);
}
