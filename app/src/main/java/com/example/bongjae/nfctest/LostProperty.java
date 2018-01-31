package com.example.bongjae.nfctest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class LostProperty extends AppCompatActivity {

    Button bus, subway, taxi, public_institution;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_property);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bus = (Button)findViewById(R.id.bus);
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LostProperty.this, Bus.class));
            }
        });

        subway = (Button)findViewById(R.id.subway);
        subway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LostProperty.this, Subway.class));
            }
        });

        taxi = (Button)findViewById(R.id.taxi);
        taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LostProperty.this, Taxi.class));
            }
        });

        public_institution = (Button)findViewById(R.id.public_institution);
        public_institution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LostProperty.this, Public_institution.class));
            }
        });
    }
}
