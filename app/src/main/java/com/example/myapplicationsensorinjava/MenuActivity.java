package com.example.myapplicationsensorinjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.ComponentActivity;

public class MenuActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button buttonMotionEvent = findViewById(R.id.button_motion_event);
        Button buttonSensors = findViewById(R.id.button_sensors);

        buttonMotionEvent.setOnClickListener(v -> {
            Intent intent = new Intent(this, MotionEventActivity.class);
            startActivity(intent);
        });

        buttonSensors.setOnClickListener(v -> {
            Intent intent = new Intent(this, SensorActivity.class);
            startActivity(intent);
        });
    }
}