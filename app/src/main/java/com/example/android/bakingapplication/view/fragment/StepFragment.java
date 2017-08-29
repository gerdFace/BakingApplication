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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.presentation.StepFragmentPresenter;
import com.example.android.bakingapplication.view.activity.BakingApplication;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment implements StepFragmentView{

    private static final String ARG_STEP_INDEX = "step_index";
    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final String TAG = StepFragment.class.getSimpleName();

    private int recipeId;
    private int stepIndex;
    private ConstraintSet constraintSet = new ConstraintSet();

    @Inject
    StepFragmentPresenter stepFragmentPresenter;

    @BindView(R.id.short_step_description)
    TextView shortDescriptionView;

    @BindView(R.id.long_step_description)
    TextView longDescriptionView;

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

        if (savedInstanceState != null) {
            stepIndex = savedInstanceState.getInt("step_index");
            recipeId = savedInstanceState.getInt("recipe_id");
        } else {
            stepIndex = getArguments().getInt(ARG_STEP_INDEX);
            recipeId = getArguments().getInt(ARG_RECIPE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_before, container, false);

        ((BakingApplication)getActivity().getApplication()).getApplicationComponent().inject(this);

        ButterKnife.bind(this, view);

        constraintSet.clone(getActivity().getApplicationContext(), R.layout.fragment_step_after);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        stepFragmentPresenter.releaseVideoPlayer();
	    Log.d(TAG, "onPause: StepFragment paused");
    }

    @Override
    public void onResume() {
        super.onResume();
        stepFragmentPresenter.setView(this);
        Log.d(TAG, "onResume: StepFragment resumed");
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

    private void setDescriptionText(String shortStepDescription, String longStepDescription) {
        shortDescriptionView.setText(shortStepDescription);
        longDescriptionView.setText(longStepDescription);
    }

    @Override
    public void showVideoView(SimpleExoPlayer player, String shortStepDescription, String longStepDescription) {
        simpleExoPlayerView.setPlayer(player);
        simpleExoPlayerView.setVisibility(View.VISIBLE);
        shortDescriptionView.setVisibility(View.VISIBLE);
        longDescriptionView.setVisibility(View.VISIBLE);
        setDescriptionText(shortStepDescription, longStepDescription);
    }

    @Override
    public void showNoVideoView(String shortStepDescription, String longStepDescription) {
        simpleExoPlayerView.setVisibility(View.GONE);
        shortDescriptionView.setVisibility(View.VISIBLE);
        longDescriptionView.setVisibility(View.VISIBLE);
        setDescriptionText(shortStepDescription, longStepDescription);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void videoIsAvailable(boolean videoIsAvailable) {
        if (!videoIsAvailable) {
            TransitionManager.beginDelayedTransition(constraintLayout);
            constraintSet.setVisibility(R.id.player_view, View.GONE);
            constraintSet.applyTo(constraintLayout);
        }
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
    public int getStepIndex() {
        return stepIndex;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("step_index", stepIndex);
        outState.putInt("recipe_id", recipeId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        stepFragmentPresenter.updateUI();
    }
}
