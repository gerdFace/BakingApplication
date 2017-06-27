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

import com.example.android.bakingapplication.model.FakeRecipeData;
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

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {
	
	private static final String TAG = InstructionFragment.class.getSimpleName();
	private static final String ARG_FOOD_ITEM_KEY = "food_item_key";
	
    private List<String> stepDescriptionList;
	private String nameOfFoodItem;
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
	
	public static InstructionFragment newInstance(String nameOfFoodItem) {
		Bundle args = new Bundle();
		args.putString(ARG_FOOD_ITEM_KEY, nameOfFoodItem);
		
		InstructionFragment instructionFragment = new InstructionFragment();
		instructionFragment.setArguments(args);
		return instructionFragment;
	}
	
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		nameOfFoodItem = getArguments().getString(ARG_FOOD_ITEM_KEY);
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
		stepDescriptionList = FakeRecipeData.get().getKRecipe(nameOfFoodItem).getStepDescriptionList();

        shortDescription.setText(stepDescriptionList.get(0));

        Log.d(TAG, "InstructionFragment shortDescription: " + stepDescriptionList.get(0));

        longDescription.setText(stepDescriptionList.get(1));

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

        return new ExtractorMediaSource(Uri.parse(stepDescriptionList.get(2)),
                                        dataSourceFactory, extractorsFactory, null, null);
    }

    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }
    
    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
	
	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}
}
