package com.example.bongjae.nfctest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Taxi extends AppCompatActivity {

    ImageButton btn_taxi1, btn_taxi2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_taxi1 = (ImageButton)findViewById(R.id.btn_taxi1);
        btn_taxi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Taxi.this, LostTaxi.class));
            }
        });

        btn_taxi2 = (ImageButton)findViewById(R.id.btn_taxi2);
        btn_taxi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Taxi.this, LostTaxi2.class));
            }
        });
    }
}
