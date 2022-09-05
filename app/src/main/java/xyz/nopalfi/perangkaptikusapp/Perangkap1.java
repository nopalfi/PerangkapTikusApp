package xyz.nopalfi.perangkaptikusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Perangkap1 extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    String url = "https://perangkap-tikus.herokuapp.com/api/v1/tikus/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perangkap1);
        ArrayList<Tikus> tikus = (ArrayList<Tikus>) getIntent().getSerializableExtra("TikusData");
        TikusRecycleViewAdapter adapter = new TikusRecycleViewAdapter(getApplicationContext());
        RequestQueue queue = Volley.newRequestQueue(this);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        TextView txtTikus = findViewById(R.id.txtTikus);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            tikus.clear();
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
                            txtTikus.setText(String.valueOf("Jumlah: "+tikus.size()));
                            adapter.setTikus(tikus);
                            recyclerView.setAdapter(adapter);
                        }
                        Toast.makeText(Perangkap1.this, "Refreshed...", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Perangkap1.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(jsonArrayRequest);
        });

        Log.d("Debug", tikus.toString());
        txtTikus.setText(String.valueOf("Jumlah: "+tikus.size()));
        adapter.setTikus(tikus);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}