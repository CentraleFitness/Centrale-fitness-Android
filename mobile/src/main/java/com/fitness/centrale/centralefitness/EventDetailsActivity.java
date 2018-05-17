package com.fitness.centrale.centralefitness;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {


    ImageView eventPicture;
    Bitmap eventPictureBitmap;
    TextView title;
    TextView description;
    TextView startDate;
    TextView endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);



        eventPicture = findViewById(R.id.eventPicture);


        byte[] byteArray = getIntent().getByteArrayExtra("picture");
        title = findViewById(R.id.eventTitle);
        title.setText(getIntent().getStringExtra("name"));



        eventPictureBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        eventPicture.setImageBitmap(eventPictureBitmap);

        description = findViewById(R.id.eventDescription);

        description.setText(getIntent().getStringExtra("description"));

        startDate = findViewById(R.id.eventDetailsStart);
        endDate = findViewById(R.id.eventDetailsEnd);
        startDate.setText(getIntent().getStringExtra("startDate"));
        endDate.setText(getIntent().getStringExtra("endDate"));


    }
}
