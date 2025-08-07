package com.example.dk_thietbi_sim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.telephony.SmsManager;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText ed_gocquay;
    private Button btn_gui;
    private final String PhoneNumber = "+84335345979";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ed_gocquay = (EditText) findViewById(R.id.ed_gocquay);
        btn_gui =(Button) findViewById(R.id.btn_gui);

        // Yêu cầu quyền gửi SMS nếu chưa cấp
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }

        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String angelText = ed_gocquay.getText().toString().trim();
                if(angelText.isEmpty()){
                    Toast.makeText(MainActivity.this,"Vui long nhap goc servo",Toast.LENGTH_SHORT).show();
                    return;
                }

                int angle = Integer.parseInt(angelText);
                String Message = "Servo" + angle;

                try{
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PhoneNumber,null,Message,null,null);
                    Toast.makeText(MainActivity.this, "Đã gửi: " + Message + " đến " + PhoneNumber, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Gui that bai" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}