package com.example.android.bakingapplication.presentation;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.bakingapplication.model.Step;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.fragment.StepFragmentView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepFragmentPresenterImpl implements StepFragmentPresenter {

    private Step currentStep;
    private SimpleExoPlayer player;
    private boolean videoIsAvailable;
    private boolean isPlaying;
    private boolean isVisible;
    private boolean isLanscapeOrientation;
    private long playerPosition;
    private RecipeRepository recipeRepository;
    private StepFragmentView view;
    private Context context;
    private String TAG = StepFragmentPresenterImpl.class.getSimpleName();

    public StepFragmentPresenterImpl(Context context, RecipeRepository recipeRepository) {
        this.context = context;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void initializeVideoPlayer() {

        if (videoIsAvailable) {
            TrackSelector trackSelector = new DefaultTrackSelector();

            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);

            prepareMediaSource();
    //        view.simpleExoPlayerView.setPlayer(player);


            updateUI(player);
        }
    }

    @Override
    public void setVideoPlayerState() {
        playerPosition = player.getCurrentPosition();
        player.setPlayWhenReady(isPlaying);
    }

    @Override
    public void releaseVideoPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void loadStep() {
        int recipeId = view.getRecipeId();
        int stepIndex = view.getStepIndex();

        recipeRepository.getStep(recipeId, stepIndex, new RecipeRepository.GetStepCallback() {
            @Override
            public void onStepLoaded(Step step) {
                currentStep = step;
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {
            }
        });
    }

    @Override
    public void setView(StepFragmentView view) {
        this.view = view;

    }

    private MediaSource prepareMediaSource() {
        String videoUrl = !currentStep.getVideoURL().isEmpty() ? currentStep.getVideoURL() : currentStep.getThumbnailURL();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "BakingApplication"),
                null);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        Log.d(TAG, "prepareMediaSource: Media source = " + Uri.parse(videoUrl));

        return new ExtractorMediaSource(Uri.parse(videoUrl),
                dataSourceFactory, extractorsFactory, null, null);
    }

    private void updateUI(SimpleExoPlayer player) {
        String shortStepDescription = currentStep.getShortDescription();
        String longStepDescription = currentStep.getDescription();

        view.showNoVideoView(shortStepDescription, longStepDescription);

        if (videoIsAvailable && isLanscapeOrientation) {
            view.showFullScreenVideoView(player);
        } else if (videoIsAvailable) {
            view.showVideoView(player, shortStepDescription, longStepDescription);
        }
    }
}
