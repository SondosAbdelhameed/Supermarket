package com.supermarket.Helpers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


/**
 * Created by sam on 11/16/17.
 */

public class AllHelper {

    Activity activity;

    public AllHelper(Activity activity) {
        this.activity = activity;
    }

    public Boolean whatImage(ImageView imageView , int drawableID){
        Boolean dif;
        Drawable drawableD = activity.getResources().getDrawable(drawableID,activity.getTheme());
        Drawable drawableI = imageView.getDrawable();
        Bitmap bitmapI,bitmapD;

        if (drawableD instanceof BitmapDrawable) {
            bitmapD= ((BitmapDrawable)drawableD).getBitmap();
        }
        else {

            bitmapD = Bitmap.createBitmap(drawableD.getIntrinsicWidth(), drawableD.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapD);
            drawableD.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawableD.draw(canvas);
        }

        if (drawableI instanceof BitmapDrawable) {
            bitmapI= ((BitmapDrawable)drawableI).getBitmap();
        }
        else {

            bitmapI= Bitmap.createBitmap(drawableI.getIntrinsicWidth(), drawableI.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapI);
            drawableI.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawableI.draw(canvas);
        }
        dif = bitmapD.sameAs(bitmapI);

        return dif;
    }

    public static String getJSON(String url) {
        HttpURLConnection conn = null;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-length", "0");
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.connect();
            int status = conn.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    return sb.toString();
            }
        } catch (IOException e) {
            Log.e("getJSON", e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    Log.e("getJSON", e.getMessage());
                }
            }
        }
        return null;
    }
}
