package xyz.nopalfi.perangkaptikusapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    Button btn3;

    String url = "https://perangkap-tikus.herokuapp.com/api/v1/tikus/";
    String jsonString = "";
    ArrayList<Tikus> tikus = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i<response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        Long id = object.getLong("id");
                        String sensor = object.getString("sensor");
                        String createdAt = object.getString("createdAt");
                        ZonedDateTime zonedDateTime = ZonedDateTime.parse(createdAt);
                        tikus.add(new Tikus(id, sensor, zonedDateTime));
                    }
                    System.out.println(tikus.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonArrayRequest);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        btn1.setOnClickListener(view -> {
            if (tikus.size() < 1) {
                Toast.makeText(this, "Data Belum Terkumpul", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("Debug", tikus.toString());
                Intent perangkap1 = new Intent(MainActivity.this, Perangkap1.class);
                perangkap1.putExtra("TikusData", tikus);
                startActivity(perangkap1);
            }
        });
        btn2.setOnClickListener(view -> {
            Toast.makeText(this, "Fitur Belum Ready!", Toast.LENGTH_SHORT).show();
        });

        btn3.setOnClickListener(view -> {
            Toast.makeText(this, "Fitur Belum Ready!", Toast.LENGTH_SHORT).show();
        });
    }

}