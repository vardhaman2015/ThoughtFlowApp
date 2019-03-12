package pcube.servey.postquestion;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import pcube.servey.R;

public class VideoPlayActivity extends AppCompatActivity
{
    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        mVideoView = findViewById(R.id.videoview);
        Uri uri = Uri.parse("android.resource://pcube.servey/" + R.raw.videoquestion);
        mVideoView.setVideoURI(uri);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.start();
    }
}
