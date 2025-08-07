package com.example.cuoi_ky_iot_test;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
public class MainActivity extends AppCompatActivity {

    private TextView temperatureValueTextView;
    private TextView txt_temp_limit,txt_humi_limit;
    private TextView humidityValueTextView;
    private EditText tempLimitEditText,humiLimitEditText;
    private Button setLimitButton;
    private DatabaseReference database;
    private float tempLimit =25; // Giới hạn mặc định
    private float humiLimit =50; // Giới hạn mặc định
    private float temp,hum;
    private long lastTime, currentTime =0;
    private long intevarTime = 30 *1000;
    private static final  int NOTIFICATION_PERMISSION_CODE = 100;
    private static final String CHANNEL_ID = "my_channel_id";
    boolean flagTemp_limit = false;
    boolean flagHum_limit = false;
    private int Temp_cnt=0;
    private int Hum_cnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        // Khởi tạo các thành phần từ layout
        temperatureValueTextView = findViewById(R.id.temperature_value);
        humidityValueTextView = findViewById(R.id.humidity_value);
        tempLimitEditText = findViewById(R.id.tempLimitEditText);
        humiLimitEditText = findViewById(R.id.humiLimitEditText);
        setLimitButton = findViewById(R.id.setLimitButton);
        txt_temp_limit = findViewById(R.id.txt_temp_limit);
        txt_humi_limit = findViewById(R.id.txt_humi_limit);
        // Khởi tạo Firebase Realtime Database
        database = FirebaseDatabase.getInstance().getReference("sensor_data");

        txt_temp_limit.setText("Nhiệt độ giới hạn:" + tempLimit);
        txt_humi_limit.setText("Độ ẩm giới hạn:" + humiLimit);

        // Lắng nghe thay đổi dữ liệu từ Firebase
        database.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                     temp = data.child("temperature").getValue(Float.class);
                     hum = data.child("humidity").getValue(Float.class);

                    // Cập nhật TextView với giá trị mới nhất
                    temperatureValueTextView.setText(temp + "°C");
                    humidityValueTextView.setText(hum + "%");

                        // Kiểm tra và cảnh báo nếu nhiệt độ vượt quá giới hạn
                        if (temp > tempLimit  ) {
                            if(Temp_cnt >= 3) {
                                flagTemp_limit = false;
                            }
                            else flagTemp_limit = true;

                            if (flagTemp_limit == true){
                                requestNotificationPermission();
                                sendNotification();
                                Temp_cnt++;
                                Toast.makeText(MainActivity.this, "Cảnh báo: Nhiệt độ vượt quá " + tempLimit + "°C!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            flagTemp_limit = false;
                            Temp_cnt=0;
                        }


                         if (hum > humiLimit ) {
                            if (Hum_cnt >= 3) {
                                flagHum_limit = false;
                            } else flagHum_limit = true;

                            if (flagHum_limit == true) {
                                Hum_cnt++;
                                requestNotificationPermission();
                                sendNotification();
                                Toast.makeText(MainActivity.this, "Cảnh báo: Độ ẩm vượt quá " + humiLimit + "°%!", Toast.LENGTH_SHORT).show();
                            }
                        }
                         else {
                             flagHum_limit = false;
                             Hum_cnt=0;
                         }




                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                temperatureValueTextView.setText("Error: " + error.getMessage());
                humidityValueTextView.setText("Error: " + error.getMessage());
            }
        });

        // Xử lý sự kiện nhấn nút cài đặt giới hạn
        setLimitButton.setOnClickListener(v -> {
            String temp_limitText = tempLimitEditText.getText().toString().trim();
            String humi_limitText = humiLimitEditText.getText().toString().trim();
            if (!temp_limitText.isEmpty()) {
                try {
                    tempLimit = Float.parseFloat(temp_limitText);
                    txt_temp_limit.setText("Nhiệt độ giới hạn:" + tempLimit);
                    Toast.makeText(MainActivity.this, "Giới hạn nhiệt độ được cài đặt: " + tempLimit + "°C", Toast.LENGTH_SHORT).show();
                    tempLimitEditText.setText(""); // Xóa nội dung sau khi cài đặt
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
            else if (!humi_limitText.isEmpty()) {
                try {
                    humiLimit = Float.parseFloat(humi_limitText);
                    txt_humi_limit.setText("Độ ẩm giới hạn:" + humiLimit);
                    Toast.makeText(MainActivity.this, "Giới hạn độ ẩm được cài đặt: " + humiLimit + "%", Toast.LENGTH_SHORT).show();
                    humiLimitEditText.setText(""); // Xóa nội dung sau khi cài đặt
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(MainActivity.this, "Vui lòng nhập giới hạn nhiệt độ hoặc độ ẩm!", Toast.LENGTH_SHORT).show();
            }
        });
    }





    // Thong bao
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "My Channel";
            String channelDescription = "This is my notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Yêu cầu quyền thông báo cho Android 13+
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.POST_NOTIFICATIONS"}, NOTIFICATION_PERMISSION_CODE);
            }
        }
    }

    // Xử lý kết quả yêu cầu quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendNotification();
            } else {
                Toast.makeText(this, "Quyền thông báo bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Gửi thông báo
    private void sendNotification() {
        // Tạo Intent để mở Activity khi nhấn vào thông báo
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Tạo thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img_2)
                .setContentTitle("Thông báo vượt giới hạn")
                .setContentText("Nhiệt độ hiện tại là: " + temp + ". Độ ẩm hiện tại: " + hum )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setSound(soundUri) // Thêm âm thanh
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        // Gửi thông báo

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }
}