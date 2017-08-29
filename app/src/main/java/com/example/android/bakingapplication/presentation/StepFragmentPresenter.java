package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.view.fragment.StepFragmentView;

public interface StepFragmentPresenter {

    void initializeVideoPlayer();

    void setVideoPlayerPosition(long playerPosition);

    void setVideoIsPlaying(boolean isPlaying);

    long getVideoPlayerPosition();

    boolean getVideoIsPlaying();

    void releaseVideoPlayer();

    void loadStep();

    void setView(StepFragmentView view);
}
