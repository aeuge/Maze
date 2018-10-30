package com.example.user157.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.round;

public class GameManager extends SimpleOnGestureListener {
    private List<Drawable> drawables=new ArrayList<>();
    private View view;
    private Player player;
    private Exit exit;
    private Maze maze;
    private Rect rect = new Rect();
    private int screenSize;
    private float scrollx = 0;
    private float scrolly = 0;
    private boolean needUpdateScroll=false;
    private Context context;

    public void setScrollx (float scrollx) {
        this.scrollx = scrollx;
    }

    public float getScrollx () {
        return scrollx;
    }

    public void setScrolly (float scrolly) {
        this.scrolly = scrolly;
    }

    public float getScrolly () {
        return scrolly;
    }

    public void setNeedUpdateScroll (boolean scroll) {
        needUpdateScroll = scroll;
    }

    public boolean getNeedUpdateScroll () {
        return needUpdateScroll;
    }

    public GameManager(Context context){
        this.context = context;
    }
    private void newLab (int size) {
        drawables.clear();
        maze = new Maze(size);
        player = new Player(maze.getStart(),size, context);
        exit = new Exit(maze.getEnd(),size, context,view);
        drawables.add(maze);
        drawables.add(player);
        drawables.add(exit);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        int diffx,diffy;
        diffx = round(e.getX()-player.getCoordX()+scrollx);
        diffy = round(e.getY()-player.getCoordY()+scrolly);
        //Log.i("GM",String.format("X=%d,Y=%d",diffx,diffy));
        if (Math.abs(diffx)>Math.abs(diffy)) {
            diffx = diffx > 0 ? 1 : -1;
            diffy = 0;
        } else {
            diffy = diffy > 0 ? 1 : -1;
            diffx = 0;
        }
        if (maze.canPlayerGoTo(player.getX()+diffx,player.getY()+diffy)) {
            player.Move(diffx*2,diffy*2);
        }
        view.invalidate();
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int diffx,diffy;
        diffx = round(e2.getX()-e1.getX());
        diffy = round(e2.getY()-e1.getY());
        //Log.i("GM",String.format("X=%d,Y=%d",diffx,diffy));
        if (Math.abs(diffx)>Math.abs(diffy)) {
            diffx = diffx > 0 ? 1 : -1;
            diffy = 0;
        } else {
            diffy = diffy > 0 ? 1 : -1;
            diffx = 0;
        }
        while (maze.canPlayerGoTo(player.getX()+diffx,player.getY()+diffy)) {
            player.Move(diffx*2,diffy*2);
            if (maze.canPlayerGoTo(player.getX()+diffy,player.getY()+diffx)||maze.canPlayerGoTo(player.getX()-diffy,player.getY()-diffx)) {
                break;
            }
        }
        view.invalidate();
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    public void draw(Canvas canvas) {
        if (player.getPoint().equals(exit.getPoint())) {
            //new lab
            newLab(maze.getSize()+2);
        }
        for (Drawable drawableItem: drawables) {
            drawableItem.draw(canvas, rect);
        }
        //exit.draw(canvas, rect);

    }

    public void setView(View view) {
        this.view = view;
        newLab(45);
    }
    public void setScreenSize(int width, int height) {
        screenSize = Math.min(width, height);
        rect.set((width-screenSize)/2, (height-screenSize)/2,(width+screenSize)/2,(height+screenSize)/2);
    }
}
