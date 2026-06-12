package com.example.absensi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AkunActivity
        extends AppCompatActivity {

    BottomNavigationView bottomNav;

    LinearLayout menuDataDiri;
    LinearLayout menuPengaturan;
    LinearLayout menuPassword;
    LinearLayout menuTentang;
    LinearLayout menuKeluar;

    LinearLayout btnCamera;

    ImageView imgProfile;

    TextView tvNama;
    TextView tvPosition;

    String userId;

    // =========================
    // GALLERY
    // =========================
    private static final int GALLERY_REQUEST = 100;

    // =========================
    // URL API
    // =========================
    String URL_PROFILE =
            "http://192.168.1.4/absensi/profile.php";

    String URL_UPLOAD =
            "http://192.168.1.4/absensi/upload_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_akun
        );

        // =========================
        // INISIALISASI
        // =========================
        bottomNav =
                findViewById(R.id.bottomNav);

        menuDataDiri =
                findViewById(R.id.menuDataDiri);

        menuPengaturan =
                findViewById(R.id.menuPengaturan);

        menuPassword =
                findViewById(R.id.menuPassword);

        menuTentang =
                findViewById(R.id.menuTentang);

        menuKeluar =
                findViewById(R.id.menuKeluar);

        btnCamera =
                findViewById(R.id.btnCamera);

        imgProfile =
                findViewById(R.id.imgProfile);

        tvNama =
                findViewById(R.id.tvNama);

        tvPosition =
                findViewById(R.id.tvPosition);

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
        // NAVBAR AKTIF
        // =========================
        bottomNav.setSelectedItemId(
                R.id.nav_account
        );

        // =========================
        // BUTTON GALLERY
        // =========================
        btnCamera.setOnClickListener(v -> {

            Intent galleryIntent =
                    new Intent(

                            Intent.ACTION_PICK,

                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                    );

            startActivityForResult(
                    galleryIntent,
                    GALLERY_REQUEST
            );

        });

        // =========================
        // MENU DATA DIRI
        // =========================
        menuDataDiri.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            AkunActivity.this,
                            DataDiriActivity.class
                    );

            startActivity(intent);

        });

        // =========================
        // MENU PENGATURAN
        // =========================
        menuPengaturan.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Menu Pengaturan",
                    Toast.LENGTH_SHORT
            ).show();

        });

        // =========================
        // MENU PASSWORD
        // =========================
        menuPassword.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Menu Ubah Password",
                    Toast.LENGTH_SHORT
            ).show();

        });

        // =========================
        // MENU TENTANG
        // =========================
        menuTentang.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Tentang Aplikasi",
                    Toast.LENGTH_SHORT
            ).show();

        });

        // =========================
        // LOGOUT
        // =========================
        menuKeluar.setOnClickListener(v -> {

            SharedPreferences.Editor editor =
                    sharedPreferences.edit();

            editor.clear();

            editor.apply();

            Intent intent =
                    new Intent(
                            AkunActivity.this,
                            LoginActivity.class
                    );

            intent.setFlags(

                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

            );

            startActivity(intent);

            finish();

        });

        // =========================
        // BOTTOM NAVIGATION
        // =========================
        bottomNav.setOnItemSelectedListener(item -> {

            int id =
                    item.getItemId();

            // HOME
            if(id == R.id.nav_home){

                Intent intent =
                        new Intent(
                                AkunActivity.this,
                                DashboardActivity.class
                        );

                startActivity(intent);

                overridePendingTransition(0,0);

                finish();

                return true;
            }

            // HISTORY
            else if(id == R.id.nav_history){

                Intent intent =
                        new Intent(
                                AkunActivity.this,
                                HistoryActivity.class
                        );

                startActivity(intent);

                overridePendingTransition(0,0);

                finish();

                return true;
            }

            // ABSEN
            else if(id == R.id.nav_absen){

                Intent intent =
                        new Intent(
                                AkunActivity.this,
                                AbsenActivity.class
                        );

                startActivity(intent);

                overridePendingTransition(0,0);

                finish();

                return true;
            }

            // NOTIFIKASI
            else if(id == R.id.nav_notification){

                Intent intent =
                        new Intent(
                                AkunActivity.this,
                                NotifikasiActivity.class
                        );

                startActivity(intent);

                overridePendingTransition(0,0);

                finish();

                return true;
            }

            // AKUN
            else if(id == R.id.nav_account){

                return true;
            }

            return false;

        });

    }

    // =========================
    // HASIL GALLERY
    // =========================
    @Override
    protected void onActivityResult(

            int requestCode,
            int resultCode,
            @Nullable Intent data

    ) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if(

                requestCode == GALLERY_REQUEST &&
                        resultCode == Activity.RESULT_OK &&
                        data != null

        ){

            try {

                Uri imageUri =
                        data.getData();

                InputStream inputStream =
                        getContentResolver()
                                .openInputStream(imageUri);

                Bitmap photo =
                        BitmapFactory.decodeStream(
                                inputStream
                        );

                // TAMPILKAN FOTO
                imgProfile.setImageBitmap(photo);

                // CONVERT BASE64
                ByteArrayOutputStream baos =
                        new ByteArrayOutputStream();

                photo.compress(

                        Bitmap.CompressFormat.JPEG,
                        100,
                        baos

                );

                byte[] imageBytes =
                        baos.toByteArray();

                String imageString =
                        Base64.encodeToString(

                                imageBytes,
                                Base64.DEFAULT

                        );

                // UPLOAD FOTO
                uploadPhoto(imageString);

            }catch (Exception e){

                e.printStackTrace();
            }

        }
    }

    // =========================
    // UPLOAD FOTO DATABASE
    // =========================
    private void uploadPhoto(String photo){

        StringRequest request =
                new StringRequest(

                        Request.Method.POST,

                        URL_UPLOAD,

                        response -> {

                            try {

                                JSONObject object =
                                        new JSONObject(response);

                                String status =
                                        object.getString("status");

                                if(status.equals("success")){

                                    Toast.makeText(

                                            AkunActivity.this,

                                            "Foto profile berhasil diperbarui",

                                            Toast.LENGTH_SHORT

                                    ).show();

                                    loadProfile();

                                }

                            }catch (Exception e){

                                Toast.makeText(

                                        AkunActivity.this,

                                        e.toString(),

                                        Toast.LENGTH_LONG

                                ).show();
                            }

                        },

                        error -> Toast.makeText(

                                AkunActivity.this,

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
                                "photo",
                                photo
                        );

                        return params;
                    }
                };

        Volley.newRequestQueue(this)
                .add(request);
    }

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

                                    tvNama.setText(
                                            data.getString("nama")
                                    );

                                    tvPosition.setText(
                                            data.getString("position")
                                    );

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

                                        AkunActivity.this,

                                        e.toString(),

                                        Toast.LENGTH_LONG

                                ).show();
                            }

                        },

                        error -> Toast.makeText(

                                AkunActivity.this,

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
}