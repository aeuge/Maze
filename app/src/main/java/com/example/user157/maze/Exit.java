package com.example.user157.maze;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.os.Handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


import static java.lang.Math.random;
import static java.lang.Math.round;

public class Exit extends Dot implements GifDrawable.Callback{
    private View view;
    private GifDrawable gifDrawable;
    private Handler handler = new Handler();

    @SuppressLint("ResourceType")
    public Exit(Point start, int size, Context context, View view) {
        super(start,getPaint(),size);
        this.view = view;
        InputStream is;
        is = context.getResources().openRawResource(R.drawable.exit2);
        try {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            gifDrawable = new GifDrawable(bytes);
            gifDrawable.setCallback(this);
            gifDrawable.start();
        }
        catch (IOException e) {
            Log.i("Exit","error");
        }
    }

    private static Paint getPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        return paint;
    }

    public void draw(Canvas canvas, Rect rect) {
        draw_calc(rect);
        gifDrawable.setBounds(newRect);
        gifDrawable.draw(canvas);
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        view.invalidate();
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
        handler.postAtTime(what,who,when);
    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
        handler.removeCallbacks(what);
    }
}
