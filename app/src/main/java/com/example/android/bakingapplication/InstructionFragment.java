package com.example.android.bakingapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapplication.activity.BakingApplication;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment implements LoaderManager.LoaderCallbacks<MediaSource> {

    @Inject
    FakeRecipeData fakeRecipeData;

	private static final String TAG = InstructionFragment.class.getSimpleName();
	private static final String ARG_FOOD_ITEM_KEY = "food_item_key";
	
    private List<String> stepDescriptionList;
	private String nameOfFoodItem;
    private SimpleExoPlayer player;
    private Context applicationContext;
    private static final int INSTRUCTION_VIDEO_LOADER = 11;
    private MediaSource mediaSource;
	
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

        ((BakingApplication)getActivity().getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this, view);

		updateUI();
		
        return view;
    }
	
	private void updateUI() {
		stepDescriptionList = fakeRecipeData.getKRecipe(nameOfFoodItem).getStepDescriptionList();

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

        fetchMediaSource(stepDescriptionList.get(2));

//        player.prepare(mediaSource);
    }

    private void fetchMediaSource(String uri) {
        Bundle uriBundle = new Bundle();
        uriBundle.putString("media_uri", uri);

        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<MediaSource> mediaSourceLoader = loaderManager.getLoader(INSTRUCTION_VIDEO_LOADER);

        if (mediaSourceLoader == null) {
            loaderManager.initLoader(INSTRUCTION_VIDEO_LOADER, uriBundle, this);
        } else {
            loaderManager.restartLoader(INSTRUCTION_VIDEO_LOADER, uriBundle, this);
        }
    }

    private void releasePlayer() {
        if (player != null) {
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
        releasePlayer();
        Log.d(TAG, "onStop: player stopped");
    }

    @Override
	public void onResume() {
		super.onResume();
		updateUI();
	}

    @Override
    public Loader<MediaSource> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<MediaSource>(getActivity()) {

            MediaSource mediaSource;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) {
                    return;
                }

                if (mediaSource != null) {
                    deliverResult(mediaSource);
                } else {
                    forceLoad();
                }
            }

            @Override
            public MediaSource loadInBackground() {
                String uriFromBundle = args.getString("media_uri");
                if (uriFromBundle == null || TextUtils.isEmpty(uriFromBundle)) {
                    return null;
                }

                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(applicationContext,
                        Util.getUserAgent(applicationContext, "BakingApplication"),
                        null);

                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

                return new ExtractorMediaSource(Uri.parse(uriFromBundle),
                        dataSourceFactory, extractorsFactory, null, null);
            }

            @Override
            public void deliverResult(MediaSource data) {
                mediaSource = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<MediaSource> loader, MediaSource mediaSource) {
        player.prepare(mediaSource);
    }

    @Override
    public void onLoaderReset(Loader<MediaSource> loader) {
        Log.d(TAG, "onLoaderReset: loader restarting");
    }
}
