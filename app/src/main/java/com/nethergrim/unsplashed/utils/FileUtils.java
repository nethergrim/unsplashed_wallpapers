package com.nethergrim.unsplashed.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileUtils {

    public static final String GET = "GET";
    public static final String JPEG = ".jpg";
    public static final String IMAGES = "images";

    public static Uri downloadImagesToCache(String downloadUrl, Context context, String name) throws IOException {
        File directory = context.getCacheDir();
        File myDir = new File(directory, IMAGES);
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        String fname = name + JPEG;
        File file = new File(myDir, fname);
        if (file.exists()) {
            return Uri.fromFile(file);
        }
        return downloadImage(downloadUrl, file);
    }

    public static Uri downloadImage(String downloadUrl, File file) throws IOException {
        URL url = new URL(downloadUrl);
        if (file.exists() && file.isFile()) {
            return Uri.fromFile(file);
        }
        URLConnection connection = url.openConnection();
        InputStream inputStream;
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        httpConn.setRequestMethod(GET);
        httpConn.connect();
        inputStream = httpConn.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[2048];
        int bufferLength;
        while ((bufferLength = inputStream.read(buffer)) > 0) {
            fos.write(buffer, 0, bufferLength);
        }
        fos.close();
        return Uri.fromFile(file);
    }

}
