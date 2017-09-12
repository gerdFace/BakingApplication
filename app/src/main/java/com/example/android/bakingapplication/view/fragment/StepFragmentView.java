package com.example.android.bakingapplication.view.fragment;

import com.google.android.exoplayer2.SimpleExoPlayer;

public interface StepFragmentView {

    void showVideoView(SimpleExoPlayer player, String shortStepDescription, String longStepDescription);

    void showNoVideoNoImageView(String shortStepDescription, String longStepDescription);

    void showImageViewNoVideo(String shortStepDescription, String longStepDescription, String imageUrl);

    void showFullScreenVideoView(SimpleExoPlayer player);

    int getRecipeId();

    boolean isLandscapeOrientation();

    boolean twoPane();

    int getStepIndex();

    long getPlayerPosition();
}
