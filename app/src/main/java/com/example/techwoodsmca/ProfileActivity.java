package com.example.techwoodsmca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener, View.OnClickListener {

    private TextView textViewUsername,textViewUserEmail;
    private MediaPlayer mMediaPlayer;
    private TextureView mTextureView;
    private TextView timerTextView;
   private Button register;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(this);
        mTextureView = (TextureView) findViewById(R.id.texture_view);
        mTextureView.setSurfaceTextureListener(this);

        // Initialize the DrawerLayout
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // Initialize the RecyclerView for the Navigation Drawer
        mDrawerList = findViewById(R.id.navigation_drawer);

        // Set the adapter for the Navigation Drawer
        mDrawerList.setAdapter(new DrawerAdapter(this));

        // Set the layout manager for the Navigation Drawer
        mDrawerList.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ActionBarDrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);

        // Set the ActionBarDrawerToggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Sync the ActionBarDrawerToggle with the DrawerLayout
        mDrawerToggle.syncState();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
           finish();
           startActivity(new Intent(this,LoginActivity.class));
        }
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);

        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
       switch(item.getItemId()){
           case R.id.menuLogout:
               SharedPrefManager.getInstance(this).logout();
               finish();
               startActivity(new Intent(this,LoginActivity.class));
               break;
       }
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        try {
            mMediaPlayer = new MediaPlayer();
            AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.techwoods);
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mMediaPlayer.setSurface(new Surface(surface));
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return true;

    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

    }


    @Override
    public void onClick(View v) {
        if (v == register)
            startActivity(new Intent(this,Events.class));


    }
}


