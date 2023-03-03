package com.example.techwoodsmca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Eventregister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventregister);
        String eventName = getIntent().getStringExtra("event_name");
        TextView eventNameTextView = findViewById(R.id.eventname);
        eventNameTextView.setText(eventName);

    }
}