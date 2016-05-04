package com.nethergrim.unsplashed.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

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


    public static Uri downloadImagesToCache(String downloadUrl, Context context, String name) {
        try {
            File directory = context.getCacheDir();

            File myDir = new File(directory, "images");

                /*  if specified not exist create new */
            if (!myDir.exists()) {
                myDir.mkdir();
            }

                /* checks the file and if it already exist delete */
            String fname = name + ".jpg";
            File file = new File(myDir, fname);
            if (file.exists()) {
                return Uri.fromFile(file);
            }

            return downloadImage(downloadUrl, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Uri downloadImage(String downloadUrl, File file) {
        try {
            URL url = new URL(downloadUrl);
            if (file.exists() && file.isFile()) {
                return Uri.fromFile(file);
            }


                /* Open a connection */
            URLConnection ucon = url.openConnection();
            InputStream inputStream;
            HttpURLConnection httpConn = (HttpURLConnection) ucon;
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            inputStream = httpConn.getInputStream();
                /*if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    inputStream = httpConn.getInputStream();
                }*/

            FileOutputStream fos = new FileOutputStream(file);
            int totalSize = httpConn.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
            }

            fos.close();
            Log.d("test", "Image Saved in sdcard..");
            return Uri.fromFile(file);
        } catch (IOException io) {
            io.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
