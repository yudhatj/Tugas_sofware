package com.example.absensi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DataDiriActivity
        extends AppCompatActivity {

    ImageView imgProfile;

    EditText etNama;
    EditText etEmail;
    EditText etPhone;
    EditText etPosition;

    Button btnSimpan;

    String userId;

    // =========================
    // URL API
    // =========================
    String URL_PROFILE =
            "http://192.168.1.4/absensi/profile.php";

    String URL_UPDATE =
            "http://192.168.1.4/absensi/update_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_data_diri
        );

        // =========================
        // INISIALISASI
        // =========================
        imgProfile =
                findViewById(R.id.imgProfile);

        etNama =
                findViewById(R.id.etNama);

        etEmail =
                findViewById(R.id.etEmail);

        etPhone =
                findViewById(R.id.etPhone);

        etPosition =
                findViewById(R.id.etPosition);

        btnSimpan =
                findViewById(R.id.btnSimpan);

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
        // LOAD PROFILE
        // =========================
        loadProfile();

        // =========================
        // BUTTON SIMPAN
        // =========================
        btnSimpan.setOnClickListener(v -> {

            updateProfile();

        });

    }

    // =========================
// LOAD PROFILE
// =========================
    private void loadProfile(){

        StringRequest request =
                new StringRequest(

                        Request.Method.POST,

                        URL_PROFILE,

                        response -> {

                            try {

                                JSONObject object =
                                        new JSONObject(response);

                                String status =
                                        object.getString("status");

                                if(status.equals("success")){

                                    JSONObject data =
                                            object.getJSONObject("data");

                                    etNama.setText(
                                            data.getString("nama")
                                    );

                                    etEmail.setText(
                                            data.getString("email")
                                    );

                                    etPhone.setText(
                                            data.getString("phone")
                                    );

                                    etPosition.setText(
                                            data.getString("position")
                                    );

                                    // FOTO PROFILE
                                    String photo =
                                            data.getString("photo");

                                    if(
                                            photo != null &&
                                                    !photo.isEmpty() &&
                                                    !photo.equals("null")
                                    ){

                                        Picasso.get()
                                                .load(
                                                        "http://192.168.1.4/absensi/" +
                                                                photo
                                                )
                                                .placeholder(
                                                        R.drawable.oip__1_
                                                )
                                                .error(
                                                        R.drawable.oip__1_
                                                )
                                                .into(
                                                        imgProfile
                                                );

                                    }else{

                                        imgProfile.setImageResource(
                                                R.drawable.oip__1_
                                        );
                                    }

                                }

                            }catch (Exception e){

                                Toast.makeText(

                                        DataDiriActivity.this,

                                        e.toString(),

                                        Toast.LENGTH_LONG

                                ).show();

                            }

                        },

                        error -> Toast.makeText(

                                DataDiriActivity.this,

                                error.toString(),

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

                        return params;
                    }
                };

        Volley.newRequestQueue(this)
                .add(request);
    }
    // =========================
    // UPDATE PROFILE
    // =========================
    private void updateProfile(){

        String nama =
                etNama.getText().toString().trim();

        String email =
                etEmail.getText().toString().trim();

        String phone =
                etPhone.getText().toString().trim();

        String position =
                etPosition.getText().toString().trim();

        // VALIDASI
        if(

                nama.isEmpty() ||
                        email.isEmpty() ||
                        phone.isEmpty() ||
                        position.isEmpty()

        ){

            Toast.makeText(

                    this,

                    "Data tidak boleh kosong",

                    Toast.LENGTH_LONG

            ).show();

            return;
        }

        StringRequest request =
                new StringRequest(

                        Request.Method.POST,

                        URL_UPDATE,

                        response -> {

                            try {

                                JSONObject object =
                                        new JSONObject(response);

                                Toast.makeText(

                                        DataDiriActivity.this,

                                        object.getString("message"),

                                        Toast.LENGTH_LONG

                                ).show();

                                loadProfile();
                            }catch (Exception e){

                                Toast.makeText(

                                        this,

                                        e.toString(),

                                        Toast.LENGTH_LONG

                                ).show();

                            }

                        },

                        error -> Toast.makeText(

                                this,

                                error.toString(),

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
                                "nama",
                                nama
                        );

                        params.put(
                                "email",
                                email
                        );

                        params.put(
                                "phone",
                                phone
                        );

                        params.put(
                                "position",
                                position
                        );

                        return params;
                    }
                };

        Volley.newRequestQueue(this)
                .add(request);

    }
}