package com.example.list_music;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView list_music;
    private ArrayList<Music> arrayList;
    private CustomMusicAdpter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        list_music =  findViewById(R.id.List_Music);
        arrayList  = new ArrayList<>();
        arrayList.add(new Music("Chạy ngay đi", "Sơn Tùng",R.raw.chayngaydi,R.drawable.son_tung));
        arrayList.add(new Music("Em là không thể", "Anh Quân Idol",R.raw.elakhongthe,R.drawable.anh_quan_idol));
        arrayList.add(new Music("Em muốn ta là gì", "Thanh Hưng",R.raw.emmuontalagi,R.drawable.thanh_hung));
        arrayList.add(new Music("Khó vẽ nụ cười", "ĐạtG, DuUyen",R.raw.khovenucuoi,R.drawable.dat_g));
        arrayList.add(new Music("Mất kết nối", "Dương Domic",R.raw.matketnoi,R.drawable.duonh_domic));

        adapter = new CustomMusicAdpter(this,R.layout.activity_music_items, arrayList);
        list_music.setAdapter(adapter);

    }
}