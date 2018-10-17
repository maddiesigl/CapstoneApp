package com.example.maddie.optionmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Maddie on 10/10/2018.
 */



public class PassportActivity extends AppCompatActivity {
    private static DatabaseAccess databaseAccess;
    public ArrayList<String> boardingPassInfo;
    private TextView startingdest, startTime, endingDest, endTime, group, gate, boardingTime, passenger, seat, flight;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);
        startingdest = findViewById(R.id.starting_dest);
        startTime = findViewById(R.id.start_time);
        endingDest = findViewById(R.id.ending_dest);
        endTime = findViewById(R.id.end_time);
        group = findViewById(R.id.group);
        gate = findViewById(R.id.gate);
        boardingTime = findViewById(R.id.boarding);
        passenger = findViewById(R.id.passenger);
        seat = findViewById(R.id.seat);
        flight = findViewById(R.id.boarding_pass);
        getPassengerInfo();



        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat format2 = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm aaa");
        Date startDate = null;
        Date endDate = null;
        Date boardDate = null;
        try {
            startDate = format1.parse(boardingPassInfo.get(10));
            endDate = format1.parse(boardingPassInfo.get(12));
            boardDate = format1.parse(boardingPassInfo.get(14));
            System.out.println(format2.format(boardDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }



        System.out.println(boardingPassInfo.get(1) + " " + boardingPassInfo.get(2));
        passenger.setText(boardingPassInfo.get(1) + " " + boardingPassInfo.get(2));
        seat.setText(boardingPassInfo.get(3));
        group.setText(boardingPassInfo.get(7));
        startingdest.setText(boardingPassInfo.get(9));
        startTime.setText(format2.format(startDate));
        endingDest.setText(boardingPassInfo.get(11));
        endTime.setText(format2.format(endDate));
        gate.setText(getGateByID(Integer.parseInt(boardingPassInfo.get(13))));
        boardingTime.setText(format2.format(boardDate));
        //boardingTime.setText(boardingPassInfo.get(14));
        flight.setText("Flight #" + boardingPassInfo.get(15));

    }

    public void getPassengerInfo(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        boardingPassInfo = databaseAccess.getBoardingPassesByID(1);
        System.out.println(Arrays.toString(boardingPassInfo.toArray()));


    }

    public String getGateByID(int id){
        String name = null;
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        name = databaseAccess.getGateByID(id);
        return name;
    }
}
