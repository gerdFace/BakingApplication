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

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {

    private static final String TAG = InstructionFragment.class.getSimpleName();
	private static final String ARG_STEP_DESCRIPTION_LIST = "step_description";

    @BindView(R.id.short_step_description)
    TextView shortDescription;

    @BindView(R.id.long_step_description)
    TextView longDescription;
	
	@BindView(R.id.player_view)
	SimpleExoPlayerView simpleExoPlayerView;

    private List<String> stepDescriptionList;
    private SimpleExoPlayer player;
    private Context applicationContext;

    public InstructionFragment() {
        // Required empty public constructor
    }
	
	public static InstructionFragment newInstance(ArrayList<String> stepDescriptionList) {
		Bundle args = new Bundle();
		args.putStringArrayList(ARG_STEP_DESCRIPTION_LIST, stepDescriptionList);
		
		InstructionFragment instructionFragment = new InstructionFragment();
		instructionFragment.setArguments(args);
		return instructionFragment;
	}
	
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stepDescriptionList = getArguments().getStringArrayList(ARG_STEP_DESCRIPTION_LIST);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);
        ButterKnife.bind(this, view);

        shortDescription.setText(stepDescriptionList.get(0));

        Log.d(TAG, "InstructionFragment shortDescription: " + stepDescriptionList.get(0));

        longDescription.setText(stepDescriptionList.get(1));

        initializeMediaPlayer();

        return view;
    }

    public void setStepDescriptionList(List<String> stepDescriptionList) {
        this.stepDescriptionList = stepDescriptionList;
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
}
