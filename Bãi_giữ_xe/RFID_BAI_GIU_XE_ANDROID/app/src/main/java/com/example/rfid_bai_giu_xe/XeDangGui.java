package com.example.rfid_bai_giu_xe;

public class XeDangGui {
    private String id;
    private String bienso;
    private String vaoluc;

    public XeDangGui(){}
    public XeDangGui(String id, String bienso, String vaoluc){
        this.id = id;
        this.bienso = bienso;
        this.vaoluc = vaoluc;
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


}
