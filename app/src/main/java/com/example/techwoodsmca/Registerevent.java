package com.example.techwoodsmca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.view.MotionEvent;


public class Registerevent extends AppCompatActivity{
 public CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        cardView = findViewById(R.id.cardview1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Registerevent.this);
                builder.setTitle("My Popup Title");
                builder.setMessage("My Popup Message");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle OK button click
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle Cancel button click
                    }
                });

                // Display the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        cardView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        cardView.setCardBackgroundColor(Color.parseColor("#E6E6E6")); //set the color to a light gray
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF")); //set the color back to white
                        break;
                }
                return true;
            }
        });
    }


}
