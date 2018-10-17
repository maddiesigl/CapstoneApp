package com.example.maddie.optionmenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maddie.optionmenu.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public EditText textView;
    public EditText textView2;
    private ScaleGestureDetector SGD;
    private Matrix matrix = new Matrix();
    private int currentLocation;
    private int wantedDest;
    private static DatabaseAccess databaseAccess;
    public Context context;
    public TextView name;

    private ImageView imageView;
    private Button scan1, scan2, scan3, scan4, scan5, query_button;
    private Button directions_button;
    public RadioGroup radioGroup;
    public RadioButton radioButton;
    public int withinPixels = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.maddie.optionmenu.R.layout.searchactivity_layout);
        //System.out.println("IDS");
        //result_type = findViewById(R.id.result);
        textView = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.editText2);
        imageView = findViewById(R.id.imageView);
        scan1 = findViewById(R.id.scan1);
        scan2 = findViewById(R.id.scan2);
        scan3 = findViewById(R.id.scan3);
        scan4 = findViewById(R.id.scan4);
        scan5 = findViewById(R.id.scan5);
        name = findViewById(R.id.name);
        query_button = findViewById(R.id.query_button);

        radioGroup = findViewById(R.id.radioGroup);

        context = getApplicationContext();

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                imageView.getTop(); //height is ready
                System.out.println("ImageView top: " + imageView.getTop());
            }
        });

        scan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                textView.setText("Scanner 1");
                currentLocation = 85;
            }
        });

        scan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                textView.setText("Scanner 2");
                currentLocation = 73;
            }
        });

        scan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                textView.setText("Scanner 3");
                currentLocation = 40;
            }
        });

        scan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                textView.setText("Scanner 4");
                currentLocation = 22;
            }
        });

        scan5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                textView.setText("Scanner 5");
                currentLocation = 15;
            }
        });

    }

    public boolean onTouchEvent(MotionEvent event) {
        // MotionEvent object holds X-Y values
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //may need to adjust the X, Y
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.open();
            PointLocation point = new PointLocation(0,0);
            PointLocation p = new PointLocation((((int)event.getX())-76), (((int)event.getY())-232));
            String [] locs = databaseAccess.getAllLocationsDest();
            PointLocation [] points = new PointLocation [locs.length];
            String [] names = new String [locs.length];
            int locsInd = 0;
            int namesInd = 0;
            //System.out.println("LOCS length " + locs.length);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            RelativeLayout layout = findViewById(R.id.relativeLayout);
            imageView = (ImageView) findViewById(R.id.imageView);

            imageView.bringToFront();
            int count = 0;
            for(int j = 0; j < locs.length; j++){
                if(locs[j]!=null){
                    count++;
                }
            }
            //System.out.println("count " + count);
            for(int i = 0; i < count; i++){

                if(i % 3 == 0){
                    names[namesInd] = locs[i];
                    namesInd++;
                }
                else {
                    //System.out.println("LOCS[i] " + locs[i]);
                    //System.out.println("i = " + i);
                    point = new PointLocation((int) ((Double.parseDouble(locs[i]) * imageView.getWidth()) + ((width - imageView.getWidth()) / 2))-87,
                            (int) ((Double.parseDouble(locs[i + 1]) * imageView.getHeight()) - (layout.getTop() / 2)) + 57);
                    points[locsInd] = point;
                    locsInd++;
                    i++;
                }
            }

            ArrayList<Integer> index = PointLocation.nearestPoint(points, p, withinPixels);
            textView.bringToFront();
            textView2.bringToFront();
            StringBuilder sb = new StringBuilder();
            if(index.isEmpty()){
                textView.setText("No close point");
            }
            else {
                for (int i = 0; i < index.size(); i++) {
                    sb.append(names[index.get(i)] + " : " + points[index.get(i)].toString() + "\n");
                    System.out.println(names[i] + " : " + points[i].toString());


                    ImageView image = new ImageView(getApplicationContext());
                    image.setImageResource(R.drawable.pin2);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 60);
//width and height of your Image ,if it is inside Relative change the LinearLayout to RelativeLayout.
                    image.setLayoutParams(layoutParams);
                    image.setX((int) points[index.get(i)].getX()+57);
                    image.setY((int) points[index.get(i)].getY()-47);
                    layout.addView(image);
                    image.bringToFront();
                }
                textView.setText(sb.toString());
            }

            String text = "click at x = " + event.getX()  +  " and y = " + event.getY();
            System.out.println(text);
           // Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }


        return super.onTouchEvent(event);
    
    }


    public static int [] getNeighbors(int id){
        int [] neighbors = databaseAccess.getNeighbors(id);
        //databaseAccess.close();
        return neighbors;
    }

    public void checkButton(View view){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        System.out.println("RadioButton: " + radioButton.getText());
        setWithinPixels(Integer.parseInt(radioButton.getText().toString()));
        System.out.println("withinPixels = " + withinPixels);
    }

    public void setWithinPixels(int pixels){
        this.withinPixels = pixels;
    }

    public String tokenizer(String s){
        String after = s.trim().replaceAll(" +", " ");
        return after;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                return true;
            case R.id.item2:
                Intent intent = new Intent(SearchActivity.this, NameActivity.class);
                startActivity(intent);
                Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Intent intent2 = new Intent(SearchActivity.this, PathActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "path", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item4:
                Intent intent1 = new Intent(SearchActivity.this, PassportActivity.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
