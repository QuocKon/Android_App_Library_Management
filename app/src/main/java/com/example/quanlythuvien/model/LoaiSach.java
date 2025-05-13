package com.example.quanlythuvien.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlythuvien.database.DatabaseSingleton;

public class LoaiSach {
    private Integer MaTheLoai;
    private String TenTheLoai;

    public LoaiSach() {
    }

    public LoaiSach(Integer maTheLoai, String tenTheLoai) {
        MaTheLoai = maTheLoai;
        TenTheLoai = tenTheLoai;
    }

    public Integer getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(Integer maTheLoai) {
        MaTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }

    public int countSach(Integer ID ){
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TheLoaiSach JOIN Sach ON TheLoaiSach.MaTheLoai = Sach.MaTheLoai WHERE Sach.MaTheLoai = ?",  new String[]{String.valueOf(ID)} );
        int soluong;
        soluong = cursor.getCount();
        cursor.close();
        return  soluong;
   }
}
