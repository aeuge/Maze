package com.example.user157.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Maze implements Drawable {
    private Paint paint;
    private final boolean[][] array;

    public int getSize() {
        return size;
    }

    private final int size;
    private Point start;
    private final Point end = new Point(1,1);
    private int bestScore = 0;
    private float cellSize;
    private float wall = 10;

    public Maze(int size) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        this.size = size;
        array = new boolean[size][size];
        generateMaze();
    }

    private void generateMaze () {
        for (int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] = i % 2 != 0 && j % 2 != 0 && i < size - 1 && j < size - 1;
            }
        }
        Random random = new Random();
        Stack<Point> stack = new Stack<>();
        stack.push(end);
        while (stack.size()>0) {
           Point current = stack.peek();
           List<Point> unusedNeighbours = new LinkedList<>();
            //left
            if (current.x > 2) {
                if (!isUsedCell(current.x-2, current.y)) {
                    unusedNeighbours.add(new Point(current.x-2, current.y));
                }
            }
            //top
            if (current.y > 2) {
                if (!isUsedCell(current.x, current.y-2)) {
                    unusedNeighbours.add(new Point(current.x, current.y-2));
                }
            }
            //right
            if (current.x < size - 2) {
                if (!isUsedCell(current.x+2, current.y)) {
                    unusedNeighbours.add(new Point(current.x+2, current.y));
                }
            }
            //bottom
            if (current.y < size-2) {
                if (!isUsedCell(current.x, current.y+2)) {
                    unusedNeighbours.add(new Point(current.x, current.y+2));
                }
            }
            if (unusedNeighbours.size()>0) {
                int rnd = random.nextInt(unusedNeighbours.size());
                Point direction = unusedNeighbours.get(rnd);
                int diffX = (direction.x - current.x) / 2;
                int diffY = (direction.y - current.y) / 2;
                array[current.x+diffX][current.y+diffY] = true;
                stack.push(direction);
            } else {
                if (stack.size()>bestScore) {
                    bestScore =stack.size();
                    start = current;
                }
                stack.pop();
            }
        }
    }

    public boolean canPlayerGoTo(int x, int y) {
        return array[x][y];
    }

    public boolean isUsedCell (int x, int y) {
        if (x<0 || y<0 || x>=size-1 || y>=size - 1) {
            return false;
        }
        return array[x][y-1]||array[x-1][y]||array[x][y+1]||array[x+1][y];
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        if ((rect.right - rect.left)/size<20) {
            wall = 1;
        }
        cellSize = (float)(rect.right - rect.left-wall)/(size-1)*2-wall;
        for (int i=0;i<size;++i) {
            for (int j=0;j<size;++j) {
                if (!array[i][j]) {
                    float left = i/2*cellSize +(i+1)/2*wall+rect.left;
                    float top = j/2*cellSize +(j+1)/2*wall+rect.top;
                    canvas.drawRect(left,top,i%2==0?left+wall:left+cellSize,j%2==0?top+wall:top+cellSize,paint);
                }
            }
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
