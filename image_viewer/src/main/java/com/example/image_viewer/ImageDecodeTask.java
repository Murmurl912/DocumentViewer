package com.example.image_viewer;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import com.example.viewer.DocumentData;
import com.example.viewer.DocumentLoadCallback;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.Callable;

public class ImageDecodeTask implements Callable<Bitmap> {

    private final DocumentData data;
    private final ContentResolver resolver;
    private final DocumentLoadCallback callback;

    public ImageDecodeTask(DocumentData data,
                           Context context,
                           DocumentLoadCallback callback) {
        resolver = context.getContentResolver();
        this.data = data;
        this.callback = callback;
    }

    @Override
    public Bitmap call() throws IOException {
        callback.onStart();
        try (InputStream stream = data.input(resolver)) {
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            callback.onComplete();
            return bitmap;
        } catch (Exception e) {
            callback.onFailure(e);
            throw e;
        }
    }

}
