package com.example.sdk;

import android.content.Context;
import android.view.ViewGroup;
import com.example.viewer.DocumentData;
import com.example.viewer.DocumentViewer;

import java.io.File;
import java.util.List;

public class Viewers {

    public static final String VIDEO_VIEWER_CLASS_NAME = "com.example.video_viewer";
    public static final String IMAGE_VIEWER_CLASS_NAME = "com.example.image_viewer";

    private final List<File> files;
    volatile static private boolean dexLoaded = false;

    private Viewers(List<File> files) {
        this.files = files;
    }

    synchronized DocumentViewer video(ViewGroup container, DocumentData data)
            throws Exception {
        try {
            loadDex(container.getContext());
            Class<?> documentViewer = container.getContext().getClassLoader()
                    .loadClass(VIDEO_VIEWER_CLASS_NAME);
            return (DocumentViewer) documentViewer.getConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw e;
        }
    }

    synchronized DocumentViewer image(ViewGroup container, DocumentData data) throws Exception {
        try {
            loadDex(container.getContext());
            Class<?> documentViewer = container.getContext().getClassLoader()
                    .loadClass(IMAGE_VIEWER_CLASS_NAME);
            return (DocumentViewer) documentViewer.getConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw e;
        }
    }

    private void loadDex(Context context) throws Exception {
        if (!dexLoaded) {
            DexUtils.loadDex(context, files);
            dexLoaded = true;
        }
    }

}
