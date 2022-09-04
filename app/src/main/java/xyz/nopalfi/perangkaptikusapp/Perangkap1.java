package xyz.nopalfi.perangkaptikusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Perangkap1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perangkap1);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        TextView txtTikus = findViewById(R.id.txtTikus);
        ArrayList<Tikus> tikus = (ArrayList<Tikus>) getIntent().getSerializableExtra("TikusData");
        Log.d("Debug", tikus.toString());
        txtTikus.setText(String.valueOf("Jumlah: "+tikus.size()));
        TikusRecycleViewAdapter adapter = new TikusRecycleViewAdapter(getApplicationContext());
        adapter.setTikus(tikus);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}