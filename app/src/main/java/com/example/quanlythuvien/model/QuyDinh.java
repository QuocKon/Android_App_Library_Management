package com.example.quanlythuvien.model;

public class QuyDinh {
    private Integer MaQuyDinh;
    private String NoiDung;
    private byte[] Anh;

    public QuyDinh(Integer maQuyDinh, String noiDung, byte[] anh) {
        MaQuyDinh = maQuyDinh;
        NoiDung = noiDung;
        Anh = anh;
    }

    public QuyDinh() {
    }

    public Integer getMaQuyDinh() {
        return MaQuyDinh;
    }

    public void setMaQuyDinh(Integer maQuyDinh) {
        MaQuyDinh = maQuyDinh;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public byte[] getAnh() {
        return Anh;
    }

    public void setAnh(byte[] anh) {
        Anh = anh;
    }
}
