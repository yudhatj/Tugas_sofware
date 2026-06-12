package com.example.absensi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DashboardActivity
        extends AppCompatActivity {

    // NAVBAR
    BottomNavigationView bottomNav;

    // BUTTON
    LinearLayout btnAbsen;
    LinearLayout btnPengajuan;

    // TEXTVIEW
    TextView tvNama;
    TextView tvJam;
    TextView tvTanggal;

    TextView tvHadir;
    TextView tvPulang;
    TextView tvIzin;
    TextView tvSakit;

    // HANDLER JAM
    Handler handler =
            new Handler();

    Runnable runnable;

    // USER LOGIN
    String userId;

    // URL API
    String URL_DASHBOARD =
            "http://192.168.1.4/absensi/dashboard.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_dashboard
        );

        // =========================
        // INISIALISASI
        // =========================
        bottomNav =
                findViewById(R.id.bottomNav);

        btnAbsen =
                findViewById(R.id.btnAbsen);

        btnPengajuan =
                findViewById(R.id.btnPengajuan);

        tvNama =
                findViewById(R.id.tvNama);

        tvJam =
                findViewById(R.id.tvJam);

        tvTanggal =
                findViewById(R.id.tvTanggal);

        tvHadir =
                findViewById(R.id.tvHadir);

        tvPulang =
                findViewById(R.id.tvPulang);

        tvIzin =
                findViewById(R.id.tvIzin);

        tvSakit =
                findViewById(R.id.tvSakit);

        // =========================
        // VALIDASI XML
        // =========================
        if (
                tvNama == null ||
                        tvJam == null ||
                        tvTanggal == null ||
                        tvHadir == null ||
                        tvPulang == null ||
                        tvIzin == null ||
                        tvSakit == null
        ) {

            Toast.makeText(
                    this,
                    "ID XML tidak ditemukan",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

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


        String nama =
                sharedPreferences.getString(
                        "nama",
                        "User"
                );

        // VALIDASI LOGIN
        if (userId.isEmpty()) {

            Toast.makeText(
                    this,
                    "Session login tidak ditemukan",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // SET NAMA
        tvNama.setText(
                "Halo, " + nama + " 👋"
        );

        // =========================
        // LOAD DASHBOARD
        // =========================
        loadDashboard();

        // =========================
        // REALTIME JAM
        // =========================
        realtimeJam();

        // =========================
        // MENU AKTIF
        // =========================
        bottomNav.setSelectedItemId(
                R.id.nav_home
        );

        // =========================
        // BUTTON ABSEN
        // =========================
        btnAbsen.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DashboardActivity.this,
                            AbsenActivity.class
                    );

            startActivity(intent);

        });

        // =========================
        // BUTTON PENGAJUAN
        // =========================
        btnPengajuan.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DashboardActivity.this,
                            PengajuanIzinActivity.class
                    );

            startActivity(intent);

        });

        // =========================
        // BOTTOM NAVIGATION
        // =========================
        bottomNav.setOnItemSelectedListener(item -> {

            int id =
                    item.getItemId();

            // HOME
            if (id == R.id.nav_home) {

                return true;
            }

            // HISTORY
            else if (id == R.id.nav_history) {

                startActivity(
                        new Intent(
                                DashboardActivity.this,
                                HistoryActivity.class
                        )
                );

                finish();

                return true;
            }

            // ABSEN
            else if (id == R.id.nav_absen) {

                startActivity(
                        new Intent(
                                DashboardActivity.this,
                                AbsenActivity.class
                        )
                );

                finish();

                return true;
            }

            // NOTIFIKASI
            else if (id == R.id.nav_notification) {

                startActivity(
                        new Intent(
                                DashboardActivity.this,
                                NotifikasiActivity.class
                        )
                );

                finish();

                return true;
            }

            // AKUN
            else if (id == R.id.nav_account) {

                startActivity(
                        new Intent(
                                DashboardActivity.this,
                                AkunActivity.class
                        )
                );

                finish();

                return true;
            }

            return false;
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadDashboard();
    }
    // LOAD DASHBOARD DATABASE
// =========================
    private void loadDashboard() {

        StringRequest request =
                new StringRequest(

                        Request.Method.POST,

                        URL_DASHBOARD,

                        response -> {

                            try {

                                JSONObject object =
                                        new JSONObject(response);

                                String status =
                                        object.getString("status");

                                // SUCCESS
                                if (status.equals("success")) {

                                    // CHECK IN
                                    String checkin =
                                            object.optString(
                                                    "checkin",
                                                    "Belum Absen"
                                            );

                                    // CHECK OUT
                                    String checkout =
                                            object.optString(
                                                    "checkout",
                                                    "Belum Absen"
                                            );


                                    // IZIN
                                    String izin =
                                            object.optString(
                                                    "izin",
                                                    "0"
                                            );

                                    // SAKIT
                                    String sakit =
                                            object.optString(
                                                    "sakit",
                                                    "0"
                                            );
                                    if(checkin.equals("Belum Absen")){


                                        tvHadir.setText(
                                                "Belum Absen"
                                        );

                                    }else{

                                        tvHadir.setText(
                                                "Sudah Absen"
                                        );
                                    }

                                    if(checkout.equals("Belum Absen")){

                                        tvPulang.setText(
                                                "Belum Absen"
                                        );

                                    }else{

                                        tvPulang.setText(
                                                "Sudah Pulang"
                                        );
                                    }

                                    tvIzin.setText(
                                            izin + " Kali"
                                    );

                                    tvSakit.setText(
                                            sakit + " Kali"
                                    );

                                } else {

                                    Toast.makeText(
                                            this,
                                            object.getString(
                                                    "message"
                                            ),
                                            Toast.LENGTH_LONG
                                    ).show();

                                }

                            } catch (Exception e) {

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

                ) {

                    @Override
                    protected Map<String, String> getParams() {

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

    private void realtimeJam() {

        runnable = new Runnable() {

            @Override
            public void run() {

                Date date =
                        new Date();

                SimpleDateFormat jamFormat =
                        new SimpleDateFormat(
                                "HH:mm:ss",
                                new Locale("id", "ID")
                        );

                SimpleDateFormat tanggalFormat =
                        new SimpleDateFormat(
                                "EEEE, dd MMMM yyyy",
                                new Locale("id", "ID")
                        );

                String tanggal =
                        tanggalFormat.format(date);

                tanggal =
                        tanggal.substring(0, 1).toUpperCase()
                                + tanggal.substring(1);

                tvJam.setText(
                        jamFormat.format(date)
                );

                tvTanggal.setText(
                        tanggal
                );

                handler.postDelayed(
                        this,
                        1000
                );
            }
        };

        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (handler != null &&
                runnable != null) {

            handler.removeCallbacks(
                    runnable
            );
        }
    }
}