package com.example.viewer;

public interface DocumentLoadCallback {

    default void onStart() {}

    default void onProgress(float percent) {}

    default void onComplete() {}

    default void onFailure(Throwable throwable) {}

}
