package me.msile.train.player.simplevideoplayer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;

import me.msile.train.player.simplevideoplayer.R;
import me.msile.train.player.simplevideoplayer.view.SimpleVideoView;
import me.msile.train.player.simplevideoplayer.view.SimplerPlayerControllerLayout;

/**
 * 简单视频播放
 */

public class SimplePlayerActivity extends Activity {

    private SimpleVideoView videoView;
    private SimplerPlayerControllerLayout controllerLayout;
    private TextView titleTv;

    private String videoUrl;
    private String videoTitle;
    private boolean isBackClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.player_activity_simple_player_lay);
        getDataFromIntent();
        videoView = findViewById(R.id.ijk_player);
        controllerLayout = findViewById(R.id.ijk_player_controller);
        titleTv = findViewById(R.id.title_tv);
        videoView.setMediaController(controllerLayout.getMediaController());

        init();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        videoUrl = intent.getStringExtra("videoUrl");
        videoTitle = intent.getStringExtra("videoTitle");
        //test code
        videoUrl = "http://maoyan.meituan.net/movie/videos/1154x480e4015046f68845d58965df1dd0ec9f7b.mp4";
        videoTitle = "复仇者联盟3：无限战争";
        //test code
    }

    private void init() {
        if (!TextUtils.isEmpty(videoTitle)) {
            titleTv.setText(videoTitle);
        }
        videoView.setVideoPath(videoUrl);
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isBackClicked) {
            controllerLayout.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBackClicked) {
            videoView.stopPlayback();
        }
    }

    @Override
    public void onBackPressed() {
        onClickBackBtnEvent();
    }

    public void onClickBackBtnEvent() {
        if (controllerLayout.isFullScreen()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        isBackClicked = true;
        finish();
    }

    public static void goToPage(Context context, String videoTitle, String videoUrl) {
        if (TextUtils.isEmpty(videoUrl)) {
            return;
        }
        Intent intent = new Intent(context, SimplePlayerActivity.class);
        intent.putExtra("videoUrl", videoUrl);
        intent.putExtra("videoTitle", videoTitle);
        context.startActivity(intent);
    }

}
