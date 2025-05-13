package com.example.quanlythuvien.adapter.AdapterMuonSach;

public class MuonSach {
    private int mamuonsach;
    private int soluong;
    private int maquanly;
    private int masach;
    private String ngaymuon;
    private String ngaytra;
    private String tinhtrang;
    private int phuphi;
    public MuonSach(){}

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public MuonSach(int mamuonsach, int maquanly, int masach, String ngaymuon, String ngaytra, String tinhtrang, int phuphi, int sl) {
        this.mamuonsach = mamuonsach;
        this.maquanly = maquanly;
        this.masach = masach;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
        this.tinhtrang = tinhtrang;
        this.phuphi = phuphi;
        this.soluong = sl;
    }
    public MuonSach(int maquanly, int masach, String ngaymuon, String ngaytra, String tinhtrang, int phuphi, int sl) {
        this.maquanly = maquanly;
        this.masach = masach;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
        this.tinhtrang = tinhtrang;
        this.phuphi = phuphi;
        this.soluong = sl;
    }

    public int getMamuonsach() {
        return mamuonsach;
    }

    public void setMamuonsach(int mamuonsach) {
        this.mamuonsach = mamuonsach;
    }

    public int getMaquanly() {
        return maquanly;
    }

    public void setMaquanly(int maquanly) {
        this.maquanly = maquanly;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(String ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public String getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(String ngaytra) {
        this.ngaytra = ngaytra;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public int getPhuphi() {
        return phuphi;
    }

    public void setPhuphi(int phuphi) {
        this.phuphi = phuphi;
    }
}
