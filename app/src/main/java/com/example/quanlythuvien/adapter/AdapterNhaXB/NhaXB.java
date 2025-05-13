package com.example.quanlythuvien.adapter.AdapterNhaXB;

public class NhaXB {
    private int manxb;
    private String tennxb;
    private String diachi;
    private String quocgia;

    public NhaXB(){}

    public NhaXB(int manxb, String tennxb, String diachi, String quocgia) {
        this.manxb = manxb;
        this.tennxb = tennxb;
        this.diachi = diachi;
        this.quocgia = quocgia;
    }
    public NhaXB(String tennxb, String diachi, String quocgia) {
        this.tennxb = tennxb;
        this.diachi = diachi;
        this.quocgia = quocgia;
    }

    public int getManxb() {
        return manxb;
    }

    public void setManxb(int manxb) {
        this.manxb = manxb;
    }

    public String getTennxb() {
        return tennxb;
    }

    public void setTennxb(String tennxb) {
        this.tennxb = tennxb;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getQuocgia() {
        return quocgia;
    }

    public void setQuocgia(String quocgia) {
        this.quocgia = quocgia;
    }
}
