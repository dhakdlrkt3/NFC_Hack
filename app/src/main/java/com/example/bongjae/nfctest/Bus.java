package com.example.bongjae.nfctest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Bus extends AppCompatActivity {

    ImageButton btn_bus1, btn_bus2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_bus1 = (ImageButton)findViewById(R.id.btn_bus1);
        btn_bus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bus.this, LostBus.class));
            }
        });

        btn_bus2 = (ImageButton)findViewById(R.id.btn_bus2);
        btn_bus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bus.this, LostBus2.class));
            }
        });
    }
}
