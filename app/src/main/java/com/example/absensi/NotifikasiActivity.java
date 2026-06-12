package com.example.absensi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotifikasiActivity
        extends AppCompatActivity {

    BottomNavigationView bottomNav;

    RecyclerView recyclerNotif;

    String userId;

    // =========================
    // URL API
    // =========================
    String URL_NOTIF =
            "http://192.168.1.4/absensi/notifikasi.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_notifikasi
        );

        // =========================
        // INISIALISASI
        // =========================
        bottomNav =
                findViewById(R.id.bottomNav);

        recyclerNotif =
                findViewById(R.id.recyclerNotif);

        // =========================
        // RECYCLER VIEW
        // =========================
        recyclerNotif.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerNotif.setHasFixedSize(true);

        // =========================
        // SESSION LOGIN
        // =========================
        SharedPreferences sharedPreferences =
                getSharedPreferences(
                        "LOGIN_SESSION",
                        MODE_PRIVATE
                );

        userId =
                sharedPreferences.getString(
                        "user_id",
                        ""
                );

        // =========================
        // LOAD DATA
        // =========================
        loadNotifikasi();

        // =========================
        // NAVBAR AKTIF
        // =========================
        bottomNav.setSelectedItemId(
                R.id.nav_notification
        );

        // =========================
        // NAVIGATION
        // =========================
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            // HOME
            if(id == R.id.nav_home){

                startActivity(
                        new Intent(
                                NotifikasiActivity.this,
                                DashboardActivity.class
                        )
                );

                overridePendingTransition(0,0);

                finish();

                return true;
            }

            // HISTORY
            else if(id == R.id.nav_history){

                startActivity(
                        new Intent(
                                NotifikasiActivity.this,
                                HistoryActivity.class
                        )
                );

                overridePendingTransition(0,0);

                finish();

                return true;
            }

            // ABSEN
            else if(id == R.id.nav_absen){

                startActivity(
                        new Intent(
                                NotifikasiActivity.this,
                                AbsenActivity.class
                        )
                );

                overridePendingTransition(0,0);

                finish();

                return true;
            }

            // NOTIFIKASI
            else if(id == R.id.nav_notification){

                return true;
            }

            // AKUN
            else if(id == R.id.nav_account){

                startActivity(
                        new Intent(
                                NotifikasiActivity.this,
                                AkunActivity.class
                        )
                );

                overridePendingTransition(0,0);

                finish();

                return true;
            }

            return false;
        });

    }

    // =========================
    // REFRESH OTOMATIS
    // =========================
    @Override
    protected void onResume() {
        super.onResume();

        loadNotifikasi();
    }

    // =========================
    // LOAD NOTIFIKASI
    // =========================
    private void loadNotifikasi(){

        StringRequest request =
                new StringRequest(

                        Request.Method.POST,

                        URL_NOTIF,

                        response -> {

                            try {

                                JSONObject object =
                                        new JSONObject(response);

                                String status =
                                        object.getString("status");

                                // =========================
                                // SUCCESS
                                // =========================
                                if(status.equals("success")){

                                    JSONArray array =
                                            object.getJSONArray("data");

                                    // =========================
                                    // ADA DATA
                                    // =========================
                                    if(array.length() > 0){

                                        // RESET ADAPTER
                                        recyclerNotif.setAdapter(null);

                                        // ADAPTER
                                        NotifikasiAdapter adapter =
                                                new NotifikasiAdapter(
                                                        this,
                                                        array
                                                );

                                        recyclerNotif.setAdapter(
                                                adapter
                                        );

                                    }

                                    // =========================
                                    // DATA KOSONG
                                    // =========================
                                    else{

                                        recyclerNotif.setAdapter(null);

                                        Toast.makeText(

                                                this,

                                                "Belum ada notifikasi",

                                                Toast.LENGTH_SHORT

                                        ).show();
                                    }

                                }

                                // =========================
                                // ERROR
                                // =========================
                                else{

                                    Toast.makeText(

                                            this,

                                            object.getString(
                                                    "message"
                                            ),

                                            Toast.LENGTH_LONG

                                    ).show();

                                }

                            }catch (Exception e){

                                Toast.makeText(

                                        this,

                                        "JSON ERROR : "
                                                + e.toString(),

                                        Toast.LENGTH_LONG

                                ).show();

                            }

                        },

                        error -> {

                            Toast.makeText(

                                    this,

                                    "VOLLEY ERROR : "
                                            + error.toString(),

                                    Toast.LENGTH_LONG

                            ).show();

                        }

                ){

                    @Override
                    protected Map<String, String> getParams(){

                        Map<String, String> params =
                                new HashMap<>();

                        params.put(
                                "user_id",
                                userId
                        );

                        return params;
                    }
                };

        // =========================
        // TIMEOUT
        // =========================
        request.setRetryPolicy(

                new DefaultRetryPolicy(

                        10000,

                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT

                )
        );

        // =========================
        // REQUEST QUEUE
        // =========================
        RequestQueue queue =
                Volley.newRequestQueue(this);

        queue.add(request);

    }
}