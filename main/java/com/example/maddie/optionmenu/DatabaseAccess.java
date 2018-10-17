package com.example.maddie.optionmenu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maddie on 8/8/2018.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    private int id = 0;
    Cursor c = null;

    //private constructor so that object creation from ouside the class is avoided
    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }
    //to return the single instance of database;

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }

        return instance;
    }



    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db != null){
            this.db.close();
        }
    }

    //method to query and return the result from database

    public String getCoords(String text){
        c = db.rawQuery("select xcoord, ycoord, name from locations where UPPER(name) = UPPER('"+text+"') OR name like '%"+text+"%'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()) {
            String type = c.getString(0) + "," + c.getString(1) + "," + c.getString(2);
            buffer.append("" + type + "\n");
        }

        return buffer.toString();
    }



    public String [] getXY(int id){
        Cursor c = null;
        c = db.rawQuery("select xcoord, ycoord from locations where locationid = " + id, new String[]{});

        String [] array = new String[c.getCount()*2];

        int i = 0;
        while(c.moveToNext()){
            String points = c.getString(0);
            array[i] = points;
            points = c.getString(1);
            array[i+1] = points;
        }
        c.close();

        return array;
    }

    public String [] getAllLocationsDest() {
        c = db.rawQuery("select name, xcoord, ycoord from locations where locationId <= 184", new String[]{});
        //System.out.println("COUNT " + c.getCount());
        String[] array = new String[c.getCount()*3];
        int i = 0;
        while (c.moveToNext()) {
            //System.out.println("DB i = " + i);
            String points = c.getString(0);
            //System.out.println("Name " + points);
            array[i] = points;
            i++;
            //System.out.println("DB i = " + i);

            points = c.getString(1);
            //System.out.println("XCoord " + points);
            array[i] = points;
            i++;
            //System.out.println("DB i = " + i);

            points = c.getString(2);
            //System.out.println("YCoord " + points);
            array[i] = points;
            i++;
        }
        return array;
    }

    public String [] getAllLocationsDestIds() {
        Cursor c = null;

        c = db.rawQuery("select locationid, name, xcoord, ycoord from locations where locationId <= 184 or locationid = 306", new String[]{});
        //System.out.println("COUNT " + c.getCount());

        String[] array = new String[c.getCount()*4];
        int i = 0;
        while (c.moveToNext()) {
            //System.out.println("DB i = " + i);
            String points = c.getString(0);
            //System.out.println("Name " + points);
            array[i] = points;
            i++;
            //System.out.println("DB i = " + i);

            points = c.getString(1);
            //System.out.println("XCoord " + points);
            array[i] = points;
            i++;
            //System.out.println("DB i = " + i);

            points = c.getString(2);
            //System.out.println("YCoord " + points);
            array[i] = points;
            i++;

            points = c.getString(3);
            array[i] = points;
            i++;
        }
        c.close();
        return array;
    }


    public int [] getNodes(){
        Cursor c = null;

        c = db.rawQuery("select locationid from locations", new String []{});

        //System.out.println("COUNT "  + c.getCount());
        int [] array = new int [c.getCount()];
        int count = 0;
        while (c.moveToNext()) {
            int nodeid = c.getInt(0);
            array[count] = nodeid;
            count++;
        }
        c.close();
        return array;
    }

    public int [] getNeighbors(int idval) {
        //System.out.println("idval = " + idval);
        Cursor c = null;

        int nodeid;
        int count = 0;
        int[] array = new int[20];
        c = db.rawQuery("select node1 from connections where node2 = " + idval, new String[]{});
        if (c.getCount() == 0) {
            //System.out.println("count = 0");
        }
        else {
            while (c.moveToNext()) {
                nodeid = c.getInt(0);
                //System.out.println("nodeid = " + nodeid + " count = " + count);
                array[count] = nodeid;
                count++;
            }
        }
        c.close();
        c = db.rawQuery("select node2 from connections where node1 = " + idval, new String[]{});
        if (c.getCount() == 0) {

        }
        else {
            while (c.moveToNext()) {
                nodeid = c.getInt(0);
                array[count] = nodeid;
                count++;
            }
        }

        c.close();
        return array;
    }

    public ArrayList<String> getBoardingPassesByID(int id){
        Cursor c = null;
        int count = 0;
        ArrayList<String> array = new ArrayList<>();
        c = db.rawQuery("select * from BoardingPasses left join Flights where boardID = " + id, new String[]{});
        while(c.moveToNext()){
            for(int i = 0; i < 16; i++){
                if(c.getType(i) == Cursor.FIELD_TYPE_INTEGER){
                    array.add(c.getInt(i) + "");
                }
                else if(c.getType(i) == Cursor.FIELD_TYPE_STRING){
                    array.add(c.getString(i));
                }
            }
        }
        c.close();
        return array;
    }
    public String getGateByID(int id){
        Cursor c = null;
        String name = null;
        c = db.rawQuery("select name from Locations where locationID = " + id, new String[]{});
        if(c.moveToNext()) {
            name = c.getString(0);
        }
        return name;
    }




}

