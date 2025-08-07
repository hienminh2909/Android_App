package com.example.project2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnDS = findViewById(R.id.btn_dsan);
        Button btnVT = findViewById(R.id.btn_vitri);
        Button btnDC = findViewById(R.id.btn_dichuyen);
        Button btnDL = findViewById(R.id.btn_vanhoa);
        Button btnDG = findViewById(R.id.button);

        btnDS.setOnClickListener(v -> {
            Intent intDS = new Intent(MainActivity.this, dacsan.class);
            startActivity(intDS);
        });

        btnVT.setOnClickListener(j -> {
            Intent intVT = new Intent(MainActivity.this, vitri.class);
            startActivity(intVT);
        });

        btnDC.setOnClickListener(i -> {
            Intent intDC = new Intent(MainActivity.this, dichuyen.class);
            startActivity(intDC);
        });

        btnDL.setOnClickListener(k -> {
            Intent intDT = new Intent(MainActivity.this, dulich.class);
            startActivity(intDT);
        });

        btnDG.setOnClickListener(h -> {
            Intent intDG = new Intent(MainActivity.this, danhgia.class);
            startActivity(intDG);
        });
    }
}