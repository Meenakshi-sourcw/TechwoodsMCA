package com.example.techwoodsmca;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername,editTextEmail,editTextPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    public ProgressDialog progressDialog;
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._-]{3,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }
        editTextEmail =(EditText) findViewById(R.id.editTextEmail);
        editTextUsername =(EditText) findViewById(R.id.editTextUsername);
        editTextPassword =(EditText) findViewById(R.id.editTextPassword);
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        buttonRegister =(Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }
 private void registerUser(){
  final String email=editTextEmail.getText().toString().trim();
  final String username = editTextUsername.getText().toString().trim();
  final String password = editTextPassword.getText().toString().trim();

     // Validate username
     if (!username.matches(USERNAME_REGEX)) {
         editTextUsername.setError("Username must be at least 3 characters and can only contain letters, numbers, dots, underscores, and hyphens");
         editTextUsername.requestFocus();
         return;
     }

// Validate password
     if (!password.matches(PASSWORD_REGEX)) {
         editTextPassword.setError("Password must be at least 8 characters and contain at least one letter and one number");
         editTextPassword.requestFocus();
         return;
     }

     // Validate email
     if (!email.matches(EMAIL_REGEX)) {
         editTextEmail.setError("Invalid email address");
         editTextEmail.requestFocus();
         return;
     }

     progressDialog = new ProgressDialog(this);
  progressDialog.setMessage("Registering user.......");
  progressDialog.show();
  StringRequest stringRequest = new StringRequest(Request.Method.POST,
             Constants.URL_REGISTER,
             new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                     progressDialog.dismiss();

                    try{
                     JSONObject jsonObject = new JSONObject(response);
                       Toast.makeText(getApplicationContext(),jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                 }catch(JSONException e){
                    e.printStackTrace();
                    }
                 }

             },
          new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
           progressDialog.hide();
             Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
         }
     }){
         @Override
         protected Map<String, String> getParams() throws AuthFailureError {
             Map<String,String> params = new HashMap<>();
             params.put("username",username);
             params.put("email",email);
             params.put("password",password);
             return params;
         }
     };

     RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


 }
    @Override
    public void onClick(View v) {

        if (v == buttonRegister)
            registerUser();
        if(v == textViewLogin)
            startActivity(new Intent(this,LoginActivity.class));

    }
}