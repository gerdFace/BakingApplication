package com.example.android.bakingapplication.view.fragment;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.presentation.StepFragmentPresenter;
import com.example.android.bakingapplication.view.activity.BakingApplication;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment implements StepFragmentView {

    private static final String ARG_STEP_INDEX = "step_index";
    private static final String ARG_RECIPE_ID = "recipe_id";

    private int recipeId;
    private int stepIndex;
    private long playerResumePosition;
    private ConstraintSet constraintSet = new ConstraintSet();

    @Inject
    StepFragmentPresenter stepFragmentPresenter;

    @BindView(R.id.short_step_description)
    TextView shortDescriptionView;

    @BindView(R.id.long_step_description)
    TextView longDescriptionView;

    @BindView(R.id.image_that_does_not_exist)
    ImageView imageView;

    @BindView(R.id.player_view)
    SimpleExoPlayerView simpleExoPlayerView;

    @BindView(R.id.fragment_step_constraint_container)
    ConstraintLayout constraintLayout;

    public StepFragment() {
    }

    public static StepFragment newInstance(int recipeId, int stepIndex) {
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, recipeId);
        args.putInt(ARG_STEP_INDEX, stepIndex);

        StepFragment instructionFragment = new StepFragment();
        instructionFragment.setArguments(args);
        return instructionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_with_video, container, false);

        getApplication().createStepFragmentComponent(getContext()).inject(this);

        ButterKnife.bind(this, view);

        constraintSet.clone(getActivity().getApplicationContext(), R.layout.fragment_step_no_video);

        if (savedInstanceState != null) {
            stepIndex = savedInstanceState.getInt("step_index");
            recipeId = savedInstanceState.getInt("recipe_id");
            playerResumePosition = savedInstanceState.getLong("player_resume_position");
        } else {
            stepIndex = getArguments().getInt(ARG_STEP_INDEX);
            recipeId = getArguments().getInt(ARG_RECIPE_ID);
            playerResumePosition = 0;
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        playerResumePosition = stepFragmentPresenter.getPlayerPosition();
        stepFragmentPresenter.releaseVideoPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        stepFragmentPresenter.setView(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (!isVisibleToUser) {
                stepFragmentPresenter.pauseVideoPlayer();
            } else {
                stepFragmentPresenter.updateUI();
            }
        }
    }

    @Override
    public void showVideoView(SimpleExoPlayer player, String shortStepDescription, String longStepDescription) {
        simpleExoPlayerView.setPlayer(player);
        simpleExoPlayerView.setVisibility(View.VISIBLE);
        setStepDescriptionViewsVisible();
        setDescriptionText(shortStepDescription, longStepDescription);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showNoVideoNoImageView(String shortStepDescription, String longStepDescription) {
        simpleExoPlayerView.setVisibility(View.GONE);
        setStepDescriptionViewsVisible();
        setDescriptionText(shortStepDescription, longStepDescription);
        TransitionManager.beginDelayedTransition(constraintLayout);
        constraintSet.setVisibility(R.id.player_view, View.GONE);
        constraintSet.applyTo(constraintLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showImageViewNoVideo(String thumbnailUrl, String shortStepDescription, String longStepDescription) {
        setStepDescriptionViewsVisible();
        shortDescriptionView.setText(shortStepDescription);
        longDescriptionView.setText(longStepDescription);
        simpleExoPlayerView.setVisibility(View.GONE);
        Glide.with(getContext()).load(thumbnailUrl).into(imageView);
    }

    @Override
    public void showFullScreenVideoView(SimpleExoPlayer player) {
        simpleExoPlayerView.setPlayer(player);
        simpleExoPlayerView.setVisibility(View.VISIBLE);
        shortDescriptionView.setVisibility(View.GONE);
        longDescriptionView.setVisibility(View.GONE);
    }

    @Override
    public int getRecipeId() {
        return recipeId;
    }

    @Override
    public boolean isLandscapeOrientation() {
        int orientation = getActivity().getResources().getConfiguration().orientation;

        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public boolean twoPane() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public int getStepIndex() {
        return stepIndex;
    }

    @Override
    public long getPlayerPosition() {
        return playerResumePosition;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("step_index", stepIndex);
        outState.putInt("recipe_id", recipeId);
        outState.putLong("player_resume_position", playerResumePosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        stepFragmentPresenter.updateUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getApplication().releaseStepFragmentComponent();
    }

    private void setDescriptionText(String shortStepDescription, String longStepDescription) {
        shortDescriptionView.setText(shortStepDescription);

        if (!shortStepDescription.equals(longStepDescription)) {
            longDescriptionView.setText(longStepDescription);
        }
    }

    private BakingApplication getApplication() {
        return (BakingApplication)getActivity().getApplication();
    }

    private void setStepDescriptionViewsVisible() {
        shortDescriptionView.setVisibility(View.VISIBLE);
        longDescriptionView.setVisibility(View.VISIBLE);
    }
}
