package com.example.absensi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HistoryActivity
        extends AppCompatActivity {

    BottomNavigationView bottomNav;

    ImageView btnBack;

    RecyclerView recyclerHistory;

    TextView tvHadir;
    TextView tvPulang;
    TextView tvIzin;
    TextView tvSakit;
    TextView tvBulan;

    String userId;

    // URL API
    String URL_HISTORY =
            "http://192.168.1.4/absensi/history.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_history
        );

        // INISIALISASI
        bottomNav =
                findViewById(R.id.bottomNav);

        btnBack =
                findViewById(R.id.btnBack);

        recyclerHistory =
                findViewById(R.id.recyclerHistory);

        tvHadir =
                findViewById(R.id.tvHadir);

        tvPulang =
                findViewById(R.id.tvPulang);

        tvIzin =
                findViewById(R.id.tvIzin);

        tvSakit =
                findViewById(R.id.tvSakit);

        tvBulan =
                findViewById(R.id.tvBulan);

        // RECYCLER
        recyclerHistory.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // BULAN SEKARANG
        SimpleDateFormat bulanFormat =
                new SimpleDateFormat(
                        "MMMM yyyy",
                        new Locale("id","ID")
                );

        tvBulan.setText(
                bulanFormat.format(new Date())
        );

        // SESSION LOGIN
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

        // LOAD DATA DATABASE
        loadHistory();

        // BUTTON BACK
        btnBack.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HistoryActivity.this,
                            DashboardActivity.class
                    );

            startActivity(intent);

            overridePendingTransition(0,0);

            finish();

        });

        // NAVBAR ACTIVE
        bottomNav.setSelectedItemId(
                R.id.nav_history
        );

        // NAVIGATION
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            // HOME
            if(id == R.id.nav_home){

                startActivity(
                        new Intent(
                                HistoryActivity.this,
                                DashboardActivity.class
                        )
                );

                finish();

                return true;
            }

            // HISTORY
            else if(id == R.id.nav_history){

                return true;
            }

            // ABSEN
            else if(id == R.id.nav_absen){

                startActivity(
                        new Intent(
                                HistoryActivity.this,
                                AbsenActivity.class
                        )
                );

                finish();

                return true;
            }

            // NOTIFIKASI
            else if(id == R.id.nav_notification){

                startActivity(
                        new Intent(
                                HistoryActivity.this,
                                NotifikasiActivity.class
                        )
                );

                finish();

                return true;
            }

            // AKUN
            else if(id == R.id.nav_account){

                startActivity(
                        new Intent(
                                HistoryActivity.this,
                                AkunActivity.class
                        )
                );

                finish();

                return true;
            }

            return false;
        });

    }

    // =========================
    // LOAD HISTORY DATABASE
    // =========================
    private void loadHistory(){

        StringRequest request =
                new StringRequest(

                        Request.Method.POST,

                        URL_HISTORY,

                        response -> {

                            try {

                                JSONObject object =
                                        new JSONObject(
                                                response
                                        );

                                String status =
                                        object.getString(
                                                "status"
                                        );

                                // SUCCESS
                                if(status.equals("success")){

                                    // SUMMARY
                                    tvHadir.setText(
                                            object.getString(
                                                    "hadir"
                                            ) + " Hari"
                                    );

                                    tvPulang.setText(
                                            object.getString(
                                                    "pulang"
                                            ) + " Kali"
                                    );

                                    tvIzin.setText(
                                            object.getString(
                                                    "izin"
                                            ) + " Hari"
                                    );

                                    tvSakit.setText(
                                            object.getString(
                                                    "sakit"
                                            ) + " Hari"
                                    );

                                    // ARRAY HISTORY
                                    JSONArray array =
                                            object.getJSONArray(
                                                    "data"
                                            );

                                    // ADAPTER
                                    HistoryAdapter adapter =
                                            new HistoryAdapter(
                                                    this,
                                                    array
                                            );

                                    recyclerHistory.setAdapter(
                                            adapter
                                    );

                                }else{

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

        // TIMEOUT
        request.setRetryPolicy(

                new DefaultRetryPolicy(

                        10000,

                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT

                )
        );

        // REQUEST
        RequestQueue queue =
                Volley.newRequestQueue(this);

        queue.add(request);

    }
}