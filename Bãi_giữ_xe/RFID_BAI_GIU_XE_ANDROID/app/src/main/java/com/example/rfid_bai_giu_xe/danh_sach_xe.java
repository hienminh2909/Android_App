package com.example.rfid_bai_giu_xe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class danh_sach_xe extends AppCompatActivity {
    private String id,bienSo,vaoLuc;
    private ArrayList<XeDangGui> danhSachXe;
    private XeAdapter adapter;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_sach_xe);

        ListView listView = findViewById(R.id.listViewxe);
        Button btn_bienso = findViewById(R.id.btn_bienso);
        //Button btn_danhsach = findViewById(R.id.btn_danhsach);
        Button btn_lichsu = findViewById(R.id.btn_lichsu);

        dbRef = FirebaseDatabase.getInstance().getReference();
        danhSachXe = new ArrayList<>();
        adapter = new XeAdapter(this,R.layout.item_xe,danhSachXe,dbRef);
        listView.setAdapter(adapter);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();



        // Lấy danh sách xe trong bãi
        dbRef.child("vehicles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot vehicleSnapshot : snapshot.getChildren()) {
                    String cardId = vehicleSnapshot.getKey();
                    //String status = vehicleSnapshot.child("status").getValue(String.class);
                    String licensePlate = vehicleSnapshot.child("license_plate").getValue(String.class);
                    String entryTime = vehicleSnapshot.child("entry_time").getValue(String.class);

                    if ( licensePlate != null && entryTime != null) {
                        danhSachXe.add(new XeDangGui(cardId, licensePlate,entryTime));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        btn_bienso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(danh_sach_xe.this, MainActivity.class));
            }
        });

        btn_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(danh_sach_xe.this, lich_su_ra_vao.class));
            }
        });
    }

}