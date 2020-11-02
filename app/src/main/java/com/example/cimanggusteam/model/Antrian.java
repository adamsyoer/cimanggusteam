package com.example.cimanggusteam.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Antrian {

    @SerializedName("catatan")
    @Expose
    private String catatan;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("jeniskendaraan")
    @Expose
    private String jeniskendaraan;
    @SerializedName("nourut")
    @Expose
    private String nourut;
    @SerializedName("platkendaraan")
    @Expose
    private String platkendaraan;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("waktusteam")
    @Expose
    private String waktusteam;


    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJeniskendaraan() {
        return jeniskendaraan;
    }

    public void setJeniskendaraan(String jeniskendaraan) {
        this.jeniskendaraan = jeniskendaraan;
    }

    public String getNourut() {
        return nourut;
    }

    public void setNourut(String nourut) {
        this.nourut = nourut;
    }

    public String getPlatkendaraan() {
        return platkendaraan;
    }

    public void setPlatkendaraan(String platkendaraan) {
        this.platkendaraan = platkendaraan;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWaktusteam() {
        return waktusteam;
    }

    public void setWaktusteam(String waktusteam) {
        this.waktusteam = waktusteam;
    }

}