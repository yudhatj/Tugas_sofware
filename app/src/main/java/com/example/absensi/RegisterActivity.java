package com.example.absensi;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity
        extends AppCompatActivity {

    LinearLayout btnRegister;

    TextView txtMasuk;

    ImageView btnBack;
    ImageView imgEye;

    EditText etNama;
    EditText etEmail;
    EditText etHp;
    EditText etPosition;
    EditText etPassword;

    // STATUS PASSWORD
    boolean isPasswordVisible = false;

    // URL API
    String URL_REGISTER =
            "http://192.168.88.221/absensi/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_register
        );

        // =========================
        // INISIALISASI
        // =========================
        btnRegister =
                findViewById(R.id.btnRegister);

        txtMasuk =
                findViewById(R.id.txtMasuk);

        btnBack =
                findViewById(R.id.btnBack);

        imgEye =
                findViewById(R.id.imgEye);

        etNama =
                findViewById(R.id.etNama);

        etEmail =
                findViewById(R.id.etEmail);

        etHp =
                findViewById(R.id.etHp);

        etPosition =
                findViewById(R.id.etPosition);

        etPassword =
                findViewById(R.id.etPassword);

        // =========================
        // SHOW HIDE PASSWORD
        // =========================
        imgEye.setOnClickListener(v -> {

            if(isPasswordVisible){

                // SEMBUNYIKAN PASSWORD
                etPassword.setInputType(

                        InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD

                );

                imgEye.setImageResource(
                        R.drawable.eye
                );

                isPasswordVisible = false;

            }else{

                // TAMPILKAN PASSWORD
                etPassword.setInputType(

                        InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                );

                imgEye.setImageResource(
                        R.drawable.eye_off
                );

                isPasswordVisible = true;
            }

            // CURSOR KE AKHIR
            etPassword.setSelection(
                    etPassword.getText().length()
            );

        });

        // =========================
        // BUTTON REGISTER
        // =========================
        btnRegister.setOnClickListener(v -> {

            registerUser();

        });

        // =========================
        // PINDAH LOGIN
        // =========================
        txtMasuk.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);

            finish();

        });

        // =========================
        // BUTTON BACK
        // =========================
        btnBack.setOnClickListener(v -> {

            finish();

        });

    }

    // =========================
    // REGISTER USER
    // =========================
    private void registerUser(){

        // AMBIL DATA
        String nama =
                etNama.getText()
                        .toString()
                        .trim();

        String email =
                etEmail.getText()
                        .toString()
                        .trim();

        String hp =
                etHp.getText()
                        .toString()
                        .trim();

        String position =
                etPosition.getText()
                        .toString()
                        .trim();

        String password =
                etPassword.getText()
                        .toString()
                        .trim();

        // =========================
        // VALIDASI NAMA
        // =========================
        if(nama.isEmpty()){

            etNama.setError(
                    "Nama wajib diisi"
            );

            etNama.requestFocus();

            return;
        }

        // =========================
        // VALIDASI EMAIL
        // =========================
        if(email.isEmpty()){

            etEmail.setError(
                    "Email wajib diisi"
            );

            etEmail.requestFocus();

            return;
        }

        // FORMAT EMAIL
        if(!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()){

            etEmail.setError(
                    "Format email tidak valid"
            );

            etEmail.requestFocus();

            return;
        }

        // =========================
        // VALIDASI HP
        // =========================
        if(hp.isEmpty()){

            etHp.setError(
                    "Nomor HP wajib diisi"
            );

            etHp.requestFocus();

            return;
        }

        // =========================
        // VALIDASI POSITION
        // =========================
        if(position.isEmpty()){

            etPosition.setError(
                    "Jabatan wajib diisi"
            );

            etPosition.requestFocus();

            return;
        }

        // =========================
        // VALIDASI PASSWORD
        // =========================
        if(password.isEmpty()){

            etPassword.setError(
                    "Password wajib diisi"
            );

            etPassword.requestFocus();

            return;
        }

        // MINIMAL PASSWORD
        if(password.length() < 6){

            etPassword.setError(
                    "Password minimal 6 karakter"
            );

            etPassword.requestFocus();

            return;
        }

        // =========================
        // REQUEST REGISTER
        // =========================
        StringRequest stringRequest =
                new StringRequest(

                        Request.Method.POST,

                        URL_REGISTER,

                        response -> {

                            String hasil =
                                    response.trim();

                            // SUCCESS
                            if(
                                    hasil.equals(
                                            "success"
                                    )
                            ){

                                Toast.makeText(
                                        RegisterActivity.this,
                                        "Register berhasil",
                                        Toast.LENGTH_SHORT
                                ).show();

                                Intent intent =
                                        new Intent(
                                                RegisterActivity.this,
                                                LoginActivity.class
                                        );

                                startActivity(intent);

                                finish();

                            }

                            // EMAIL SUDAH ADA
                            else if(
                                    hasil.equals(
                                            "email_exists"
                                    )
                            ){

                                Toast.makeText(
                                        RegisterActivity.this,
                                        "Email sudah digunakan",
                                        Toast.LENGTH_SHORT
                                ).show();

                            }

                            // ERROR
                            else{

                                Toast.makeText(
                                        RegisterActivity.this,
                                        hasil,
                                        Toast.LENGTH_LONG
                                ).show();

                            }

                        },

                        error -> Toast.makeText(
                                RegisterActivity.this,
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
                                "nama",
                                nama
                        );

                        params.put(
                                "email",
                                email
                        );

                        params.put(
                                "phone",
                                hp
                        );

                        params.put(
                                "position",
                                position
                        );

                        params.put(
                                "password",
                                password
                        );

                        return params;
                    }
                };

        // REQUEST QUEUE
        RequestQueue requestQueue =
                Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
}