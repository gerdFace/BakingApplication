package com.example.android.bakingapplication.view.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    boolean isPlaying;
    long playerPosition;
    private int recipeId;
    private int stepIndex;

    @Inject
    StepFragmentPresenter stepFragmentPresenter;

    @BindView(R.id.short_step_description)
    TextView shortDescriptionView;

    @BindView(R.id.long_step_description)
    TextView longDescriptionView;

    @BindView(R.id.player_view)
    SimpleExoPlayerView simpleExoPlayerView;

    public StepFragment() {
        // Required empty public constructor
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
        stepIndex = getArguments().getInt(ARG_STEP_INDEX);
        recipeId = getArguments().getInt(ARG_RECIPE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);

        ((BakingApplication)getActivity().getApplication()).getApplicationComponent().inject(this);

        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            isPlaying = savedInstanceState.getBoolean("is_playing");
            playerPosition = savedInstanceState.getLong("player_position");
        } else {
            isPlaying = false;
            playerPosition = 0;
        }

//        stepFragmentPresenter.loadStep();

//        stepFragmentPresenter.initializeVideoPlayer();

        return view;
    }

//    private void showVideoFullscreenView() {
//        simpleExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                | View.SYSTEM_UI_FLAG_IMMERSIVE);

//    private void restoreVideoState() {
//        if (playerPosition > 0) {
//            player.seekTo(playerPosition);
//            player.setPlayWhenReady(isPlaying);
//        }
//    }

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
        stepFragmentPresenter.setVideoPlayerPosition(playerPosition);
        stepFragmentPresenter.setVideoIsPlaying(isPlaying);
        Log.d(TAG, "onResume: StepFragment resumed");
//
//        if (player == null) {
//            updateUI();
//        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (!isVisibleToUser) {
                stepFragmentPresenter.releaseVideoPlayer();

            } else {
                stepFragmentPresenter.initializeVideoPlayer();
            }
//
//            if (isVisibleToUser && player == null) {
//                updateUI();
//            }
        }
    }

    private void setDescriptionText(String shortStepDescription, String longStepDescription) {
        shortDescriptionView.setText(shortStepDescription);
        longDescriptionView.setText(longStepDescription);
    }

    @Override
    public void showVideoView(SimpleExoPlayer player, String shortStepDescription, String longStepDescription) {
        simpleExoPlayerView.setPlayer(player);
        setDescriptionText(shortStepDescription, longStepDescription);
    }

    @Override
    public void showNoVideoView(String shortStepDescription, String longStepDescription) {
        simpleExoPlayerView.setVisibility(View.GONE);
        setDescriptionText(shortStepDescription, longStepDescription);
    }

    @Override
    public void showFullScreenVideoView(SimpleExoPlayer player) {
        simpleExoPlayerView.setPlayer(player);
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
        outState.putBoolean("is_playing", isPlaying);
        outState.putLong("player_position", playerPosition);

    }
}
