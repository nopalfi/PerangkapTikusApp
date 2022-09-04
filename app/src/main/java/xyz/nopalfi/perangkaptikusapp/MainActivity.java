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

    final String URL = "https://perangkap-tikus.herokuapp.com/api/v1/tikus/";
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
        new FetchData().start();

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
    class FetchData extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    jsonString = line;
                }
                if (!jsonString.isEmpty()) {

                    try {
                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Long id = jsonObject.getLong("id");
                            String sensor = jsonObject.getString("sensor");
                            String createdAt = jsonObject.getString("createdAt");
                            ZonedDateTime createdAtZoned = ZonedDateTime.parse(createdAt);
                            Tikus addTikus = new Tikus(id, sensor, createdAtZoned);
                            tikus.add(addTikus);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}