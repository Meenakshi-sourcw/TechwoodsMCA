package com.example.techwoodsmca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Events extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<Listitem> listitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listitems = new ArrayList<>();
        adapter = new MyAdapter(listitems, getApplicationContext());
        recyclerView.setAdapter(adapter);

        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_PART, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonobject = new JSONObject(s);
                    JSONArray array = jsonobject.getJSONArray("events");

                    for(int i=0;i< array.length();i++){
                        JSONObject o=array.getJSONObject(i);
                        Listitem item = new Listitem(
                                o.getString("event_name"),
                                o.getString("event_description"),
                                o.getString("no_of_participants"),
                                o.getString("event_date"),
                                o.getString("event_venue"),
                                o.getString("event_category"),
                                o.getString("event_organizer")
                        );
                        listitems.add(item);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
