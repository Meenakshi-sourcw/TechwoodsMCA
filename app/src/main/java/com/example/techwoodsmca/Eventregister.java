package com.example.techwoodsmca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Eventregister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventregister);

        String eventName = getIntent().getStringExtra("event_name");
        TextView eventNameTextView = findViewById(R.id.eventname);
        ScrollView scrollView = findViewById(R.id.participantsLayout);
        LinearLayout layout = findViewById(R.id.layout);
        eventNameTextView.setText(eventName);

        // Add title TextView
        TextView titleTextView = new TextView(this);
        titleTextView.setText("Enter the team members name");
        layout.addView(titleTextView);

        Intent intent = getIntent();
        int numParticipants = intent.getIntExtra("numParticipants", 0);
        for (int i = 1; i< numParticipants; i++) {
            EditText editText = new EditText(this);
            editText.setId(View.generateViewId()); // Set a unique ID to the EditText
            editText.setHint("Enter player " + (i + 1) + " name");
            layout.addView(editText);
        }




        Button registerButton = new Button(this);
        registerButton.setText("Register");
        registerButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.width = 200; // You can adjust the width as per your requirement
        registerButton.setLayoutParams(layoutParams);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle registration here
            }
        });


        layout.addView(registerButton);

        setContentView(scrollView);
    }
}
