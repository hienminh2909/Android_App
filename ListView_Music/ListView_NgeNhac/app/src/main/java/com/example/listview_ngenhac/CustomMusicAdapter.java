package com.example.listview_ngenhac;

import android.icu.text.Transliterator;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomMusicAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Music> arrayList;
    private MediaPlayer mediaPlayer;
    private Boolean flag =true;
    public CustomMusicAdapter(Context context, int layout, ArrayList<Music> arrayList)
    {
        this.context = context;
        this.layout  = layout;
        this.arrayList = arrayList;

    }

    @Override
    public int getCount(){
        //Cần trả về số phần tử mà ListView hien thị
        return arrayList.size();
    }

    @Override
    public Object getItem(int position){
        //Cần trả về đối tượng dữ liệu phần tử ở vị trí position
        return arrayList.get(position);
    }

    @Override

    public long getItemId(int position){
        //Trả về một ID liên quan đến phần tử ở vị trí position
        return position;
    }

    private class ViewHoler{
        TextView txt_name, txt_singer;
        ImageView img_play, img_stop, img_singer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup content){
        final ViewHoler viewholer;
        if(convertView == null){  //convertView là View hiện thị phần tử, nếu là null cần tạo mới
            viewholer = new ViewHoler();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView =layoutInflater.inflate(layout, null);
            viewholer.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            viewholer.txt_singer = (TextView) convertView.findViewById(R.id.txt_singer);
            viewholer.img_play = (ImageView) convertView.findViewById(R.id.img_play);
            viewholer.img_stop = (ImageView) convertView.findViewById(R.id.img_stop);
            viewholer.img_singer= (ImageView) convertView.findViewById(R.id.img_singer);

            convertView.setTag(viewholer);
        }
        else{
            viewholer = (ViewHoler) convertView.getTag();

        }

        final Music music = arrayList.get(position);

        viewholer.txt_name.setText(music.getName());
        viewholer.txt_singer.setText(music.getSinger());
        viewholer.img_singer.setImageResource(music.getImg_singer());
        // play music
        viewholer.img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    mediaPlayer = MediaPlayer.create(context, music.getSong());
                    flag = false;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    viewholer.img_play.setImageResource(R.drawable.nut_phat);
                }
                else{
                    mediaPlayer.start();
                    viewholer.img_play.setImageResource(R.drawable.nut_pause);
                }
            }
        });

        // stop music
        viewholer.img_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    flag =true;
                }
                viewholer.img_play.setImageResource(R.drawable.nut_phat);
            }
        });
        return convertView;
    }

}

