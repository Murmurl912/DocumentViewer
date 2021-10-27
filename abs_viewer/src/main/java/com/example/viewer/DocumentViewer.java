package com.example.viewer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface DocumentViewer {

    View view(ViewGroup container);

    void load(Context context,
              DocumentData document,
              DocumentLoadCallback callback);

    void onViewAttached();

    void onViewDetached();

    void onViewDestroyed();

}
