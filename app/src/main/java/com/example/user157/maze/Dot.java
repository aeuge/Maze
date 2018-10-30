package com.example.user157.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import static java.lang.Math.round;

public class Dot implements Drawable {
    protected int x,y,x1,y1;
    protected Paint paint;
    protected Point point;
    protected Point coord = new Point();
    protected float cellSize;
    protected int size;
    protected int wall=5;
    protected Rect newRect;

    public Dot(Point start,Paint paint, int size) {
        this.size = size;
        this.point = start;
        this.paint = paint;
    }

    public void draw_calc(Rect rect) {
        if ((rect.right - rect.left)/size<20) {
            wall = 2;
        }
        cellSize = (float)(rect.right - rect.left-wall)/(size-1)*2-wall;

        x = round(point.x/2*cellSize +(point.x+1)/2*wall+rect.left);
        y = round(point.y/2*cellSize +(point.y+1)/2*wall+rect.top);
        x1 = round(point.x/2*cellSize +(point.x+1)/2*wall+rect.left+cellSize);
        y1 = round(point.y/2*cellSize +(point.y+1)/2*wall+rect.top+cellSize);
        coord.x = round(x+cellSize/2);
        coord.y = round(y+cellSize/2);
        newRect = new Rect(x,y,x1,y1);
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        draw_calc(rect);
        canvas.drawRect(newRect,paint);
    }

    public int getCoordX () {
        return coord.x;
    }

    public int getCoordY () {
        return coord.y;
    }

    public int getX () {
        return point.x;
    }

    public int getY () {
        return point.y;
    }

    public Point getPoint () {
        return point;
    }

}
