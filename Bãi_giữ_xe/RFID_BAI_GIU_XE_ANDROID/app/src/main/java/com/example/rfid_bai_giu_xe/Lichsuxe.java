package com.example.rfid_bai_giu_xe;

public class Lichsuxe {
    private String id;
    private String bienso;
    private String vaoluc;
    private String raluc;

    private String action;
    public Lichsuxe(){}
    public Lichsuxe(String id, String bienso, String vaoluc,String raluc, String action){
        this.id = id;
        this.bienso = bienso;
        this.vaoluc = vaoluc;
        this.raluc  = raluc;
        this.action = action;
    }

    public String getId(){
        return id;
    }

    public String getBienSo(){
        return bienso;
    }

    public String getVaoLuc(){
        return vaoluc;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setBienSo(String bienso){
        this.bienso = bienso;
    }

    public void setVaoLuc(String vaoluc){
        this.vaoluc = vaoluc;
    }

    public String getRaluc(){
        return raluc;
    }

    public void setRaluc(String raluc){
        this.raluc = raluc;
    }

    public String getAction(){
        return action;
    }

    public void setAction(String action){
        this.action = action;
    }


}
