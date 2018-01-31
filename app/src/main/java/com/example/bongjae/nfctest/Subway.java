package com.example.bongjae.nfctest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Subway extends AppCompatActivity {

    ImageButton btn_subway1, btn_subway2, btn_subway3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_subway1 = (ImageButton)findViewById(R.id.btn_subway1);
        btn_subway1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Subway.this, LostSubway.class));
            }
        });

        btn_subway2 = (ImageButton)findViewById(R.id.btn_subway2);
        btn_subway2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Subway.this, LostSubway2.class));
            }
        });

        btn_subway3 = (ImageButton)findViewById(R.id.btn_subway3);
        btn_subway3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Subway.this, LostSubway3.class));
            }
        });
    }
}