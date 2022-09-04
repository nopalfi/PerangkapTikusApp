package xyz.nopalfi.perangkaptikusapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TikusRecycleViewAdapter extends RecyclerView.Adapter<TikusRecycleViewAdapter.ViewHolder> {

    private static final String TAG = "TikusRecycleViewAdapter";

    List<Tikus> tikus = new ArrayList<>();
    private Context context;

    public TikusRecycleViewAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    public void setTikus(List<Tikus> tikus) {
        this.tikus = tikus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tikus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "TikusRecycleView Called");
        holder.txtId.setText(tikus.get(position).getId().toString());
        holder.txtSensor.setText(tikus.get(position).getSensor());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
        ZonedDateTime zonedDateTime = tikus.get(position).getCreatedAt();
        holder.txtCreatedAt.setText(zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Makassar")).format(formatter));

    }

    @Override
    public int getItemCount() {
        if (tikus == null) {
            return 0;
        } else
        return tikus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtId;
        private TextView txtSensor;
        private TextView txtCreatedAt;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycleView);
            txtId = itemView.findViewById(R.id.txtId);
            txtSensor = itemView.findViewById(R.id.txtSensor);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
        }
    }
}
