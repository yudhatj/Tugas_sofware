package com.example.absensi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SuccessAbsenActivity
        extends AppCompatActivity {

    // TEXTVIEW
    TextView tvTitle;

    TextView tvTanggal;
    TextView tvJam;

    TextView tvNama;
    TextView tvJabatan;
    TextView tvLokasi;
    TextView tvKeterangan;

    // BUTTON
    LinearLayout btnSelesai;

    // URL API
    String URL_GET =
            "http://192.168.1.4/absensi/get_absen.php";

    // USER LOGIN
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_success_absen);

        // EDGE TO EDGE
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });

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

        // VALIDASI LOGIN
        if(userId.isEmpty()){

            Toast.makeText(
                    this,
                    "Session login hilang",
                    Toast.LENGTH_LONG
            ).show();

            finish();

            return;
        }

        // INISIALISASI
        tvTitle =
                findViewById(R.id.tvTitle);

        tvTanggal =
                findViewById(R.id.tvTanggal);

        tvJam =
                findViewById(R.id.tvJam);

        tvNama =
                findViewById(R.id.tvNama);

        tvJabatan =
                findViewById(R.id.tvJabatan);

        tvLokasi =
                findViewById(R.id.tvLokasi);

        tvKeterangan =
                findViewById(R.id.tvKeterangan);

        btnSelesai =
                findViewById(R.id.btnSelesai);

        // LOAD DATA
        loadData();

        // BUTTON
        btnSelesai.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            SuccessAbsenActivity.this,
                            DashboardActivity.class
                    );

            startActivity(intent);

            overridePendingTransition(0,0);

            finish();

        });
    }

    // LOAD DATA DATABASE
    private void loadData(){

        StringRequest request =
                new StringRequest(

                        Request.Method.POST,

                        URL_GET,

                        response -> {

                            try {

                                // VALIDASI RESPONSE
                                if(response == null
                                        || response.isEmpty()){

                                    Toast.makeText(
                                            this,
                                            "Response kosong",
                                            Toast.LENGTH_LONG
                                    ).show();

                                    return;
                                }

                                JSONObject object =
                                        new JSONObject(
                                                response.trim()
                                        );

                                String status =
                                        object.getString("status");

                                // SUCCESS
                                if(status.equals("success")){

                                    // DATA
                                    String nama =
                                            object.optString(
                                                    "nama",
                                                    "-"
                                            );

                                    String jabatan =
                                            object.optString(
                                                    "position",
                                                    "Karyawan"
                                            );

                                    String lokasi =
                                            object.optString(
                                                    "lokasi",
                                                    "-"
                                            );

                                    String tanggal =
                                            object.optString(
                                                    "tanggal",
                                                    "-"
                                            );

                                    String jam =
                                            object.optString(
                                                    "jam",
                                                    "-"
                                            );

                                    String keterangan =
                                            object.optString(
                                                    "keterangan",
                                                    "-"
                                            );

                                    // JABATAN NULL
                                    if(
                                            jabatan.equals("null")
                                                    || jabatan.isEmpty()
                                    ){

                                        jabatan = "Karyawan";
                                    }

                                    // LOKASI NULL
                                    if(
                                            lokasi.equals("null")
                                                    || lokasi.isEmpty()
                                    ){

                                        lokasi =
                                                "Lokasi tidak tersedia";
                                    }

                                    // STATUS ABSEN
                                    if(
                                            keterangan.equalsIgnoreCase(
                                                    "Check In"
                                            )
                                    ){

                                        tvTitle.setText(
                                                "Berhasil Check In!"
                                        );

                                        tvKeterangan.setText(
                                                "🟢 Masuk"
                                        );

                                        tvKeterangan.setTextColor(
                                                Color.parseColor("#22C55E")
                                        );

                                    }

                                    else if(
                                            keterangan.equalsIgnoreCase(
                                                    "Check Out"
                                            )
                                    ){

                                        tvTitle.setText(
                                                "Berhasil Check Out!"
                                        );

                                        tvKeterangan.setText(
                                                "🔴 Pulang"
                                        );

                                        tvKeterangan.setTextColor(
                                                Color.parseColor("#EF4444")
                                        );
                                    }

                                    // FORMAT JAM
                                    tvJam.setText(
                                            jam + " WIB"
                                    );

                                    // FORMAT TANGGAL
                                    try {

                                        SimpleDateFormat inputFormat =
                                                new SimpleDateFormat(
                                                        "yyyy-MM-dd",
                                                        Locale.getDefault()
                                                );

                                        SimpleDateFormat outputFormat =
                                                new SimpleDateFormat(
                                                        "EEEE, dd MMMM yyyy",
                                                        new Locale(
                                                                "id",
                                                                "ID"
                                                        )
                                                );

                                        Date date =
                                                inputFormat.parse(
                                                        tanggal
                                                );

                                        String tanggalIndonesia =
                                                outputFormat.format(
                                                        date
                                                );

                                        tvTanggal.setText(
                                                tanggalIndonesia
                                        );

                                    }catch (Exception e){

                                        tvTanggal.setText(
                                                tanggal
                                        );
                                    }

                                    // SET DATA
                                    tvNama.setText(nama);

                                    tvJabatan.setText(jabatan);

                                    tvLokasi.setText(lokasi);

                                }else{

                                    Toast.makeText(
                                            this,
                                            "Data absensi tidak ditemukan",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }

                            }catch (Exception e){

                                e.printStackTrace();

                                Toast.makeText(
                                        this,
                                        "JSON Error : "
                                                + e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                        },

                        error -> {

                            String message =
                                    error.toString();

                            if(error instanceof TimeoutError){

                                message =
                                        "Koneksi timeout";
                            }

                            Toast.makeText(
                                    this,
                                    message,
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

        RequestQueue queue =
                Volley.newRequestQueue(this);

        queue.add(request);
    }
}