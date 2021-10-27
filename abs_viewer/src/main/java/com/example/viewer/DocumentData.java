package com.example.viewer;

import android.content.ContentResolver;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class DocumentData {

    public final Uri uri;

    public DocumentData(Uri uri) {
        this.uri = uri;
    }

    public InputStream input(ContentResolver resolver) throws FileNotFoundException {
        return resolver.openInputStream(uri);
    }

    public OutputStream output(ContentResolver resolver) throws FileNotFoundException {
        return resolver.openOutputStream(uri);
    }

}
