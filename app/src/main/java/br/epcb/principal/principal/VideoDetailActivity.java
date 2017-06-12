package br.epcb.principal.principal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import br.epcb.entity.entity.EPCBCourse;


public class VideoDetailActivity extends BaseActivity {

    private static final String LOG_TAG = ModuleActivityFragment.class.getSimpleName();

    private VideoView videoViewEPCB;
    private Button btCloseVideo;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;

    private EPCBCourse mEPCBCourse;

    private String mVideoURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video_detail);

        Intent intent = getIntent();
        mEPCBCourse = (EPCBCourse) intent.getSerializableExtra(VIDEO_TRANSFER);
        mVideoURL = mEPCBCourse.getURLOtherVideo();

        btCloseVideo = (Button) findViewById(R.id.btCloseVideo);
        if (btCloseVideo != null) {
            btCloseVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        if (mediaControls == null) {
            mediaControls = new MediaController(VideoDetailActivity.this);
        }

        videoViewEPCB = (VideoView) findViewById(R.id.videoViewEPCB);

        progressDialog = new ProgressDialog(VideoDetailActivity.this);
        progressDialog.setTitle("");
        progressDialog.setMessage("Carregando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        try {

            videoViewEPCB.setMediaController(mediaControls);

            Uri uriVideo = Uri.parse(mVideoURL);
            videoViewEPCB.setVideoURI(uriVideo);

            //Log.v(LOG_TAG, "URL Video: " + mVideoURL);

        } catch (Exception e) {
            //Log.e("Error", e.getMessage());
            //e.printStackTrace();
        }

        videoViewEPCB.requestFocus();
        videoViewEPCB.setZOrderOnTop(true);

        videoViewEPCB.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                //Log.v(LOG_TAG, "onPrepared() ");
                // close the progress bar and play the video
                    progressDialog.dismiss();
                    //if we have a position on savedInstanceState, the video playback should start from here
                    videoViewEPCB.seekTo(position);
                    if (position == 0) {
                        //mediaPlayer.setLooping(true);
                        videoViewEPCB.start();

                    } else {
                        //if we come from a resumed activity, video playback will be paused
                        //videoViewEPCB.pause();
                    }

            }


        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //Log.v(LOG_TAG, "onSaveInstanceState() ");
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", videoViewEPCB.getCurrentPosition());
        progressDialog.dismiss();

        //videoViewEPCB.pause();
        //videoViewEPCB.seekTo(videoViewEPCB.getCurrentPosition());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Log.v(LOG_TAG, "onRestoreInstanceState() ");
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        videoViewEPCB.seekTo(position);
    }


}
