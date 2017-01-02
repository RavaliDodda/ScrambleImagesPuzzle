package com.riktam.scrambleimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ImageCorrectListioner{
    RecyclerView recyclerView;
    GridLayoutManager recylerViewLayoutManager;
    Context context;
    Drawable[] drawablesList;
    ImageView imageView;
    //    Button checkButton;
    int onlyOnce;
    DisplayMetrics displayMetrics;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Integer> imageRandomNumbers=new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        imageView = (ImageView) findViewById(R.id.rose_image);
//        checkButton = (Button) findViewById(R.id.check_button);
        onlyOnce = 1;

        displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels, displayMetrics.widthPixels));
        recylerViewLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        imageRandomNumbers=randomNumbersGenerator(4);


        recyclerViewAdapter = new RecyclerViewAdapter(recyclerView, this, displayMetrics.widthPixels / 3, verticesList(), randomNumbersGenerator(9),this);
        recyclerView.setAdapter(recyclerViewAdapter);

           }


    ArrayList<ArrayList<Integer>> verticesList() {
        ArrayList<ArrayList<Integer>> allVertex = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ArrayList<Integer> oneVertex = new ArrayList<Integer>();
                oneVertex.add(i);
                oneVertex.add(j);
                allVertex.add(oneVertex);
                Log.d("array", allVertex + "");
            }
        }
        return allVertex;

    }

    ArrayList<Integer> randomNumbersGenerator(int n) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        numbers = new ArrayList<Integer>();

        while (numbers.size() != n) {

            int random = randomGenerator.nextInt(n);
            if (!numbers.contains(random)) {
                numbers.add(random);
                Log.d("random number", random + "");

            }
        }
        return numbers;
    }

    @Override
    public Void imageChange(int roundNo) {
        Toast.makeText(context,"listener worked "+roundNo,Toast.LENGTH_SHORT).show();
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.image2));
        recyclerViewAdapter = new RecyclerViewAdapter(recyclerView, context, displayMetrics.widthPixels / 3, verticesList(), randomNumbersGenerator(9),imageView);

        recyclerView.setAdapter(recyclerViewAdapter);
        return null;
    }
}
