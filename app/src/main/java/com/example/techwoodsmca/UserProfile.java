package com.example.techwoodsmca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    TextView username,profile_email,editbio,editlocation,editphone, nameview;
    Button editprofile;
    private Context context;
    private String email,contactNumber,city,bio;
    Integer userId;
    public EditText emailEditText,contactNumberEditText,cityEditText,bioEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username = (TextView) findViewById(R.id.profile_name);
        profile_email = (TextView) findViewById(R.id.profile_email);
        editprofile = (Button) findViewById(R.id.edit_profile_button);
        editbio=(TextView) findViewById(R.id.profile_bio);
        editlocation=(TextView)findViewById(R.id.viewlocation);
        editphone=(TextView)findViewById(R.id.viewcontact);


        username.setText(SharedPrefManager.getInstance(this).getUsername());

        userId=SharedPrefManager.getInstance(this).getUserId();
        String url = "http://10.0.0.213/Android/v1/get_user_details.php?user_id=" + userId;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract the user's details from the response JSON
                               // String existUsername = response.getString("username");
                            String existEmail = response.getString("email");
                            String existContactNumber = response.getString("contact_number");
                            String existCity = response.getString("city");
                            String existBio=response.getString("bio");

                            profile_email.setText(existEmail);
                            editbio.setText(existBio);
                            editlocation.setText(existCity);
                            editphone.setText(existContactNumber);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserProfile.this, "Failed to retrieve user details", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(UserProfile.this, "Failed to retrieve user details", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);




        editprofile.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserProfile.this);
                dialog.setContentView(R.layout.edit_popup);
                dialog.setTitle("Edit Profile");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

// Get the CardView from the layout and set its elevation
                CardView cardView = dialog.findViewById(R.id.popup_card);
                cardView.setCardElevation(16f);

                dialog.show();

// Find the edit text fields and buttons in the dialog

               emailEditText = dialog.findViewById(R.id.edit_email);
                 contactNumberEditText = dialog.findViewById(R.id.edit_contact_number);
                 cityEditText = dialog.findViewById(R.id.edit_location);
                 bioEditText =dialog.findViewById(R.id.edit_bio);
                nameview=(TextView)findViewById(R.id.usernameview);
                Button cancelButton = dialog.findViewById(R.id.btn_cancel);
                Button saveButton = dialog.findViewById(R.id.btn_save);

                emailEditText.setText((SharedPrefManager.getInstance(UserProfile.this).getUserEmail()));
                getUserDetails();
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieve the values from the EditTexts
                        email = emailEditText.getText().toString().trim();
                        bio=bioEditText.getText().toString().trim();
                        contactNumber = contactNumberEditText.getText().toString().trim();
                         city = cityEditText.getText().toString().trim();


                        // Validate that all required fields are filled in
                        if (email.isEmpty() || contactNumber.isEmpty() || city.isEmpty()) {
                            Toast.makeText(UserProfile.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            // Update the user's details on the server
                            updateUserDetails();

                        }

                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss(); // Close the EditProfileActivity
                    }
                });
            }
        });

    }
    public void getUserDetails() {
        userId=SharedPrefManager.getInstance(this).getUserId();
        String url = "http://10.0.0.213/Android/v1/get_user_details.php?user_id=" + userId;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract the user's details from the response JSON
                                String existingUsername = response.getString("username");
                            String existingEmail = response.getString("email");
                            String existingContactNumber = response.getString("contact_number");
                            String existingCity = response.getString("city");
                            String existingBio=response.getString("bio");



                            // Pre-populate the EditTexts with the user's existing details
//                            profile_email.setText(existingEmail);
                
                            emailEditText.setText(existingEmail);
                            contactNumberEditText.setText(existingContactNumber);
                            cityEditText.setText(existingCity);
                            bioEditText.setText(existingBio);
//                            editlocation.setText(existingCity);
//                            editphone.setText(existingContactNumber);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserProfile.this, "Failed to retrieve user details", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(UserProfile.this, "Failed to retrieve user details", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    private void updateUserDetails() {
        userId=SharedPrefManager.getInstance(this).getUserId();
        RequestQueue queue = Volley.newRequestQueue(this);
        // Create a string request using POST method
        String url = "http://10.0.0.213/Android/v1/update_user_details.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject Response = new JSONObject(response);
                            // Check if the update was successful
                            if (Response.getString("status").equals("success")) {
                                Toast.makeText(UserProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Close the EditProfileActivity
                            } else {
                                Toast.makeText(UserProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(UserProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();}

        }){@Override
        public String getBodyContentType() {
            // as we are passing data in the form of url encoded
            // so we are passing the content type below
            return "application/x-www-form-urlencoded; charset=UTF-8";
        }
            // Define the parameters to be passed to the PHP script
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("user_id", userId.toString());
                params.put("contact", contactNumber);
                params.put("email", email);
                params.put("city", city);
                params.put("bio", bio);

                return params;
            }
        };
        Toast.makeText(UserProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }

}






