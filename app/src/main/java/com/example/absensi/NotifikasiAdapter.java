package com.example.absensi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotifikasiAdapter
        extends RecyclerView.Adapter<NotifikasiAdapter.ViewHolder> {

    Context context;

    JSONArray array;

    // URL API
    String URL_HAPUS =
            "http://192.168.88.221/absensi/hapus_notifikasi.php";

    // CONSTRUCTOR
    public NotifikasiAdapter(
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
                                R.layout.item_notifikasi,
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

            // =========================
            // DATA
            // =========================
            String id =
                    object.getString("id");

            String title =
                    object.getString("title");

            String message =
                    object.getString("message");

            String time =
                    object.getString("time");

            String type =
                    object.getString("type");

            // =========================
            // SET TEXT
            // =========================
            holder.tvTitle.setText(title);

            holder.tvMessage.setText(message);

            holder.tvDate.setText(time);

            // =========================
            // ICON
            // =========================
            if(type.equals("checkin")){

                holder.imgNotif.setImageResource(
                        R.drawable.badge_check
                );

            }else if(type.equals("checkout")){

                holder.imgNotif.setImageResource(
                        R.drawable.log
                );

            }else if(type.equals("izin")){

                holder.imgNotif.setImageResource(
                        R.drawable.clipboard_list
                );

            }else{

                holder.imgNotif.setImageResource(
                        R.drawable.bell
                );
            }

            // =========================
            // MENU TITIK TIGA
            // =========================
            holder.btnMenu.setOnClickListener(v -> {

                PopupMenu popupMenu =
                        new PopupMenu(
                                context,
                                holder.btnMenu
                        );

                popupMenu.getMenu().add(
                        "Hapus"
                );

                popupMenu.setOnMenuItemClickListener(item -> {

                    hapusNotif(
                            id,
                            position
                    );

                    return true;
                });

                popupMenu.show();

            });

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return array.length();
    }

    // =========================
    // HAPUS NOTIFIKASI
    // =========================
    private void hapusNotif(
            String id,
            int position
    ){

        StringRequest request =
                new StringRequest(

                        Request.Method.POST,

                        URL_HAPUS,

                        response -> {

                            try {

                                JSONObject object =
                                        new JSONObject(response);

                                String status =
                                        object.getString("status");

                                String message =
                                        object.getString("message");

                                if(status.equals("success")){

                                    // HAPUS DARI ARRAY
                                    JSONArray newArray =
                                            new JSONArray();

                                    for(int i = 0;
                                        i < array.length();
                                        i++){

                                        if(i != position){

                                            newArray.put(
                                                    array.getJSONObject(i)
                                            );
                                        }
                                    }

                                    array = newArray;

                                    notifyDataSetChanged();

                                    Toast.makeText(

                                            context,

                                            message,

                                            Toast.LENGTH_SHORT

                                    ).show();

                                }else{

                                    Toast.makeText(

                                            context,

                                            message,

                                            Toast.LENGTH_LONG

                                    ).show();
                                }

                            }catch (Exception e){

                                Toast.makeText(

                                        context,

                                        e.toString(),

                                        Toast.LENGTH_LONG

                                ).show();
                            }

                        },

                        error -> Toast.makeText(

                                context,

                                error.toString(),

                                Toast.LENGTH_LONG

                        ).show()

                ){

                    @Override
                    protected Map<String, String> getParams(){

                        Map<String, String> params =
                                new HashMap<>();

                        params.put(
                                "id",
                                id
                        );

                        return params;
                    }
                };

        // TIMEOUT
        request.setRetryPolicy(

                new DefaultRetryPolicy(

                        10000,

                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT

                )
        );

        // QUEUE
        RequestQueue queue =
                Volley.newRequestQueue(context);

        queue.add(request);

    }

    // =========================
    // VIEW HOLDER
    // =========================
    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        ImageView imgNotif;
        ImageView btnMenu;

        TextView tvTitle;
        TextView tvMessage;
        TextView tvDate;

        public ViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            imgNotif =
                    itemView.findViewById(
                            R.id.imgNotif
                    );

            btnMenu =
                    itemView.findViewById(
                            R.id.btnMenu
                    );

            tvTitle =
                    itemView.findViewById(
                            R.id.tvTitle
                    );

            tvMessage =
                    itemView.findViewById(
                            R.id.tvMessage
                    );

            tvDate =
                    itemView.findViewById(
                            R.id.tvDate
                    );
        }
    }
}