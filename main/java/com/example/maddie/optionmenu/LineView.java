package com.example.maddie.optionmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;


/**
 * Created by Maddie on 4/18/2018.
 */

public class LineView extends View {

    private Paint paint = new Paint();
    private Path path = new Path();
    private ArrayList<PointLocation> pathLocs = new ArrayList<PointLocation>();
    private Canvas mCanvas;
    private PointLocation pointA = new PointLocation(0,0),
            pointB = new PointLocation(0,0), pointC;
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        mCanvas = canvas;

        if(pathLocs.isEmpty()){
            System.out.println("Path is empty");
        }
        else {
            paint.setColor(Color.rgb(0,128,0));
            canvas.drawCircle((float)pathLocs.get(0).getX(), (float)pathLocs.get(0).getY(), 5, paint);
            paint.setColor(Color.BLACK);
            for (int i = 0; i < pathLocs.size(); i = i + 2) {
                //canvas.drawCircle((float) (pathLocs.get(i).getX()), (float) (pathLocs.get(i).getY()), 6, paint);
                canvas.drawLine((float) (pathLocs.get(i).getX()), (float) (pathLocs.get(i).getY()), (float) (pathLocs.get(i + 1).getX()), (float) (pathLocs.get(i + 1).getY()), paint);
            }
            paint.setColor(Color.RED);
            canvas.drawCircle((float)pathLocs.get(pathLocs.size()-1).getX(), (float)pathLocs.get(pathLocs.size()-1).getY(), 7, paint);
        }

        //canvas.drawLine((float)pointA.x, (float)pointA.y, (float)pointB.x, (float)pointB.y, paint);
        super.onDraw(canvas);
        pathLocs.clear();
    }
    public void setPointA(PointLocation point){
        pointA = point;
    }

    public void setPointB(PointLocation point){
        pointB = point;
    }
    public void setPointC(PointLocation point){
        pointC = point;
    }

    public void draw(){
        invalidate();
        requestLayout();
    }

    public void drawLine(){
        invalidate();
    }

    public void buildPath(){
        //System.out.println("Point A = " + pointA.toString() + " Point B = " + pointB.toString());
        path.moveTo((float)pointA.x, (float)pointA.y);
        path.lineTo((float)pointB.x, (float)pointB.y);
        //invalidate();
        requestLayout();
    }

    public void setPathLocs(ArrayList<PointLocation> locations){
        this.pathLocs = locations;
    }
}
