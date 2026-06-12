package com.example.absensi;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity
        extends AppCompatActivity {

    LinearLayout btnLogin;

    TextView txtDaftar;

    EditText etEmail;
    EditText etPassword;

    ImageView imgEye;

    // STATUS PASSWORD
    boolean isPasswordVisible = false;

    // URL LOGIN API
    String URL_LOGIN =
            "http://192.168.1.4/absensi/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_login
        );

        // =========================
        // INISIALISASI
        // =========================
        btnLogin =
                findViewById(R.id.btnLogin);

        txtDaftar =
                findViewById(R.id.txtDaftar);

        etEmail =
                findViewById(R.id.etEmail);

        etPassword =
                findViewById(R.id.etPassword);

        imgEye =
                findViewById(R.id.imgEye);

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
        // BUTTON LOGIN
        // =========================
        btnLogin.setOnClickListener(v -> {

            loginUser();

        });

        // =========================
        // PINDAH REGISTER
        // =========================
        txtDaftar.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            LoginActivity.this,
                            RegisterActivity.class
                    );

            startActivity(intent);

        });

    }

    // =========================
    // LOGIN USER
    // =========================
    private void loginUser() {

        String email =
                etEmail.getText()
                        .toString()
                        .trim();

        String password =
                etPassword.getText()
                        .toString()
                        .trim();

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
        // VALIDASI PASSWORD
        // =========================
        if(password.isEmpty()){

            etPassword.setError(
                    "Password wajib diisi"
            );

            etPassword.requestFocus();

            return;
        }

        // =========================
        // REQUEST LOGIN
        // =========================
        StringRequest stringRequest =
                new StringRequest(

                        Request.Method.POST,

                        URL_LOGIN,

                        response -> {

                            try {

                                // PARSE JSON
                                JSONObject object =
                                        new JSONObject(
                                                response.trim()
                                        );

                                // STATUS
                                String status =
                                        object.getString(
                                                "status"
                                        );

                                // =========================
                                // LOGIN BERHASIL
                                // =========================
                                if(
                                        status.equals(
                                                "success"
                                        )
                                ){

                                    // DATA USER
                                    String userId =
                                            object.getString(
                                                    "id"
                                            );

                                    String nama =
                                            object.getString(
                                                    "nama"
                                            );

                                    String jabatan =
                                            object.getString(
                                                    "position"
                                            );

                                    String emailUser =
                                            object.getString(
                                                    "email"
                                            );

                                    // =========================
                                    // SIMPAN SESSION
                                    // =========================
                                    SharedPreferences
                                            sharedPreferences =
                                            getSharedPreferences(
                                                    "LOGIN_SESSION",
                                                    MODE_PRIVATE
                                            );

                                    SharedPreferences.Editor
                                            editor =
                                            sharedPreferences.edit();

                                    // SIMPAN DATA
                                    editor.putString(
                                            "user_id",
                                            userId
                                    );

                                    editor.putString(
                                            "nama",
                                            nama
                                    );

                                    editor.putString(
                                            "jabatan",
                                            jabatan
                                    );

                                    editor.putString(
                                            "email",
                                            emailUser
                                    );

                                    editor.putBoolean(
                                            "is_login",
                                            true
                                    );

                                    editor.apply();

                                    Toast.makeText(
                                            LoginActivity.this,
                                            "Login berhasil",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    // =========================
                                    // PINDAH DASHBOARD
                                    // =========================
                                    Intent intent =
                                            new Intent(
                                                    LoginActivity.this,
                                                    DashboardActivity.class
                                            );

                                    startActivity(intent);

                                    finish();

                                }

                                // =========================
                                // LOGIN GAGAL
                                // =========================
                                else{

                                    Toast.makeText(
                                            LoginActivity.this,
                                            object.getString(
                                                    "message"
                                            ),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }

                            } catch (Exception e){

                                e.printStackTrace();

                                Toast.makeText(
                                        LoginActivity.this,
                                        "JSON Error",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        },

                        error -> Toast.makeText(
                                LoginActivity.this,
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
                                "email",
                                email
                        );

                        params.put(
                                "password",
                                password
                        );

                        return params;
                    }
                };

        // =========================
        // REQUEST QUEUE
        // =========================
        RequestQueue requestQueue =
                Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
}