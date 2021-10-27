package com.example.image_viewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.viewer.DocumentData;
import com.example.viewer.DocumentLoadCallback;
import com.example.viewer.DocumentViewer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageViewer implements DocumentViewer {

    private ImageView image;
    private DocumentData data;
    private Bitmap bitmap;
    private ImageDecodeTask task;
    private Future<Bitmap> future;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public View view(ViewGroup container) {
        if (image == null) {
            image = new ImageView(container.getContext());
            ViewGroup.LayoutParams params = container.generateLayoutParams(null);
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            image.setLayoutParams(params);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        return image;
    }

    @Override
    public void load(Context context,
                     DocumentData document,
                     DocumentLoadCallback callback) {
        if (future != null) {
            future.cancel(true);
            future = null;
        }
        task = new ImageDecodeTask(data, context, new DocumentLoadCallback() {
            @Override
            public void onComplete() {
                onImageLoaded();
                callback.onComplete();
            }
        });
        future = executor.submit(task);
    }

    @Override
    public void onViewAttached() {
        if (image != null && bitmap != null) {
            image.setImageBitmap(bitmap);
        }
    }

    private void onImageLoaded() {
        if (image != null && bitmap != null) {
            image.post(()-> {
                image.setImageBitmap(bitmap);
            });
        }
    }

    @Override
    public void onViewDetached() {

    }

    @Override
    public void onViewDestroyed() {
        if (future != null) {
            future.cancel(true);
        }
        if (bitmap != null) {
            bitmap.recycle();
        }
        task = null;
        bitmap = null;
        image = null;
        data = null;
    }

}
