package com.example.techwoodsmca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Singleparticipant extends AppCompatActivity {

    public EditText firstname, lastname, email, contact, college_name, regno;
    public DatePicker dob;

    public LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleparticipant);
        firstname = findViewById(R.id.first_name_edittext);
        lastname = findViewById(R.id.last_name_edittext);
        email = findViewById(R.id.email_edittext);
        contact = findViewById(R.id.contact_number_edittext);
        dob = findViewById(R.id.date_picker);



        college_name = findViewById(R.id.college_name_edittext);
        regno = findViewById(R.id.register_no_edittext);

        String eventName = getIntent().getStringExtra("event_name");
        TextView eventNameTextView = findViewById(R.id.eventname);
        ScrollView scrollView = findViewById(R.id.participantsLayout);
        layout = findViewById(R.id.layout);
        eventNameTextView.setText(eventName);
        Intent intent = getIntent();
        layout.setOrientation(LinearLayout.VERTICAL);


        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });






        Button registerButton = new Button(this);
        registerButton.setText("Register");



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event_name = eventNameTextView.getText().toString();
                String first_name = firstname.getText().toString();
                String last_name = lastname.getText().toString();
                String email_id = email.getText().toString();
                String contact_no = contact.getText().toString();
                String date = dob.getYear() + "-" + (dob.getMonth() + 1) + "-" + dob.getDayOfMonth();
                String col = college_name.getText().toString();
                String reg = regno.getText().toString();
                if (TextUtils.isEmpty(first_name)) {
                    firstname.setError("Please enter your first name");
                } else if (!first_name.matches("^[a-zA-Z]+$")) {
                    firstname.setError("Please enter a valid first name");
                }

                else if (TextUtils.isEmpty(last_name)) {
                    lastname.setError("Please enter your last name");
                } else if (!last_name.matches("^[a-zA-Z]+$")) {
                    lastname.setError("Please enter a valid last name");
                }



                else if (TextUtils.isEmpty(email_id)) {
                    email.setError("Please enter your email id");
                } else if (!email_id.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    email.setError("Please enter a valid email id");
                }

                else if (TextUtils.isEmpty(contact_no)) {
                    contact.setError("Please enter your contact number");
                } else if (!contact_no.matches("[0-9]+") || contact_no.length() != 10) {
                    contact.setError("Please enter a valid contact number");
                }

                else if (TextUtils.isEmpty(col)) {
                    college_name.setError("Please enter your college  name");
                } else if (!col.matches("^[a-zA-Z ]+$")) {
                    college_name.setError("Please enter a valid college name");
                }



                else if (TextUtils.isEmpty(reg)) {
                    regno.setError("Please enter your register number");
                } else if (!reg.matches("[0-9]+")) {
                    regno.setError("Please enter a valid register number");
                }
                else{
                    registerParticipant(event_name, first_name, last_name, email_id, contact_no, date, col, reg);

                }







            }
        });
        layout.addView(registerButton);
    }








    private void registerParticipant(String event_name, String first_name, String last_name,String email_id,String contact_no,String date,String col,String reg ) {


        // Set the URL of your PHP script
        String url = Constants.URL_INT;

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);



        // Create a string request using POST method
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response from PHP script
                        Log.e("Register", "Response: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // on below line we are displaying a success toast message.
                            Toast.makeText(Singleparticipant.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(Singleparticipant.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            // Define the parameters to be passed to the PHP script
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("event_name", event_name);
                params.put("firstname", first_name);
                params.put("lastname", last_name);
                params.put("email", email_id);
                params.put("contact", contact_no);
                params.put("dob", date);
                params.put("college_name", col);
                params.put("regno", reg);

                return params;

            }
        };
        Toast.makeText(Singleparticipant.this,"Registered Sucessfully",Toast.LENGTH_LONG).show();

        // Add the request to the RequestQueue
        queue.add(stringRequest);

    }


}
