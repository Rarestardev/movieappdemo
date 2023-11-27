package com.rarestar.empirebesttv.video_player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.media.AudioManagerCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.audiofx.AudioEffect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.PlayBoxAdapter;
import com.rarestar.empirebesttv.adapter.Serial_link_Adapter;
import com.rarestar.empirebesttv.views.PlayMovieActivity;

import java.util.Locale;

public class VideoPlayer extends AppCompatActivity implements View.OnClickListener {
    String path;
    String videoTitle;
    PlayerView playerView;
    ExoPlayer player;
    TextView video_title,text_nightMode,text_mute,indicatorTextView,zoom_percentage,exo_position;
    ImageView videoBack,lock,unlock,scaling,ic_mute,ic_night,ic_vol,ic_playSpeed,ic_brightness,ic_EQ,
            indicatorImageView,video_more;
    VideoView video_view;
    ConcatenatingMediaSource concatenatingMediaSource;
    PlaybackParameters parameters;
    ScaleGestureDetector scaleGestureDetector;
    private enum ControlsMode {LOCK,FULLSCREEN}
    float speed;
    boolean dark = false;
    boolean mute = false;
    boolean singleTap = false;
    boolean doubleTap = false;
    View nightMode;
    RelativeLayout void_view_Id,indicatorView,zoom_layout,zoom_container,root,double_tap_play_pause;
    float startBrightness = -1.0f;
    float startVolumePercent = -1.0f;
    float scale_factor = 1.0f;
    int startVideoTime = -1;
    private static final int MAX_VIDEO_STEP_TIME = 60 * 1000;
    private static final int MAX_BRIGHTNESS = 100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        if (PlayMovieActivity.Trailer){
            path = PlayMovieActivity.trailerUrl;
            videoTitle = PlayMovieActivity.Name;
        }else{
            if (PlayMovieActivity.Category.equals("Movie") || PlayMovieActivity.Category.equals("Animation")){
                path = PlayBoxAdapter.LINK;
                videoTitle = PlayMovieActivity.Name;
            }else if (PlayMovieActivity.Category.equals("Serial")){
                path = Serial_link_Adapter.linkSerial;
                videoTitle = PlayMovieActivity.Name;
            }
        }
        screenOrientation();
        init();
        click();
        playVideo();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void init(){
        playerView = findViewById(R.id.exoplayer_view);
        video_title = findViewById(R.id.video_title);
        videoBack = findViewById(R.id.video_back);
        exo_position = findViewById(R.id.exo_position);
        lock = findViewById(R.id.lock);
        unlock = findViewById(R.id.unlock);
        scaling = findViewById(R.id.scaling);
        root = findViewById(R.id.root_layout);
        ic_night = findViewById(R.id.ic_night);
        ic_mute = findViewById(R.id.ic_mute);
        ic_vol = findViewById(R.id.ic_vol);
        ic_playSpeed = findViewById(R.id.ic_playSpeed);
        nightMode = findViewById(R.id.nightMode);
        text_nightMode = findViewById(R.id.text_nightMode);
        text_mute = findViewById(R.id.text_mute);
        ic_brightness = findViewById(R.id.ic_brightness);
        ic_EQ = findViewById(R.id.ic_EQ);
        indicatorImageView = findViewById(R.id.indicatorImageView);
        indicatorTextView = findViewById(R.id.indicatorTextView);
        video_view = findViewById(R.id.video_view);
        void_view_Id = findViewById(R.id.void_view_Id);
        indicatorView = findViewById(R.id.indicatorView);
        zoom_container = findViewById(R.id.zoom_container);
        zoom_layout = findViewById(R.id.zoom_layout);
        zoom_percentage = findViewById(R.id.zoom_percentage);
        double_tap_play_pause = findViewById(R.id.double_tap_play_pause);
        video_more = findViewById(R.id.video_more);

        scaleGestureDetector = new ScaleGestureDetector(this , new ScaleDetector());

        video_title.setText(videoTitle);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void click(){
        videoBack.setOnClickListener(this);
        lock.setOnClickListener(this);
        unlock.setOnClickListener(this);
        ic_night.setOnClickListener(this);
        ic_mute.setOnClickListener(this);
        ic_vol.setOnClickListener(this);
        ic_playSpeed.setOnClickListener(this);
        ic_brightness.setOnClickListener(this);
        video_more.setOnClickListener(this);
        ic_EQ.setOnClickListener(this);
        scaling.setOnClickListener(firstListener);

        void_view_Id.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return super.onTouch(v, event);
            }
            @Override
            public boolean onSwipeLeft() {
                return true;
            }
            @Override
            public boolean onSingleTap() {
                super.onSingleTap();
                if (singleTap){
                    playerView.showController();
                    singleTap = true;
                }else {
                    playerView.hideController();
                    singleTap = false;
                }
                return true;
            }
            @Override
            public boolean onDoubleTap() {
                super.onDoubleTap();
                if (doubleTap){
                    player.setPlayWhenReady(true);
                    double_tap_play_pause.setVisibility(View.GONE);
                    doubleTap = false;
                }else {
                    player.setPlayWhenReady(false);
                    double_tap_play_pause.setVisibility(View.VISIBLE);
                    doubleTap = true;
                }
                return true;
            }
            @Override
            public boolean onSwipeTop() {
                return true;
            }
            @Override
            public void onGestureDone() {
                startBrightness = -1.0f;
                startVolumePercent = -1.0f;
                startVideoTime = -1;
                hideIndicator();
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void adjustBrightness(double adjustPercent) {
                if (adjustPercent < -1.0f){
                    adjustPercent = -1.0f;
                }else if (adjustPercent > 1.0f){
                    adjustPercent = 1.0f;
                }
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                if (startBrightness < 0){
                    startBrightness = lp.screenBrightness;
                }
                float targetBrightness = (float) (startBrightness + adjustPercent * 1.0f);
                if (targetBrightness <= 0.0f){
                    targetBrightness = 0.0f;
                } else if (targetBrightness >= 1.0f) {
                    targetBrightness = 1.0f;
                }
                lp.screenBrightness = targetBrightness;
                getWindow().setAttributes(lp);
                indicatorImageView.setImageResource(R.drawable.baseline_brightness_high_24);
                indicatorTextView.setText((int)(targetBrightness * MAX_BRIGHTNESS) + "%");
                showIndicator();
            }
            @Override
            public void adjustVolumeLevel(double adjustPercent) {
                if (adjustPercent < -1.0f){
                    adjustPercent = -1.0f;
                }else if (adjustPercent > 1.0f){
                    adjustPercent = 1.0f;
                }
                AudioManager audioManager = ContextCompat.getSystemService(VideoPlayer.this,AudioManager.class);
                final  int STREAM = AudioManager.STREAM_MUSIC;
                int maxVolume = AudioManagerCompat.getStreamMaxVolume(audioManager,STREAM);
                if (maxVolume == 0)return;
                if (startVolumePercent < 0){
                    int curVolume = audioManager.getStreamVolume(STREAM);
                    startVolumePercent = curVolume * 1.0f / maxVolume;
                }
                double targetPercent = startVolumePercent + adjustPercent;
                if (targetPercent > 1.0f){
                    targetPercent = 1.0f;
                }else if (targetPercent < 0){
                    targetPercent = 0;
                }
                int index = (int) (maxVolume * targetPercent);
                if (index > maxVolume){
                    index = maxVolume;
                } else if (index < 0) {
                    index = 0;
                }
                audioManager.setStreamVolume(STREAM , index ,0);
                indicatorImageView.setImageResource(R.drawable.baseline_volume_up_24);
                indicatorTextView.setText(index * 100 / maxVolume + "%");
                showIndicator();
            }
            @Override
            public void adjustVideoPosition(double adjustPercent, boolean forwardDirection) {
                if (adjustPercent < -1.0f){
                    adjustPercent = -1.0f;
                }else if (adjustPercent > 1.0f){
                    adjustPercent = 1.0f;
                }
                int totalTime = (int) player.getDuration();
                
                if (startVideoTime < 0 ) {
                    startVideoTime = (int) player.getContentPosition();
                }
                double positiveAdjustPercent = Math.max(adjustPercent , - adjustPercent);
                int targetTime = startVideoTime + (int) (MAX_VIDEO_STEP_TIME * adjustPercent * (positiveAdjustPercent / 0.1));

                if (targetTime > totalTime){
                    totalTime = totalTime;
                }
                if (targetTime < 0) {
                    targetTime = 0;
                }
                String targetTimeString = formatDuration(targetTime / 1000);

                if (forwardDirection){
                    indicatorImageView.setImageResource(R.drawable.baseline_fast_forward_24);
                }else {
                    indicatorImageView.setImageResource(R.drawable.baseline_fast_rewind_24);
                }
                indicatorTextView.setText(targetTimeString);
                showIndicator();
                player.seekTo(targetTime);
            }
            @Override
            public Rect viewRect() {
                return new Rect(video_view.getLeft(), video_view.getTop(),video_view.getRight(),video_view.getBottom());
            }
        });
    }
    private void hideIndicator() {
        indicatorView.setVisibility(View.GONE);
    }
    private void showIndicator() {
        indicatorView.setVisibility(View.VISIBLE);
    }

    @SuppressLint({"NonConstantResourceId", "SourceLockedOrientationActivity", "Range", "QueryPermissionsNeeded"})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.video_back:
                if (player != null){
                    player.release();
                }
                finish();
                break;
            case R.id.lock:
                ControlsMode controlsMode = ControlsMode.FULLSCREEN;
                root.setVisibility(View.VISIBLE);
                lock.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "صفحه باز شد", Toast.LENGTH_SHORT).show();
                break;
            case R.id.unlock:
                controlsMode = ControlsMode.LOCK;
                root.setVisibility(View.INVISIBLE);
                lock.setVisibility(View.VISIBLE);
                Toast.makeText(this, "صفحه قفل شد", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ic_night:
                if (dark){
                    nightMode.setVisibility(View.GONE);
                    text_nightMode.setText("حالت تاریک");
                    dark = false;
                }else {
                    nightMode.setVisibility(View.VISIBLE);
                    text_nightMode.setText("حالت روشن");
                    dark = true;
                }
                break;
            case R.id.ic_mute:
                if (mute){
                    player.setVolume(100);
                    text_mute.setText("بی صدا");
                    ic_mute.setImageResource(R.drawable.baseline_volume_mute_24);
                    mute = false;
                }else {
                    player.setVolume(0);
                    text_mute.setText("با صدا");
                    ic_mute.setImageResource(R.drawable.baseline_volume_down_24);
                    mute = true;
                }
                break;
            case R.id.ic_brightness:
                BrightnessDialog brightnessDialog = new BrightnessDialog();
                brightnessDialog.show(getSupportFragmentManager(),"dialog");
                break;
            case R.id.ic_EQ:
                Intent intent = new Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, 123);
                }else {
                    Toast.makeText(this, "اکولایزر پیدا نشد!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ic_vol:
                VolumeDialog volumeDialog = new VolumeDialog();
                volumeDialog.show(getSupportFragmentManager(),"dialog");
                break;
            case R.id.ic_playSpeed:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("انتخاب سرعت پخش")
                        .setPositiveButton( "باشه",null);
                String [] items = {"0.5x" , "1x" + " "+ "استاندارد" , "1.25x" ,"1.5x", "2x"};
                int checkedItem = -1;
                alertDialog.setSingleChoiceItems(items, checkedItem, (dialogInterface, i) -> {
                    switch (i){
                        case 0:
                            speed = 0.5f;
                            parameters = new PlaybackParameters(speed);
                            player.setPlaybackParameters(parameters);
                            break;
                        case 1:
                            speed = 1f;
                            parameters = new PlaybackParameters(speed);
                            player.setPlaybackParameters(parameters);
                            break;
                        case 2:
                            speed = 1.25f;
                            parameters = new PlaybackParameters(speed);
                            player.setPlaybackParameters(parameters);
                            break;
                        case 3:
                            speed = 1.5f;
                            parameters = new PlaybackParameters(speed);
                            player.setPlaybackParameters(parameters);
                            break;
                        case 4:
                            speed = 2f;
                            parameters = new PlaybackParameters(speed);
                            player.setPlaybackParameters(parameters);
                            break;
                        default:
                            break;
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
                break;
        }
    }
    private void playVideo(){
        Uri uri = Uri.parse(path);
        Log.i("Check",path.toString());
        player = new ExoPlayer.Builder(this).build();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this,Util.getUserAgent(this,"app"));
        concatenatingMediaSource = new ConcatenatingMediaSource();
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri));
        concatenatingMediaSource.addMediaSource(mediaSource);
        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);
        player.setPlaybackParameters(parameters);
        player.prepare(concatenatingMediaSource);
        player.seekTo(C.TIME_UNSET);
        playError();
    }
    @SuppressLint("SourceLockedOrientationActivity")
    private void screenOrientation(){
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            Bitmap bitmap;
            Uri uri = Uri.parse(path);
            retriever.setDataSource(this,uri);
            bitmap = retriever.getFrameAtTime();
            int videoWidth = bitmap.getWidth();
            int videoHeight = bitmap.getHeight();
            if (videoWidth > videoHeight){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }catch (Exception e){
            Log.e("MediaMetadataRetriever" ,"screenOrientation");
        }
    }
    private void playError() {
        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Toast.makeText(VideoPlayer.this, "فایل نا معتبر", Toast.LENGTH_SHORT).show();
            }
        });
        player.setPlayWhenReady(true);
    }
    View.OnClickListener firstListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            player.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
            scaling.setImageResource(R.drawable.fullscreen);

            Toast.makeText(VideoPlayer.this, "تمام صفحه", Toast.LENGTH_SHORT).show();
            scaling.setOnClickListener(secondListener);
        }
    };
    View.OnClickListener secondListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
            player.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
            scaling.setImageResource(R.drawable.baseline_zoom_out_map_24);

            Toast.makeText(VideoPlayer.this, "بزرگنمایی", Toast.LENGTH_SHORT).show();
            scaling.setOnClickListener(thirdListener);
        }
    };
    View.OnClickListener thirdListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            player.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
            scaling.setImageResource(R.drawable.fit);

            Toast.makeText(VideoPlayer.this, "معمولی", Toast.LENGTH_SHORT).show();
            scaling.setOnClickListener(firstListener);
        }
    };
    @Override
    public void onBackPressed() {
        player.stop();
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        player.setPlayWhenReady(false);
        player.getPlaybackState();
        super.onPause();
    }
    @Override
    protected void onRestart() {
        player.setPlayWhenReady(true);
        player.release();
        super.onRestart();
    }
    @Override
    protected void onResume() {
        player.setPlayWhenReady(true);
        player.seekToDefaultPosition();
        super.onResume();
    }
    private String formatDuration(int duration){
        int h = duration / 3600;
        int m = (duration - h * 3600) / 60;
        int s = duration - (h * 3600 + m * 60);
        String durationValue;
        if (h == 0){
            durationValue = String.format(Locale.getDefault() , "%1$02d:%2$02d" , m , s);
        }else{
            durationValue = String.format(Locale.getDefault() , "%1$d:%2$02d:%3$02d",h , m , s);
        }
        return durationValue;
    }
    private class ScaleDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean onScale(@NonNull ScaleGestureDetector detector) {
            scale_factor *= detector.getScaleFactor();
            scale_factor = Math.max(0.5f , Math.min(scale_factor,6.0f));
            zoom_layout.setScaleX(scale_factor);
            zoom_layout.setScaleY(scale_factor);
            int percentage = (int) (scale_factor * 100);
            zoom_percentage.setText("" + percentage + "%");
            zoom_container.setVisibility(View.VISIBLE);
            return super.onScale(detector);
        }
        @Override
        public void onScaleEnd(@NonNull ScaleGestureDetector detector) {
            zoom_container.setVisibility(View.GONE);
            super.onScaleEnd(detector);
        }
    }
}