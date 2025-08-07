package com.example.rfid_bai_giu_xe;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;
import java.text.SimpleDateFormat;
import android.content.Intent;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference dbRef;
    private TextView cardIdText,text_bienso,txt_soluong;
    private EditText ed_bienso;
    private Button btn_danhsach;
    private Button btn_lichsu;
    private Button btn_tuchoi;
    private Button btn_xera;
    private Button btn_xevao;
    private String currentCardId;
    private String currentAction;
    boolean Flag_id= false;
    private ValueEventListener vehicleCountListener; // Listener cho số lượng xe
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_bienso = findViewById(R.id.ed_bienso);
        cardIdText = findViewById(R.id.txt_numberID);
        text_bienso = findViewById(R.id.text_bienso);
        txt_soluong = findViewById(R.id.txt_soluong);
        btn_tuchoi =findViewById(R.id.btn_tuchoi);
        btn_danhsach =findViewById(R.id.btn_danhsach);
        btn_lichsu =findViewById(R.id.btn_lichsu);
        btn_xera =findViewById(R.id.btn_xera);
        btn_xevao =findViewById(R.id.btn_xevao);

        dbRef = FirebaseDatabase.getInstance().getReference();

        vehicleCountListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    long vehicleCount = snapshot.getChildrenCount();
                    runOnUiThread(() -> txt_soluong.setText("Số xe hiện có: " + vehicleCount));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dbRef.child("vehicles").addValueEventListener(vehicleCountListener);

        dbRef.child("notifications").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String cardId = child.child("cardId").getValue(String.class);
                    String action = child.child("action").getValue(String.class);

                    if (cardId != null && action != null) {
                        currentAction = action;
                        currentCardId = cardId;
                    }
                }
                    if (currentCardId != null && currentAction != null) {


                        if ("entry".equals(currentAction)) {

                            dbRef.child("vehicles").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot child : snapshot.getChildren()){
                                        String cardId = child.getKey();
                                        //Toast.makeText(MainActivity.this, cardId + currentCardId, Toast.LENGTH_SHORT).show();
                                        if(cardId.equals(currentCardId)){
                                            Flag_id = true;
                                            break;
                                        }
                                        else Flag_id = false;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            if(Flag_id == false) {
                                // Xe vào: Hiển thị card_id và ô nhập biển số
                                cardIdText.setText(currentCardId);
                                ed_bienso.setText("");
                                ed_bienso.setVisibility(View.VISIBLE);
                                text_bienso.setVisibility(View.GONE);
                                btn_xevao.setVisibility(View.VISIBLE);
                                btn_xera.setVisibility(View.GONE);
                                btn_tuchoi.setVisibility(View.VISIBLE);

                            }
                            else if (Flag_id == true){
                                // Xe vào: Hiển thị card_id và ô nhập biển số
                                cardIdText.setText(currentCardId);
                                ed_bienso.setVisibility(View.GONE);
                                text_bienso.setVisibility(View.VISIBLE);
                                text_bienso.setText("Xe đã có trong bãi");
                                btn_xevao.setVisibility(View.GONE);
                                btn_xera.setVisibility(View.GONE);
                                btn_tuchoi.setVisibility(View.INVISIBLE);
                            }

                        }

                        else if ("exit".equals(currentAction)) {
                            // Xe ra: Kiểm tra xe đã vào chưa
                            dbRef.child("vehicles").child(currentCardId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot vehicleSnapshot) {
                                    String status = vehicleSnapshot.child("status").getValue(String.class);
                                    String licensePlate = vehicleSnapshot.child("license_plate").getValue(String.class);

                                    if ("entered".equals(status) && licensePlate != null) {
                                        cardIdText.setText(currentCardId);
                                        ed_bienso.setVisibility(View.GONE);
                                        text_bienso.setVisibility(View.VISIBLE);
                                        text_bienso.setText(licensePlate);
                                        btn_xevao.setVisibility(View.GONE);
                                        btn_xera.setVisibility(View.VISIBLE);
                                        btn_tuchoi.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        cardIdText.setText("Không có xe" + currentCardId);
                                        ed_bienso.setVisibility(View.GONE);
                                        text_bienso.setVisibility(View.GONE);
                                        btn_xevao.setVisibility(View.GONE);
                                        btn_xera.setVisibility(View.GONE);
                                        btn_tuchoi.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}

                            });

                    }

                }
            }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
        });


        btn_xevao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String licensePlate = ed_bienso.getText().toString().trim();
                if (!licensePlate.isEmpty() && currentCardId != null) {
                    String entryTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Map<String, Object> vehicleData = new HashMap<>();
                    vehicleData.put("status", "entered");
                    vehicleData.put("license_plate", licensePlate);
                    vehicleData.put("entry_time", entryTime);

                    // Cập nhật thông tin xe
                    dbRef.child("vehicles").child(currentCardId).updateChildren(vehicleData);

                    // Ghi lịch sử
                    String historyKey = currentCardId + "_" + System.currentTimeMillis();
                    Map<String, Object> historyData = new HashMap<>();
                    historyData.put("card_id", currentCardId);
                    historyData.put("license_plate", licensePlate);
                    historyData.put("entry_time", entryTime);
                    historyData.put("action", "entered");
                    dbRef.child("history").child(historyKey).setValue(historyData);

                    Toast.makeText(MainActivity.this, "Entry recorded", Toast.LENGTH_SHORT).show();
                    resetUI();
                }
                else {
                    Toast.makeText(MainActivity.this, "Please enter license plate", Toast.LENGTH_SHORT).show();
                }
                dbRef.child("notifications").removeValue();
            }
        });

        // Xử lý nút Xe Ra
        btn_xera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCardId != null) {
                    String exitTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    //Map<String, Object> vehicleData = new HashMap<>();
                    //vehicleData.put("exit_time", exitTime);
                    //vehicleData.put("status", "exited");

                    // Cập nhật thông tin xe
                    //dbRef.child("vehicles").child(currentCardId).updateChildren(vehicleData);

                    // Ghi lịch sử
                    dbRef.child("vehicles").child(currentCardId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String licensePlate = snapshot.child("license_plate").getValue(String.class);
                            String entryTime = snapshot.child("entry_time").getValue(String.class);

                            String historyKey = currentCardId + "_" + System.currentTimeMillis();
                            Map<String, Object> historyData = new HashMap<>();
                            historyData.put("card_id", currentCardId);
                            historyData.put("license_plate", licensePlate);
                            historyData.put("entry_time", entryTime);
                            historyData.put("exit_time", exitTime);
                            historyData.put("action", "exited");
                            dbRef.child("history").child(historyKey).setValue(historyData);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {}
                    });
                    dbRef.child("vehicles").child(currentCardId).removeValue();
                    dbRef.child("notifications").removeValue();
                    Toast.makeText(MainActivity.this, "Exit recorded", Toast.LENGTH_SHORT).show();
                    resetUI();
                }
            }
        });


        btn_danhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, danh_sach_xe.class));
            }
        });

        btn_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, lich_su_ra_vao.class));
            }
        });

        btn_tuchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetUI();
                dbRef.child("notifications").removeValue();
            }
        });
    }




    private void resetUI() {

//            cardIdText.setText("");
//            ed_bienso.setText("");
            currentCardId = null;
            currentAction = null;
            ed_bienso.setVisibility(View.GONE);
            text_bienso.setText("");
            cardIdText.setText(currentCardId);
            text_bienso.setVisibility(View.GONE);
            btn_xera.setVisibility(View.GONE);
            btn_xevao.setVisibility(View.GONE);

            btn_tuchoi.setVisibility(View.INVISIBLE);

    }

}