package com.example.techwoodsmca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    TextView username,email,location,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username=(TextView)findViewById(R.id.profile_name);
        email=(TextView)findViewById(R.id.profile_email);
        email.setText(SharedPrefManager.getInstance(this).getUserEmail());
        username.setText(SharedPrefManager.getInstance(this).getUsername());
    }
}