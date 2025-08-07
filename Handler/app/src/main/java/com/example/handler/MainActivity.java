package com.example.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView txt_phantram, txt_xephang;
    ImageView img_back,img_lq;
    Button btn_caidat, btn_danhgia;
    ProgressBar thanhtai;
    RatingBar ratingBar;
    private Boolean isRunning = false;
    int Max_sec =100;
    float stars ;

    Handler myHanler = new Handler(Looper.getMainLooper()){
        public void handleMessage (Message msg){
            thanhtai.incrementProgressBy(5);
            if((thanhtai.getProgress() == thanhtai.getMax()) && isRunning == true){
                txt_phantram.setText("Đã cài đặt");
                thanhtai.setVisibility(View.INVISIBLE);
                btn_caidat.setVisibility(View.VISIBLE);
                btn_caidat.setText("Gỡ cài đặt");
            }
            else if((thanhtai.getProgress() == thanhtai.getMax()) && isRunning == false){
                txt_phantram.setText("Chưa cài đặt");
                thanhtai.setVisibility(View.INVISIBLE);
                btn_caidat.setVisibility(View.VISIBLE);
                btn_caidat.setText("Cài đặt");
            }
            else{
                txt_phantram.setText(thanhtai.getProgress() + "%");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btn_caidat = (Button) findViewById(R.id.btn_caidat);
        btn_danhgia = (Button) findViewById(R.id.btn_danhgia);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txt_phantram =(TextView) findViewById(R.id.txt_phantram);
        txt_xephang = (TextView) findViewById(R.id.txt_xephang);
        thanhtai = (ProgressBar) findViewById(R.id.thanhtai);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                stars = ratingBar.getRating();
                Toast.makeText(MainActivity.this, "Ban da danh gia" + rating + "sao",Toast.LENGTH_SHORT).show();
            }
        });

        btn_caidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhtai.setProgress(0);
                btn_caidat.setVisibility(View.INVISIBLE);
                thanhtai.setVisibility(View.VISIBLE);
                txt_phantram.setVisibility(View.VISIBLE);

                final Thread backgroudThread = new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            for (int i =0 ; i< Max_sec; i++){
                                if (isRunning == true) Thread.sleep(1500);
                                else Thread.sleep(100);
                                Message msg = myHanler.obtainMessage();
                                myHanler.sendMessage(msg);
                            }
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                isRunning = !isRunning;
                backgroudThread.start();
            }
        });

        btn_danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"Bạn đã đánh giá " + stars + "sao",Toast.LENGTH_SHORT).show();
                btn_danhgia.setVisibility(View.INVISIBLE);
                txt_xephang.setText("Bạn đã đánh giá " + stars + "sao");
                ratingBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}