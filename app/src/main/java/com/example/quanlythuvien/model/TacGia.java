package com.example.quanlythuvien.model;

public class TacGia {
    private Integer MaTacGia;
    private String TenTacGia;
    private String GioiTinh;

    public TacGia(Integer maTacGia, String tenTacGia, String gioiTinh) {
        MaTacGia = maTacGia;
        TenTacGia = tenTacGia;
        GioiTinh = gioiTinh;
    }

    public TacGia() {
    }

    public Integer getMaTacGia() {
        return MaTacGia;
    }

    public void setMaTacGia(Integer maTacGia) {
        MaTacGia = maTacGia;
    }

    public String getTenTacGia() {
        return TenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        TenTacGia = tenTacGia;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }
}
