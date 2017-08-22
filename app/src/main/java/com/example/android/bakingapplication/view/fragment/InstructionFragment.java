package com.example.android.bakingapplication.view.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.Step;
import com.example.android.bakingapplication.view.ExoPlayerVideoHandler;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {

    private static final String ARG_SELECTED_STEP = "selected_step";
    private static final String TAG = InstructionFragment.class.getSimpleName();

    private Step step;
    private String videoUrl;

    @BindView(R.id.short_step_description)
    TextView shortDescription;

    @BindView(R.id.long_step_description)
    TextView longDescription;

    @BindView(R.id.player_view)
    SimpleExoPlayerView simpleExoPlayerView;

    public InstructionFragment() {
        // Required empty public constructor
    }

    public static InstructionFragment newInstance(Step selectedStep) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_SELECTED_STEP, selectedStep);

        InstructionFragment instructionFragment = new InstructionFragment();
        instructionFragment.setArguments(args);
        return instructionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        step = getArguments().getParcelable(ARG_SELECTED_STEP);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);

        ButterKnife.bind(this, view);

        shortDescription.setText(step.getShortDescription());
        longDescription.setText(step.getDescription());

        if (!step.getVideoURL().isEmpty()) {
            videoUrl = step.getVideoURL();
        } else if (step.getVideoURL().isEmpty() && !step.getThumbnailURL().isEmpty()) {
            videoUrl = step.getThumbnailURL();
        } else {
            videoUrl = null;
        }

//        updateUI();

        return view;
    }
//
//    private void updateUI() {
//
//        boolean videoIsAvailable = !step.getVideoURL().isEmpty() || !step.getThumbnailURL().isEmpty();
//        int orientation = getActivity().getResources().getConfiguration().orientation;
//
//        if (videoIsAvailable && orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            showVideoFullscreenView();
//        } else if (videoIsAvailable && orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setDescriptionText();
//        } else {
//            showNoVideoView();
//        }
//    }
//
//    private void showVideoFullscreenView() {
//        //Remove title bar
//        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        //Remove notification bar
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        shortDescription.setVisibility(View.INVISIBLE);
//        longDescription.setVisibility(View.INVISIBLE);
//    }

//    private void showNoVideoView() {
//        setDescriptionText();
//        simpleExoPlayerView.setVisibility(View.INVISIBLE);
//    }
//
//    private void setDescriptionText() {
//
//    }

//    private void releasePlayer() {
//        if (player != null) {
//            player.setPlayWhenReady(false);
//            player.stop();
//            player.release();
//            player = null;
//        }
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        if (player != null) {
//            releasePlayer();
//            Log.d(TAG, "onDestroy: player destroyed");
//        }
//    }
//
        @Override
        public void onResume() {
            super.onResume();
            if(videoUrl != null && simpleExoPlayerView != null){
                ExoPlayerVideoHandler.getInstance()
                        .prepareExoPlayerForUri(getActivity().getApplicationContext(), videoUrl, simpleExoPlayerView);
                ExoPlayerVideoHandler.getInstance().goToForeground();
            }
            Log.d(TAG, "onResume: " + videoUrl + simpleExoPlayerView);
        }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && videoUrl != null) {
            shortDescription.setVisibility(View.GONE);
            longDescription.setVisibility(View.GONE);
            //        //Remove title bar
        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
        public void onPause(){
            super.onPause();
            ExoPlayerVideoHandler.getInstance().goToBackground();
        }

        @Override
        public void onDestroyView(){
            super.onDestroyView();
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
    }
