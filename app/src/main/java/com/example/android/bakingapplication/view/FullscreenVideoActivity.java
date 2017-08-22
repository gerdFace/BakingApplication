package com.example.android.bakingapplication.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapplication.R;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;

public class FullscreenVideoActivity extends AppCompatActivity {

    private String videoUrl;

    @BindView(R.id.player_view)
    SimpleExoPlayerView exoPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video);

        videoUrl = getIntent().getStringExtra("video_url");
    }

    private boolean destroyVideo = true;

    @Override
    protected void onResume(){
        super.onResume();
        ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(this,
                        videoUrl, exoPlayerView);
        ExoPlayerVideoHandler.getInstance().goToForeground();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT && videoUrl != null) {
            destroyVideo = false;
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        destroyVideo = false;
        super.onBackPressed();
    }

    @Override
    protected void onPause(){
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(destroyVideo){
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
    }
}
