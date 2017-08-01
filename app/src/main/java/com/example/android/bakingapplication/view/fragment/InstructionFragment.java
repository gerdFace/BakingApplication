package com.example.android.bakingapplication.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.Step;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {

    private static final String ARG_SELECTED_STEP = "selected_step";
	private static final String TAG = InstructionFragment.class.getSimpleName();

    private Step step;
    private SimpleExoPlayer player;
    private Context applicationContext;

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

		updateUI();
		
        return view;
    }

	private void updateUI() {
        shortDescription.setText(step.getShortDescription());

        Log.d(TAG, "InstructionFragment shortDescription: " + step.getShortDescription());

        longDescription.setText(step.getDescription());

        if (!step.getVideoURL().isEmpty() || !step.getThumbnailURL().isEmpty()) {
            initializeMediaPlayer();
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }
    }

    private void initializeMediaPlayer() {
        applicationContext = this.getActivity();

        TrackSelector trackSelector = new DefaultTrackSelector();

        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(applicationContext, trackSelector, loadControl);

        simpleExoPlayerView.setPlayer(player);

        player.prepare(prepareMediaSource());
    }

    private MediaSource prepareMediaSource() {
        String videoUrl = !step.getVideoURL().isEmpty() ? step.getVideoURL() : step.getThumbnailURL();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(applicationContext,
	                                                                        Util.getUserAgent(applicationContext, "BakingApplication"),
	                                                                        null);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        Log.d(TAG, "prepareMediaSource: Media source = " + Uri.parse(videoUrl));

        return new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
                                        dataSourceFactory, extractorsFactory, null, null);
    }

    private void releasePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
            player = null;
        }
    }
    
//    TODO Media continues to play after navigating from page (2 pages left or right stops media?)
    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
	    Log.d(TAG, "onPause: player paused");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            releasePlayer();
            Log.d(TAG, "onDestroy: player destroyed");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player == null) {
            updateUI();
        }
    }
}
