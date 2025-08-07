package com.example.rfid_bai_giu_xe;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
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

public class lich_su_ra_vao extends AppCompatActivity {

    private ArrayList<Lichsuxe> lichsuxe;
    private lichsuAdapter adapter;
    private ImageButton btn_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lich_su_ra_vao);

        ListView listView = findViewById(R.id.Lv_lichsu);
        Button btn_bienso = findViewById(R.id.btn_bienso);
        Button btn_danhsach = findViewById(R.id.btn_danhsach);
        Button btn_lichsu = findViewById(R.id.btn_lichsu);
        btn_delete = findViewById(R.id.btn_delete);
        lichsuxe = new ArrayList<>();
        adapter = new lichsuAdapter(this,R.layout.item_lich_su_xe,lichsuxe);
        listView.setAdapter(adapter);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child("history").removeValue();
                lichsuxe.clear();
            }
        });

        // Lấy danh sách xe trong bãi
        dbRef.child("history").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot vehicleSnapshot : snapshot.getChildren()) {
                    String cardId = vehicleSnapshot.child("card_id").getValue(String.class);
                    //String status = vehicleSnapshot.child("status").getValue(String.class);
                    String licensePlate = vehicleSnapshot.child("license_plate").getValue(String.class);
                    String entryTime = vehicleSnapshot.child("entry_time").getValue(String.class);
                    String exitTime = vehicleSnapshot.child("exit_time").getValue(String.class);
                    String action = vehicleSnapshot.child("action").getValue(String.class);

                    if ( licensePlate != null && entryTime != null) {
                        lichsuxe.add(new Lichsuxe(cardId, licensePlate,entryTime,exitTime,action));
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
                startActivity(new Intent(lich_su_ra_vao.this, MainActivity.class));
            }
        });

        btn_danhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(lich_su_ra_vao.this, danh_sach_xe.class));
            }
        });
    }
}