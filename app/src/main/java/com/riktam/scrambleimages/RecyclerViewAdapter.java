package com.riktam.scrambleimages;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by JUNED on 6/10/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    LinearLayout linearLayout;
    public int size;
    int count = 0;
    ImageView imageView;
    int startItemPosition, stopItemPosition;
    RecyclerView recyclerView;
    ArrayList<ArrayList<Integer>> allVertex;
    ArrayList<Integer> randomNumbers;
    int roundCount;
     int trialCount;
    ImageView randomImageView;
    static ImageCorrectListioner imageCorrectListioner;

    public RecyclerViewAdapter(RecyclerView recyclerview, Context context1, int width, ArrayList<ArrayList<Integer>> list, ArrayList<Integer> numbers ,ImageCorrectListioner imageCorrectListioner) {
        recyclerView = recyclerview;
        this.imageCorrectListioner=imageCorrectListioner;
        context = context1;
        size = width;
        trialCount=0;
        roundCount=1;
        randomNumbers = numbers;
        allVertex = list;
    }
    public RecyclerViewAdapter(RecyclerView recyclerview, Context context1, int width, ArrayList<ArrayList<Integer>> list, ArrayList<Integer> numbers ,ImageView imageView)
    {
        recyclerView = recyclerview;
        context = context1;
        size = width;
        trialCount=0;
        roundCount=1;
        randomNumbers = numbers;
        allVertex = list;
        this.randomImageView=imageView;
    }

    public LinearLayout getItem(int position) {
        return (LinearLayout) recyclerView.getLayoutManager().findViewByPosition(position);
    }

    void exchangeImages()

    {
        ImageView dummy = new ImageView(context);
        ImageView startImageView = ((ImageView) (getItem(startItemPosition).findViewById(R.id.image_recycler_view)));
        ImageView stopImageView = (ImageView) (getItem(stopItemPosition).findViewById(R.id.image_recycler_view));
        dummy.setImageDrawable(startImageView.getDrawable());
        startImageView.setImageDrawable(stopImageView.getDrawable());
        stopImageView.setImageDrawable(dummy.getDrawable());

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View v) {

            super(v);

        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        linearLayout = new LinearLayout(context);
        imageView = new ImageView(context);
        imageView.setId(R.id.image_recycler_view);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        linearLayout.addView(imageView);
        final ViewHolder viewHolder1 = new ViewHolder(linearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    startItemPosition = viewHolder1.getAdapterPosition();
                    Toast.makeText(context, "Image one selected " + startItemPosition, Toast.LENGTH_SHORT).show();
                    count++;
                } else if (count == 1) {
                    stopItemPosition = viewHolder1.getAdapterPosition();
                    Toast.makeText(context, "Image two selected and swaping takes place " + stopItemPosition, Toast.LENGTH_SHORT).show();
                    count = 0;
                    exchangeImages();
                    checkImage();

                }
            }
        });

//        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (count == 0) {
//                    startItemPosition = viewHolder1.getAdapterPosition();
//                    //Toast.makeText(context, "Image one selected " + startItemPosition, Toast.LENGTH_SHORT).show();
//                    count++;
//                } else if (count == 1) {
//                    stopItemPosition = viewHolder1.getAdapterPosition();
//                    //Toast.makeText(context, "Image two selected and swaping takes place " + stopItemPosition, Toast.LENGTH_SHORT).show();
//                    count = 0;
//                    exchangeImages();
//                    checkImage();
//
//                }
//                return true;
//            }
//        });
        return viewHolder1;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bitmap orginalImage;
        if(randomImageView!=null)
        {
            orginalImage =((BitmapDrawable)(randomImageView.getDrawable())).getBitmap();
        }
        else
         orginalImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.rose);
        Bitmap scaleImage = Bitmap.createScaledBitmap(orginalImage, 240, 240, true);
        int width, height;
        width = 80;
        height = 80;
        for (int i = 0; i < 9; i++) {
            Log.d("random number", allVertex.get(randomNumbers.get(i)) + "");
        }
        Bitmap partImage = Bitmap.createBitmap(scaleImage, allVertex.get(randomNumbers.get(position)).get(0) * width, allVertex.get(randomNumbers.get(position)).get(1) * width, width, height);
        imageView.setImageBitmap(partImage);
    }

    @Override
    public int getItemCount() {

        return 9;
    }

    public void checkImage() {

        Bitmap[] parts = new Bitmap[9];


        // getting all parts of images and storing in array
        for (int i = 0; i < 9; i++) {
            ImageView partImage = (ImageView) (recyclerView.getLayoutManager().findViewByPosition(i).findViewById(R.id.image_recycler_view));
            Bitmap bitmap = ((BitmapDrawable) (partImage.getDrawable())).getBitmap();
            parts[i] = bitmap;
        }

        //forming single image from all images storing in result bitmap
        Bitmap result = Bitmap.createBitmap(parts[0].getWidth() * 3, parts[0].getHeight() * 3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        for (int i = 0; i < parts.length; i++) {
            canvas.drawBitmap(parts[i], parts[i].getWidth() * (i % 3), parts[i].getHeight() * (i / 3), paint);
        }

        // comparing image2 images
        Bitmap orginalBitmap;
        if(randomImageView!=null)
        {
            orginalBitmap= ((BitmapDrawable) (randomImageView.getDrawable())).getBitmap();
        }
        else {
             orginalBitmap = ((BitmapDrawable) (context.getResources().getDrawable(R.drawable.rose))).getBitmap();
        }
        Bitmap scaledFullBitmap = Bitmap.createScaledBitmap(orginalBitmap, parts[0].getWidth() * 3, parts[0].getHeight() * 3, true);
        boolean resultboolean = isImagesSame(scaledFullBitmap, result);
        if (resultboolean == true) {

            Toast.makeText(context, "well done by "+trialCount +" trials", Toast.LENGTH_SHORT).show();
            trialCount=0;
            imageCorrectListioner.imageChange(roundCount);
            roundCount++;
        }
        else
        {
            trialCount++;
        }
           // Toast.makeText(context, "try again", Toast.LENGTH_SHORT).show();


    }

    boolean isImagesSame(Bitmap bitmap1, Bitmap bitmap2) {
        for (int i = 0; i < bitmap1.getWidth(); i++) {
            for (int j = 0; j < bitmap1.getHeight(); j++) {
                if (bitmap1.getPixel(i, j) != bitmap2.getPixel(i, j)) {
                    return false;
                }

            }

        }
        roundCount++;
        return true;
    }
}