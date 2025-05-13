package com.example.quanlythuvien.model;

public class VeUngDung {
    private int ID;
    private String NoiDung;

    public VeUngDung(int ID, String noiDung) {
        this.ID = ID;
        NoiDung = noiDung;
    }

    public VeUngDung() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }
}