package com.example.absensi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OnboardingActivity extends AppCompatActivity {

    Button btnMulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        btnMulai = findViewById(R.id.btnMulai);

        btnMulai.setOnClickListener(v -> {

            Intent intent = new Intent(
                    OnboardingActivity.this,
                    LoginActivity.class
            );

            startActivity(intent);

            // supaya tidak kembali ke onboarding
            finish();

        });
    }
}