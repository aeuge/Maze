package com.example.user157.maze;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;


import static java.lang.Math.round;

public class Player extends Dot  {
    Bitmap bitmap;

    public Player(Point start, int size, Context context) {
        super(start,getPaint(),size);
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.player);
    }

    private static Paint getPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        return paint;
    }

    public void Move (int diffx,int diffy) {
        point.x += diffx;
        point.y += diffy;
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        draw_calc(rect);
        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),newRect,getPaint());
    }
}
