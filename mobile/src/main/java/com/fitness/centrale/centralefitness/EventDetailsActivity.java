package com.fitness.centrale.centralefitness;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class EventDetailsActivity extends AppCompatActivity {


    ImageView eventPicture;
    Bitmap eventPictureBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        //eventPicture = findViewById(R.id.eventPicture);
        //eventPictureBitmap = (Bitmap) getIntent().getParcelableExtra("picture");
        //eventPicture.setImageBitmap(eventPictureBitmap);



    }
}
