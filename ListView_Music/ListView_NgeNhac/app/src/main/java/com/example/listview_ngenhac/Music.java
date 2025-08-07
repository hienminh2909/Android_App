package com.example.listview_ngenhac;

public class Music {
    private String name;
    private String singer;
    private int song;
    private int img_singer;
    public Music(String name, String singer, int song, int img_singer){
        this.name = name;
        this.singer = singer;
        this.song = song;
        this.img_singer = img_singer;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getSinger(){
        return singer;
    }
    public void setSinger(String singer){
        this.singer = singer;
    }

    public int getSong(){
        return song;
    }
    public void setSong(int song){
        this.song = song;
    }

    public int getImg_singer() {
        return img_singer;
    }

    public void setImg_singer() {
        this.img_singer = img_singer;
    }
}
