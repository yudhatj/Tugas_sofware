package com.example.absensi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

public class HistoryAdapter
        extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;

    JSONArray array;

    // CONSTRUCTOR
    public HistoryAdapter(
            Context context,
            JSONArray array
    ){

        this.context = context;

        this.array = array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.item_history,
                                parent,
                                false
                        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        try {

            JSONObject object =
                    array.getJSONObject(position);

            String tanggal =
                    object.getString("tanggal");

            String jam =
                    object.getString("jam");

            String type =
                    object.getString("type");

            String lokasi =
                    object.getString("lokasi");

            // SET DATA
            holder.tvTanggal.setText(
                    tanggal
            );

            holder.tvJam.setText(
                    jam
            );

            holder.tvType.setText(
                    type
            );

            holder.tvLokasi.setText(
                    lokasi
            );

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return array.length();
    }

    // VIEW HOLDER
    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvTanggal;
        TextView tvJam;
        TextView tvType;
        TextView tvLokasi;

        public ViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            tvTanggal =
                    itemView.findViewById(
                            R.id.tvTanggal
                    );

            tvJam =
                    itemView.findViewById(
                            R.id.tvJam
                    );

            tvType =
                    itemView.findViewById(
                            R.id.tvType
                    );

            tvLokasi =
                    itemView.findViewById(
                            R.id.tvLokasi
                    );

        }
    }
}