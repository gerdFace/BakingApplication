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
        videoIsAvailable = !currentStep.getVideoURL().isEmpty();

        if (videoIsAvailable && player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();

            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);

            long playerResumePosition = view.getPlayerPosition();

            if (playerResumePosition > 0) {
                player.seekTo(playerResumePosition);
            }
            player.prepare(prepareMediaSource());
        }

        updateUI();
    }

    @Override
    public void releaseVideoPlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
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
    public long getPlayerPosition() {
        if (player != null) {
            return player.getCurrentPosition();
        } else {
            return 0;
        }
    }

    @Override
    public void setView(StepFragmentView view) {
        this.view = view;
        loadStep();
        initializeVideoPlayer();
    }

    @Override
    public void updateUI() {
        String shortStepDescription = currentStep.getShortDescription();
        String longStepDescription = currentStep.getDescription();
        boolean thumbnailIsAvailable = !currentStep.getThumbnailURL().isEmpty();

        if (videoIsAvailable && view.isLandscapeOrientation() && !view.twoPane()) {
            view.showFullScreenVideoView(player);
        } else if (videoIsAvailable) {
            view.showVideoView(player, shortStepDescription, longStepDescription);
        } else if (thumbnailIsAvailable) {
            view.showImageViewNoVideo(currentStep.getThumbnailURL(), shortStepDescription, longStepDescription);
        } else {
            view.showNoVideoNoImageView(shortStepDescription, longStepDescription);
        }
    }

    @Override
    public void pauseVideoPlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
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
}
