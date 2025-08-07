package com.example.bmi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;
public class edit_bmi extends AppCompatActivity {
    private EditText edt_height, edt_weight;
    private ImageButton btnCal_bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_bmi);

        edt_height = findViewById(R.id.ed_height);
        edt_weight = findViewById(R.id.ed_weight);
        btnCal_bmi = findViewById(R.id.img_btncal);

        btnCal_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightStr = edt_weight.getText().toString();
                String heightStr = edt_height.getText().toString();
                if(weightStr.isEmpty() || heightStr.isEmpty()){
                    Toast.makeText(edit_bmi.this,"Vui long hay nhap day du thong tin!",Toast.LENGTH_SHORT).show();
                }
                else{
                    double weight = Double.parseDouble(weightStr);
                    double height = Double.parseDouble(heightStr);
                    if(weight <= 0 || height <= 0){
                        Toast.makeText(edit_bmi.this,"Du lieu khong hop le",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(edit_bmi.this, ketqua_bmi.class);
                    intent.putExtra("WEIGHT", weight);
                    intent.putExtra("HEIGHT",height);
                    startActivity(intent);
                }
            }
        });



    }
}

