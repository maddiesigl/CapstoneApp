package com.example.maddie.optionmenu;

import java.util.ArrayList;

/**
 * Created by Maddie on 8/30/2018.
 */

public class PointLocation {
    // the coordinates of the point
    public double x;
    public double y;

    public PointLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    // distance returns the distance between this point and a given point
    public double distance(PointLocation p) {
        //System.out.println("THIS: " + this.toString());
        //System.out.println("Distance P: " + p.toString());
        return Math.sqrt((p.x - this.x) * (p.x - this.x) +
                (p.y - this.y) * (p.y - this.y));
    }

    public String toString() {
        String s = "";
        s = "(" + this.getX() + "," + this.getY() + ")";

        return s;
    }

    public static ArrayList<Integer> nearestPoint(PointLocation[] points, PointLocation point, int withinPixels) {
        PointLocation p = points[0];
        ArrayList<Integer> closePoints = new ArrayList<Integer>();
        System.out.println("point = " + point.toString());
        int count = 0;
        for(int i = 0; i < points.length; i++){
            if(points[i] != null) {
                count++;
            }
        }
       // System.out.println("POINT LENGTH " + count);

        int nearestIndex = 0;
        for (int i = 0; i < count; i++) {
            //System.out.println("i = " + points[i]);
            if (points[i].distance(point) < p.distance(point) && points[i].distance(point) <= withinPixels) {
                p = points[i];
                closePoints.add(i);
                nearestIndex = i;
                //System.out.println("P: " + p.toString());

            }
            //System.out.println("Distance = " + points[i].distance(point));

           // System.out.println("ClosePoints = " + closePoints.toString());
        }


        return closePoints;
    }
}
