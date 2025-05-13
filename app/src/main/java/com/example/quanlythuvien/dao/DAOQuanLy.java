package com.example.quanlythuvien.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import com.example.quanlythuvien.databases.DB;

public class DAOQuanLy {
    private SQLiteDatabase database;
    public DAOQuanLy(Context ct){
        DB db = new DB(ct);
        database = db.getWritableDatabase();
    }
    public int getMaQuanLy(String tendangnhap){
        int ma =0;
        String sql = "select MaQuanLy from QuanLy where TenDangNhap =?";
        Cursor c = database.rawQuery(sql, new String[]{tendangnhap});
        if(c!=null && c.moveToFirst()){
            ma = c.getInt(0);
        }
        return ma;
    }
    public String getTenQuanLy(int maquanly){
        String ten="Error";
        String sql = "select TenDangNhap from QuanLy where MaQuanLy=?";
        Cursor c = database.rawQuery(sql, new String[]{maquanly+""});
        if(c!=null && c.moveToFirst()){
            ten=c.getString(0);
        }
        return ten;
    }
    // Trả về Cursor hoặc QuanLy object (nếu có model)
    public Cursor getThongTinQuanLyTheoTenDangNhap(String tenDangNhap) {
        String sql = "SELECT * FROM QuanLy WHERE TenDangNhap = ?";
        return database.rawQuery(sql, new String[]{tenDangNhap});
    }

    // Cập nhật thông tin cá nhân
    public boolean capNhatThongTinQuanLy(String tenDangNhap, ContentValues values) {
        int rows = database.update("QuanLy", values, "TenDangNhap = ?", new String[]{tenDangNhap});
        return rows > 0;
    }





}
