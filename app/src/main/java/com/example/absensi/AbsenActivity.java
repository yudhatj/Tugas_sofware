package com.example.absensi;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AbsenActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    BottomNavigationView bottomNav;

    ImageView btnBack;
    ImageView imgPreview;

    // USER
    TextView txtNama;
    TextView txtJabatan;

    // STATUS
    TextView tvStatus;

    // LOCATION
    TextView tvAlamat;
    TextView tvLatitude;
    TextView tvLongitude;
    TextView tvAkurasi;

    // BUTTON
    LinearLayout btnCheckIn;
    LinearLayout btnCheckOut;
    LinearLayout btnAbsen;

    // MAP
    GoogleMap mMap;

    // LOCATION CLIENT
    FusedLocationProviderClient fusedLocationClient;

    // DATA LOCATION
    double latitude = 0.0;
    double longitude = 0.0;

    String alamat = "";

    // LOGIN
    String userId;

    // STATUS ABSEN
    String tipeAbsen = "Check In";

    // FOTO
    Bitmap bitmap;

    // API
    String URL_ABSEN =
            "http://192.168.1.4/absensi/absen.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_absen);

        // SESSION
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
                        "-"
                );

        String jabatan =
                sharedPreferences.getString(
                        "jabatan",
                        "-"
                );

        // INISIALISASI
        bottomNav = findViewById(R.id.bottomNav);

        btnBack = findViewById(R.id.btnBack);

        imgPreview = findViewById(R.id.imgPreview);

        txtNama = findViewById(R.id.txtNama);

        txtJabatan = findViewById(R.id.txtJabatan);

        tvStatus = findViewById(R.id.tvStatus);

        tvAlamat = findViewById(R.id.tvAlamat);

        tvLatitude = findViewById(R.id.tvLatitude);

        tvLongitude = findViewById(R.id.tvLongitude);

        tvAkurasi = findViewById(R.id.tvAkurasi);

        btnCheckIn = findViewById(R.id.btnCheckIn);

        btnCheckOut = findViewById(R.id.btnCheckOut);

        btnAbsen = findViewById(R.id.btnAbsen);

        // SET USER
        txtNama.setText(nama);

        txtJabatan.setText(jabatan);

        // MAP
        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getSupportFragmentManager()
                                .findFragmentById(R.id.map);

        if(mapFragment != null){

            mapFragment.getMapAsync(this);
        }

        // LOCATION
        fusedLocationClient =
                LocationServices
                        .getFusedLocationProviderClient(this);

        getCurrentLocation();

        // BACK
        btnBack.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            AbsenActivity.this,
                            DashboardActivity.class
                    )
            );

            finish();
        });

        // CHECK IN
        btnCheckIn.setOnClickListener(v -> {

            tipeAbsen = "Check In";

            tvStatus.setText(
                    "Mode Check In"
            );

        });

        // CHECK OUT
        btnCheckOut.setOnClickListener(v -> {

            tipeAbsen = "Check Out";

            tvStatus.setText(
                    "Mode Check Out"
            );

        });

        // CAMERA
        imgPreview.setOnClickListener(v -> {

            if(ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(

                        this,

                        new String[]{
                                Manifest.permission.CAMERA
                        },

                        200

                );

            }else{

                bukaKamera();
            }

        });

        // ABSEN
        btnAbsen.setOnClickListener(v -> {

            if(bitmap == null){

                Toast.makeText(

                        this,

                        "Silakan selfie terlebih dahulu",

                        Toast.LENGTH_SHORT

                ).show();

                return;
            }

            kirimAbsen();

        });

        // NAVBAR
        bottomNav.setSelectedItemId(
                R.id.nav_absen
        );

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            // HOME
            if(id == R.id.nav_home){

                startActivity(
                        new Intent(
                                this,
                                DashboardActivity.class
                        )
                );

                finish();

                return true;
            }

            // HISTORY
            else if(id == R.id.nav_history){

                startActivity(
                        new Intent(
                                this,
                                HistoryActivity.class
                        )
                );

                finish();

                return true;
            }

            // ABSEN
            else if(id == R.id.nav_absen){

                return true;
            }

            // NOTIFIKASI
            else if(id == R.id.nav_notification){

                startActivity(
                        new Intent(
                                this,
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
                                this,
                                AkunActivity.class
                        )
                );

                finish();

                return true;
            }

            return false;
        });

    }

    // MAP READY
    @Override
    public void onMapReady(
            GoogleMap googleMap
    ) {

        mMap = googleMap;
    }

    // GET LOCATION
    private void getCurrentLocation(){

        if(ContextCompat.checkSelfPermission(

                this,

                Manifest.permission.ACCESS_FINE_LOCATION

        ) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(

                    this,

                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },

                    101

            );

            return;
        }

        fusedLocationClient
                .getLastLocation()
                .addOnSuccessListener(location -> {

                    if(location != null){

                        latitude =
                                location.getLatitude();

                        longitude =
                                location.getLongitude();

                        // TEXT
                        tvLatitude.setText(
                                "Latitude : " + latitude
                        );

                        tvLongitude.setText(
                                "Longitude : " + longitude
                        );

                        tvAkurasi.setText(
                                "Akurasi GPS : "
                                        + (int) location.getAccuracy()
                                        + " Meter"
                        );

                        // MAP
                        if(mMap != null){

                            LatLng posisi =
                                    new LatLng(
                                            latitude,
                                            longitude
                                    );

                            mMap.clear();

                            mMap.addMarker(
                                    new MarkerOptions()
                                            .position(posisi)
                                            .title("Lokasi Anda")
                            );

                            mMap.moveCamera(
                                    CameraUpdateFactory
                                            .newLatLngZoom(
                                                    posisi,
                                                    18f
                                            )
                            );
                        }

                        // ADDRESS
                        getAlamatRealtime(
                                latitude,
                                longitude
                        );
                    }

                });

    }

    // GET ADDRESS
    private void getAlamatRealtime(
            double lat,
            double lng
    ){

        try {

            Geocoder geocoder =
                    new Geocoder(
                            this,
                            Locale.getDefault()
                    );

            List<Address> addresses =
                    geocoder.getFromLocation(
                            lat,
                            lng,
                            1
                    );

            if(addresses != null
                    && addresses.size() > 0){

                Address address =
                        addresses.get(0);

                alamat =
                        address.getAddressLine(0);

                tvAlamat.setText(alamat);
            }

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    // CAMERA
    private void bukaKamera(){

        Intent intent =
                new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE
                );

        cameraLauncher.launch(intent);
    }

    // CAMERA RESULT
    ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(

                    new ActivityResultContracts.StartActivityForResult(),

                    result -> {

                        if(result.getResultCode()
                                == RESULT_OK){

                            Bundle bundle =
                                    result.getData()
                                            .getExtras();

                            bitmap =
                                    (Bitmap)
                                            bundle.get("data");

                            imgPreview.setImageBitmap(
                                    bitmap
                            );
                        }
                    });

    // =========================
    // KIRIM ABSEN
    // =========================
    private void kirimAbsen(){

        String tanggal =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                ).format(new Date());

        String jam =
                new SimpleDateFormat(
                        "HH:mm:ss",
                        Locale.getDefault()
                ).format(new Date());

        // PARAMETER
        Map<String, String> params =
                new HashMap<>();

        params.put("user_id", userId);

        params.put("type", tipeAbsen);

        params.put("date", tanggal);

        params.put("time", jam);

        params.put("address", alamat);

        params.put(
                "latitude",
                String.valueOf(latitude)
        );

        params.put(
                "longitude",
                String.valueOf(longitude)
        );

        // FOTO
        Map<String,
                VolleyMultipartRequest.DataPart>
                file = new HashMap<>();

        long imageName =
                System.currentTimeMillis();

        file.put(

                "photo",

                new VolleyMultipartRequest.DataPart(

                        "IMG_" + imageName + ".jpg",

                        getFileData(bitmap),

                        "image/jpeg"

                )
        );

        // REQUEST
        VolleyMultipartRequest request =
                new VolleyMultipartRequest(

                        Request.Method.POST,

                        URL_ABSEN,

                        params,

                        file,

                        response -> {

                            try {

                                String result =
                                        new String(
                                                response.data
                                        );

                                JSONObject object =
                                        new JSONObject(result);

                                String status =
                                        object.getString("status");

                                String message =
                                        object.getString("message");

                                if(status.equals("success")){

                                    Toast.makeText(

                                            this,

                                            message,

                                            Toast.LENGTH_SHORT

                                    ).show();

                                    Intent intent =
                                            new Intent(

                                                    AbsenActivity.this,

                                                    SuccessAbsenActivity.class

                                            );

                                    intent.putExtra(
                                            "type",
                                            tipeAbsen
                                    );

                                    startActivity(intent);

                                    finish();

                                }else{

                                    Toast.makeText(

                                            this,

                                            message,

                                            Toast.LENGTH_LONG

                                    ).show();
                                }

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

                                "UPLOAD ERROR : "
                                        + error.toString(),

                                Toast.LENGTH_LONG

                        ).show()

                );

        RequestQueue queue =
                Volley.newRequestQueue(this);

        queue.add(request);

    }

    // =========================
    // CONVERT BITMAP
    // =========================
    private byte[] getFileData(
            Bitmap bitmap
    ){

        ByteArrayOutputStream bos =
                new ByteArrayOutputStream();

        bitmap.compress(

                Bitmap.CompressFormat.JPEG,

                80,

                bos

        );

        return bos.toByteArray();
    }
}