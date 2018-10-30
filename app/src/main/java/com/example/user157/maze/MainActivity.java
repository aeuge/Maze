package com.example.user157.maze;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MazeView view;
    private GestureDetector gestureDetector;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int u[] = new int[2];
        int i;
        super.onCreate(savedInstanceState);
        gameManager = new GameManager(this);
        view = new MazeView(this, gameManager);
        setContentView(view);
        //view.getLocationOnScreen(u);
        //i = getWindow().getDecorView().getRootView();
        gestureDetector = new GestureDetector(this,gameManager);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameManager.getNeedUpdateScroll()) {
            //Log.i("GD",String.format("scX=%f,scY=%f",gameManager.getScrollx(),gameManager.getScrolly()));
            gameManager.setScrollx(gameManager.getScrollx()-event.getX());
            gameManager.setScrolly(gameManager.getScrolly()-event.getY());
            gameManager.setNeedUpdateScroll(false);
        }
        //Log.i("GD",String.format("X=%f,Y=%f",event.getX(),event.getY()));
        return gestureDetector.onTouchEvent(event);

    }
}
