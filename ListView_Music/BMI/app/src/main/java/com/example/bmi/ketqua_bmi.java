package com.example.bmi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ketqua_bmi extends AppCompatActivity {
    private TextView txt_kq, txt_mota;
    private Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ketqua_bmi);
        txt_kq = findViewById(R.id.txt_kq);
        txt_mota = findViewById(R.id.txt_mota);
        btn_back = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        double weight = intent.getDoubleExtra("WEIGHT", 0);
        double height = intent.getDoubleExtra("HEIGHT", 0);
        double bmi = weight / (height * height);

        txt_kq.setText("CHI SO BMI: " + String.format("%.2f", bmi));

        if (bmi < 18.5) {
            txt_mota.setText("Bạn hơi gầy, hãy ăn uống đầy đủ đúng chất !");
        } else if (bmi >= 18.5 && bmi < 24.9) {
            txt_mota.setText("Bạn có cân nặng lý tưởng, hãy duy trì nhé !");
        } else if (bmi >= 25 && bmi < 29.9) {
            txt_mota.setText("Bạn hơi thừa cân hãy tập thể dục !");
        } else {
            txt_mota.setText("Bạn bị béo phì hãy ăn uống lành mạnh và tập thể dục !");
        }
        btn_back.setOnClickListener(v -> finish());
    }
}