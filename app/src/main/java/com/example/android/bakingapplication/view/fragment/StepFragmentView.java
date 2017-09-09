package com.example.android.bakingapplication.view.fragment;

import com.google.android.exoplayer2.SimpleExoPlayer;

public interface StepFragmentView {

    void showVideoView(SimpleExoPlayer player, String shortStepDescription, String longStepDescription);

    void showNoVideoView(String shortStepDescription, String longStepDescription);

    void videoIsAvailable(boolean videoIsAvailable);

    void showFullScreenVideoView(SimpleExoPlayer player);

    int getRecipeId();

    boolean isLandscapeOrientation();

    boolean twoPane();

    int getStepIndex();
}
