package com.example.maddie.optionmenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathActivity extends AppCompatActivity {

    public EditText name;
    public Button query_button;
    public EditText textView2;
    private ScaleGestureDetector SGD;
    private Matrix matrix = new Matrix();
    private int currentLocation;
    private int wantedDest;
    private static DatabaseAccess databaseAccess;

    private ImageView imageView;
    private Button scan1, scan2, scan3, scan4, scan5;
    private Button directions_button;
    private Button destinations_button;
    private TextView dest, start;
    private Context context;
    public RadioGroup radioGroup;
    public RadioButton radioButton;
    public int withinPixels = 15;
    public int width, height;
    public RelativeLayout layout;
    public Boolean destSet;
    public Boolean startSet;
    private LineView lineView;
    private Canvas canvas;
    private ArrayList<PointLocation> locs = new ArrayList<>();
    //private LineView mLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pathactivity_layout);
        //System.out.println("IDS");
        // name = findViewById(R.id.name);
        //query_button = findViewById(R.id.query_button);
        //result_type = findViewById(R.id.result);
        //textView2 = findViewById(R.id.editText2);
        imageView = (ImageView) findViewById(R.id.imageView);
        scan1 = findViewById(R.id.scan1);
        scan2 = findViewById(R.id.scan2);
        scan3 = findViewById(R.id.scan3);
        scan4 = findViewById(R.id.scan4);
        scan5 = findViewById(R.id.scan5);
        canvas = new Canvas();
        startSet = false;
        destSet = false;
        dest = findViewById(R.id.dest);
        start = findViewById(R.id.start);
        directions_button = findViewById(R.id.directions_button);
        //destinations_button = findViewById(R.id.destinations_button);
        context = getApplicationContext();
        final Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 60);
        layout = findViewById(R.id.relativeLayout);
        //buttonCreation();
        //setting onclicklistener to query button
        final View view = new View(this);
            imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    imageView.getWidth(); //height is ready
                    //System.out.println("Width = " + imageView.getWidth());
                    buttonCreation();
                }
            });

        scan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 1");
                currentLocation = 85;
                start.setText("scanner 1");
            }
        });

        scan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 2");
                currentLocation = 73;
                start.setText("scanner 2");

            }
        });

        scan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 3");
                currentLocation = 40;
                start.setText("scanner 3");

            }
        });

        scan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 4");
                currentLocation = 22;
                start.setText("scanner 4");

            }
        });

        scan5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance of database access class and open database connection
                System.out.println("You clicked on Scanner 5");
                currentLocation = 15;
                start.setText("scanner 5");

            }
        });

        directions_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startSet = false;
                start.setText("");
                destSet = false;
                dest.setText("");

                //System.out.println("DB opened");
                PathFinding pathFinding = new PathFinding(databaseAccess.getNodes());
                String path = pathFinding.shortestPath2(currentLocation, wantedDest);
                List<String> pathList = new ArrayList<String>(Arrays.asList(path.split(" ")));
                System.out.println(pathList);

                for(int i = 0; i < pathList.size()-1; i++) {
                    String[] xycoords = databaseAccess.getXY(Integer.parseInt(pathList.get(i)));
                    String[] xycoords2 = databaseAccess.getXY(Integer.parseInt(pathList.get(i+1)));
                    PointLocation location = makePoints(xycoords[0], xycoords[1]);
                    locs.add(location);
                    //System.out.println(pathList.get(i).toString() + " " + pathList.get(i+1).toString());
                    PointLocation location2 = makePoints(xycoords2[0], xycoords2[1]);
                    locs.add(location2);
                    displayLine(view, location, location2);
                }
                if(!locs.isEmpty()){
                    lineView.setPathLocs(locs);
                }
                else{
                    System.out.println("locs is empty");
                }
                //drawPath(location, location2);
            }
        });
    }
    public static int [] getNeighbors(int id){
        int [] neighbors = databaseAccess.getNeighbors(id);
        //databaseAccess.close();
        return neighbors;
    }

    public void setWithinPixels(int pixels){
        this.withinPixels = pixels;
    }

    public void setWantedDest(int dest){
        this.wantedDest = dest;
    }
    public void setStart(int startLoc){
        this.currentLocation = startLoc;
    }

    public void buttonCreation(){
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        String [] dests = databaseAccess.getAllLocationsDestIds();
        final Button[] buttons = new Button[dests.length + 15];
        String[] names = new String[dests.length + 15];
        String name;
        for (int b = 0; b < dests.length; b = b + 4) {

            int id = Integer.parseInt(dests[b]);
            //System.out.println("id = " + id);
            name = dests[b + 1];
            names[id] = name;
            buttons[id] = new Button(this);
            buttons[id].setLayoutParams(new LinearLayout.LayoutParams(30,30));
            buttons[id].setWidth(20);
            buttons[id].setHeight(20);
            buttons[id].setBackgroundResource(R.drawable.transparent);
            buttons[id].setX((int) ((Double.parseDouble(dests[b+2]) * imageView.getWidth()) + ((width - imageView.getWidth()) / 2))-15);
            buttons[id].setY((int) ((Double.parseDouble(dests[b + 3]) * imageView.getHeight()) - (layout.getTop() / 2))-20);
            final int index = id;
            final String finalName = name;
            buttons[id].setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   //System.out.println("view coords = " + buttons[index].getX() + " , " + buttons[index].getY());
                                                   if(startSet == false){
                                                       setStart(index);
                                                       start.setText(finalName);
                                                       startSet = true;
                                                   }
                                                   else if (destSet == false) {
                                                       setWantedDest(index);
                                                       wantedDest = index;
                                                       dest.setText(finalName);
                                                       destSet = true;
                                                   }

                                                   System.out.println("index = " + index + "                                 finalName = " + finalName);

                                               }
                                           }
            );
            buttons[id].setVisibility(View.VISIBLE);
            layout.addView(buttons[id]);
            layout.bringChildToFront(buttons[id]);
        }
        scan1.bringToFront();
        scan2.bringToFront();
        scan3.bringToFront();
        scan4.bringToFront();
        scan5.bringToFront();

    }

    public PointLocation makePoints(String x, String y){
        //System.out.println("width = " + width + "height = " + height );
        //System.out.println("real width = " + imageView.getMeasuredWidth() + " real height = " + imageView.getMeasuredHeight());
        PointLocation location = new PointLocation(((Double.parseDouble(x) * imageView.getWidth()) + ((width - imageView.getWidth()) / 2)),
                (Double.parseDouble(y) * imageView.getHeight()) - (layout.getTop() / 2));
        //System.out.println("Location: " + location.toString());
        return location;
    }

    public void displayLine(View view, PointLocation pointA, PointLocation pointB) {
        lineView = findViewById(R.id.LineView);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(20);
        //System.out.println("Paint");
        lineView.setPointA(pointA);
        lineView.setPointB(pointB);
        lineView.buildPath();
        lineView.draw();
        //lineView.drawLine();


        view.draw(canvas);
        view.invalidate();
    }

    public void drawPath(PointLocation a, PointLocation b){
        //lineView = (LineView) findViewById(R.id.LineView);
        //LineView mLineView = new LineView(this);
        //System.out.println(a.toString());
        //System.out.println(b.toString());
        lineView.setPointA(a);
        lineView.setPointB(b);
        lineView.draw();
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
                Intent intent = new Intent(PathActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(PathActivity.this, NameActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "path", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
