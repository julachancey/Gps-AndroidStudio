package com.example.gpsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Bundle extras=getIntent().getExtras();
        if(extras!=null) {

            ImageView frame = findViewById(R.id.photo_frame);
            frame.setImageResource(extras.getInt("pictureID"));

        }
    }

    public void onClick(View view){
        finish();
    }
}