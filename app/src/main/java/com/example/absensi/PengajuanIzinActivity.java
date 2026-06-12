package com.example.absensi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PengajuanIzinActivity
        extends AppCompatActivity {

    // SPINNER
    Spinner spJenis;

    // EDITTEXT
    EditText etTanggal;
    EditText etWaktu;
    EditText etKeterangan;

    // BUTTON
    LinearLayout btnKirim;

    // URL API
    String URL_IZIN =
            "http://192.168.1.4/absensi/pengajuan_izin.php";

    // USER LOGIN
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_pengajuan_izin
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

        // INISIALISASI
        spJenis =
                findViewById(R.id.spJenis);

        etTanggal =
                findViewById(R.id.etTanggal);

        etWaktu =
                findViewById(R.id.etWaktu);

        etKeterangan =
                findViewById(R.id.etKeterangan);

        btnKirim =
                findViewById(R.id.btnKirim);

        // VALIDASI XML
        if(spJenis == null
                || etTanggal == null
                || etWaktu == null
                || etKeterangan == null
                || btnKirim == null){

            Toast.makeText(
                    this,
                    "ID XML tidak ditemukan",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // DATA SPINNER
        String[] dataJenis = {

                "Izin",
                "Sakit",
                "Cuti",
                "Keperluan Keluarga"

        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        dataJenis
                );

        spJenis.setAdapter(adapter);

        // =========================
        // DATE PICKER
        // =========================
        etTanggal.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();

            int year =
                    calendar.get(Calendar.YEAR);

            int month =
                    calendar.get(Calendar.MONTH);

            int day =
                    calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog =
                    new DatePickerDialog(

                            this,

                            (view, y, m, d) -> {

                                String tanggal =
                                        y + "-" +
                                                String.format("%02d", (m + 1)) +
                                                "-" +
                                                String.format("%02d", d);

                                etTanggal.setText(
                                        tanggal
                                );

                            },

                            year,
                            month,
                            day
                    );

            dialog.show();

        });

        // =========================
        // TIME PICKER
        // =========================
        etWaktu.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();

            int hour =
                    calendar.get(Calendar.HOUR_OF_DAY);

            int minute =
                    calendar.get(Calendar.MINUTE);

            TimePickerDialog dialog =
                    new TimePickerDialog(

                            this,

                            (view, h, m) -> {

                                String waktu =
                                        String.format(
                                                "%02d:%02d",
                                                h,
                                                m
                                        );

                                etWaktu.setText(
                                        waktu
                                );

                            },

                            hour,
                            minute,
                            true
                    );

            dialog.show();

        });

        // =========================
        // BUTTON KIRIM
        // =========================
        btnKirim.setOnClickListener(v -> {

            String jenis =
                    spJenis.getSelectedItem()
                            .toString();

            String tanggal =
                    etTanggal.getText()
                            .toString()
                            .trim();

            String waktu =
                    etWaktu.getText()
                            .toString()
                            .trim();

            String keterangan =
                    etKeterangan.getText()
                            .toString()
                            .trim();

            // VALIDASI
            if(tanggal.isEmpty()){

                etTanggal.setError(
                        "Tanggal wajib diisi"
                );

                return;
            }

            if(waktu.isEmpty()){

                etWaktu.setError(
                        "Waktu wajib diisi"
                );

                return;
            }

            if(keterangan.isEmpty()){

                etKeterangan.setError(
                        "Keterangan wajib diisi"
                );

                return;
            }

            // REQUEST API
            StringRequest request =
                    new StringRequest(

                            Request.Method.POST,

                            URL_IZIN,

                            response -> {

                                if(response.contains("success")){

                                    Toast.makeText(
                                            this,
                                            "Pengajuan berhasil dikirim",
                                            Toast.LENGTH_LONG
                                    ).show();

                                    finish();

                                }else{

                                    Toast.makeText(
                                            this,
                                            response,
                                            Toast.LENGTH_LONG
                                    ).show();
                                }

                            },

                            error -> Toast.makeText(
                                    this,
                                    "Error : "
                                            + error.toString(),
                                    Toast.LENGTH_LONG
                            ).show()

                    ){

                        @Override
                        protected Map<String, String> getParams(){

                            Map<String, String> params =
                                    new HashMap<>();

                            params.put(
                                    "user_id",
                                    userId
                            );

                            params.put(
                                    "jenis",
                                    jenis
                            );

                            params.put(
                                    "tanggal",
                                    tanggal
                            );

                            params.put(
                                    "waktu",
                                    waktu
                            );

                            params.put(
                                    "keterangan",
                                    keterangan
                            );

                            return params;
                        }
                    };

            // EXECUTE REQUEST
            RequestQueue queue =
                    Volley.newRequestQueue(this);

            queue.add(request);

        });

    }
}