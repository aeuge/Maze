package com.example.user157.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MazeView extends View {
    private GameManager gameManager;

    public MazeView(Context context, GameManager gameManager) {
        super(context);
        this.gameManager = gameManager;
        gameManager.setView(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        gameManager.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        gameManager.setScreenSize(w,h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameManager.setScrollx(event.getX());
        gameManager.setScrolly(event.getY());
        gameManager.setNeedUpdateScroll(true);
        //Log.i("MV",String.format("X=%f,Y=%f",event.getX(),event.getY()));
        return false;
    }
}
