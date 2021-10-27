package com.example.video_viewer;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;
import com.example.viewer.DocumentData;
import com.example.viewer.DocumentLoadCallback;
import com.example.viewer.DocumentViewer;

public class VideoViewer implements DocumentViewer {

    private VideoView video;

    @Override
    public View view(ViewGroup container) {
        if (video == null) {
            video = new VideoView(container.getContext());
            ViewGroup.LayoutParams params = container.generateLayoutParams(null);
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            video.setLayoutParams(params);
        }
        return video;
    }

    @Override
    public void load(Context context, DocumentData document, DocumentLoadCallback callback) {
        video.setVideoURI(document.uri);
        video.setOnErrorListener((mp, what, extra) -> {
            callback.onFailure(new RuntimeException());
            return true;
        });
        video.start();
        callback.onStart();
        video.setOnPreparedListener(mp -> callback.onComplete());
        video.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
    }

    @Override
    public void onViewAttached() {
        if (video != null) {
            if (!video.isPlaying()) {
                video.resume();
            }
        }
    }

    @Override
    public void onViewDetached() {
        if (video != null) {
            video.pause();
        }
    }

    @Override
    public void onViewDestroyed() {
        video = null;
    }
}
