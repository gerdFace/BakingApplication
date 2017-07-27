package com.example.android.bakingapplication;

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

import com.example.android.bakingapplication.activity.BakingApplication;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class InstructionFragment extends Fragment {

    @Inject
    Realm realm;

	private static final String TAG = InstructionFragment.class.getSimpleName();
	private static final String NAME_FOOD_ITEM_KEY = "name_food_item_key";
	private static final String SHORT_DESCRIPTION_OF_STEP_SELECTED = "short_description_of_step_selected";
	
    private Step step;
	private String nameOfFoodItem;
    private SimpleExoPlayer player;
    private Context applicationContext;
    private String stepSelected;

	@BindView(R.id.short_step_description)
	TextView shortDescription;
	
    @BindView(R.id.long_step_description)
    TextView longDescription;
	
	@BindView(R.id.player_view)
	SimpleExoPlayerView simpleExoPlayerView;

    public InstructionFragment() {
        // Required empty public constructor
    }
	
	public static InstructionFragment newInstance(String nameOfFoodItem, String shortDescriptionOfStepSelected) {
		Bundle args = new Bundle();
		args.putString(NAME_FOOD_ITEM_KEY, nameOfFoodItem);
		args.putString(SHORT_DESCRIPTION_OF_STEP_SELECTED, shortDescriptionOfStepSelected);

		InstructionFragment instructionFragment = new InstructionFragment();
		instructionFragment.setArguments(args);
		return instructionFragment;
	}
	
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		nameOfFoodItem = getArguments().getString(NAME_FOOD_ITEM_KEY);
        stepSelected = getArguments().getString(SHORT_DESCRIPTION_OF_STEP_SELECTED);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);

        ((BakingApplication)getActivity().getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this, view);

		updateUI();
		
        return view;
    }

	private void updateUI() {
        // TODO loading individual step (.7 Pour batter...) - need step list?
        step = realm.where(Step.class)
                .equalTo("shortDescription", stepSelected)
                .findFirst();

//        shortDescription.setText(stepDescriptionList.g);

        Log.d(TAG, "InstructionFragment shortDescription: " + step.getShortDescription());

        longDescription.setText(step.getDescription());

        initializeMediaPlayer();
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
	    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(applicationContext,
	                                                                        Util.getUserAgent(applicationContext, "BakingApplication"),
	                                                                        null);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        return new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
                                        dataSourceFactory, extractorsFactory, null, null);
    }

    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
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
		updateUI();
	}
}
