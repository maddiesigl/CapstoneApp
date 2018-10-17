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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class NameActivity extends AppCompatActivity {

    public EditText name;
    public Button query_button;
    public EditText textView;
    public EditText textView2;
    private ScaleGestureDetector SGD;
    private Matrix matrix = new Matrix();
    private int currentLocation;
    private int wantedDest;
    private static DatabaseAccess databaseAccess;
    public Context context;

    private ImageView imageView;
    private Button scan1, scan2, scan3, scan4, scan5;
    private Button directions_button;
    public RadioGroup radioGroup;
    public RadioButton radioButton;
    public int withinPixels = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nameactivity_layout);
        //System.out.println("IDS");
        name = findViewById(R.id.name);
        query_button = findViewById(R.id.query_button);
        //result_type = findViewById(R.id.result);
        textView = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.editText2);
        imageView = findViewById(R.id.imageView);
        scan1 = findViewById(R.id.scan1);
        scan2 = findViewById(R.id.scan2);
        scan3 = findViewById(R.id.scan3);
        scan4 = findViewById(R.id.scan4);
        scan5 = findViewById(R.id.scan5);
        radioGroup = findViewById(R.id.radioGroup);

        context = getApplicationContext();


        //setting onclicklistener to query button


        query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("Listener");

                databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                System.out.println("DB opened");
                String n = name.getText().toString();
                System.out.println(n);
                n = tokenizer(n);
                n = n.replace("'", "").replace(";", "");
                System.out.println(n);
                name.setText(n);

                String type = databaseAccess.getCoords(n);

                if(type.isEmpty() || name.getText().toString().isEmpty()){
                    imageView.bringToFront();
                    textView.bringToFront();
                    textView2.bringToFront();
                    textView.setText("search returned no results");
                    System.out.println("search returned no results");
                }
                else {
                    String[] coords = type.split("[\\n,]+");
                    //imageView = (ImageView) findViewById(R.id.imageView);

                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    int height = size.y;


                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout);

                    imageView.bringToFront();
                    scan1.bringToFront();
                    scan2.bringToFront();
                    scan3.bringToFront();
                    scan4.bringToFront();
                    scan5.bringToFront();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < coords.length - 1; i = i + 3) {
                        //System.out.println("i = " + i);
                        //System.out.println("Add image: " + ((int) (imageView.getWidth() * Double.parseDouble(coords[i])))
                        //      + ", " + ((int) (imageView.getHeight() * Double.parseDouble(coords[i + 1]))));
                        ImageView image = new ImageView(getApplicationContext());
                        image.setImageResource(R.drawable.pin2);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 60);
//width and height of your Image ,if it is inside Relative change the LinearLayout to RelativeLayout.
                        image.setLayoutParams(layoutParams);

                        image.setX((int) ((Double.parseDouble(coords[i]) * imageView.getWidth()) + ((width - imageView.getWidth()) / 2)) - 30);
                        image.setY((int) ((Double.parseDouble(coords[i + 1]) * imageView.getHeight()) - (layout.getTop() / 2)) + 10);

                        // Adds the view to the layout
                        layout.addView(image);
                        layout.bringChildToFront(image);
                        sb.append(coords[i + 2] + " : " + ((int) (imageView.getWidth() * Double.parseDouble(coords[i])))
                                     + ", " + ((int) (imageView.getHeight() * Double.parseDouble(coords[i + 1]))) + "\n");
                    }
                        textView.setText(sb.toString());

                }
                textView.bringToFront();
                textView2.bringToFront();


                        databaseAccess.close();
            }
        });

        scan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 1");
                Toast.makeText(NameActivity.this, "Scanner 1", Toast.LENGTH_LONG).show();
                currentLocation = 85;
            }
        });

        scan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 2");
                Toast.makeText(NameActivity.this, "Scanner 2", Toast.LENGTH_LONG).show();
                currentLocation = 73;
            }
        });

        scan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 3");
                Toast.makeText(NameActivity.this, "Scanner 3", Toast.LENGTH_LONG).show();
                currentLocation = 40;
            }
        });

        scan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 4");
                Toast.makeText(NameActivity.this, "Scanner 4", Toast.LENGTH_LONG).show();
                currentLocation = 22;
            }
        });

        scan5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 5");
                Toast.makeText(NameActivity.this, "Scanner 5", Toast.LENGTH_LONG).show();
                currentLocation = 15;
            }
        });

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
                Intent intent = new Intent(NameActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Intent intent2 = new Intent(NameActivity.this, PathActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "path", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
