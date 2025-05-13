package com.example.quanlythuvien.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlythuvien.adapter.AdapterSach.Sach;
import com.example.quanlythuvien.databases.DB;

import java.util.ArrayList;
import java.util.List;

public class DAOSach {
    private SQLiteDatabase database;

    public DAOSach(Context context){

        DB db = new DB(context);
        database = db.getWritableDatabase();
    }

    public int getMaSach(String ten){
        int ma=0;
        String sql = "select MaSach from Sach where TenSach=?";
        Cursor c = database.rawQuery(sql, new String[]{ten});
        if(c != null && c.moveToFirst()){
            ma = c.getInt(0);
            c.close();
        }
        return ma;
    }

    public List<String> getTenTheLoai (){
        String sql = "select TenTheLoai from TheLoaiSach";
        Cursor c = database.rawQuery(sql, null);
        List<String> listtheloai = new ArrayList<>();
        while(c.moveToNext()){
            listtheloai.add(c.getString(0));
        }
        return listtheloai;
    }

    public String getMaTheLoai(String tenTheLoai) {
        String ketQua = "";
        String sql = "SELECT MaTheLoai FROM TheLoaiSach WHERE TenTheLoai=?";
        Cursor c = database.rawQuery(sql, new String[]{tenTheLoai});

        if (c != null && c.moveToFirst()) {

            ketQua = c.getString(0);
            c.close();
        }

        return ketQua;
    }


    public List<String> getNhaXB(){
        List<String> listnhaxb = new ArrayList<>();
        String sql = "select TenNXB from NhaXuatBan";
        Cursor c = database.rawQuery(sql, null);
        while (c.moveToNext()){
            listnhaxb.add(c.getString(0));
        }
        return listnhaxb;
    }

    public List<String> getAlltacgia(){
        List<String> listtacgia = new ArrayList<>();
        String sql = "select TenTacGia from TacGia";
        Cursor c = database.rawQuery(sql, null);
        while (c.moveToNext()){
            listtacgia.add(c.getString(0));
        }
        return listtacgia;
    }

    public String getManhaXB (String tennhaxb){
        String kq="";
        String sql = "select MaNXB from NhaXuatBan where TenNXB=?";
        Cursor c = database.rawQuery(sql, new String[]{tennhaxb});
        if (c != null && c.moveToFirst()) {

            kq = c.getString(0);
            c.close();
        }

        return kq;
    }

    public String getMaTacGia (String ten){
        String kq="";
        String sql = "select MaTacGia from TacGia where TenTacGia=?";
        Cursor c = database.rawQuery(sql, new String[]{ten});
        if (c != null && c.moveToFirst()) {

            kq = c.getString(0);
            c.close();
        }

        return kq;
    }


    public long them_sach (Sach obj){
        String sql = "select TenSach from Sach where MaTheLoai=? and MaTacGia=? and NgayXuatBan=? and MaNXB=?";
        Cursor c = database.rawQuery(sql, new String[]{obj.getMaTheLoai()+"", obj.getMatacGia()+"", obj.getNgayXuatBan(), obj.getManxb()+""});
        if(c != null && c.moveToFirst()){
            String tensach = obj.getTenSach().toLowerCase().trim();
            String tensachtrave = c.getString(0).toLowerCase().trim();
            c.close();
            if(tensach.equals(tensachtrave)){
                return -2;
            }
            else{
                ContentValues values = new ContentValues();
                values.put("TenSach", obj.getTenSach());
                values.put("MaTheLoai", obj.getMaTheLoai());
                values.put("TomTat", obj.getTomTat());
                values.put("MaTacGia",obj.getMatacGia());
                values.put("NgayXuatBan",obj.getNgayXuatBan());
                values.put("MaNXB", obj.getManxb());
                values.put("SoLuong", obj.getSoLuong());
                values.put("Anh", obj.getHinhAnh());
                return database.insert("Sach", null, values);
            }
        }
        else{
            c.close();
            ContentValues values = new ContentValues();
            values.put("TenSach", obj.getTenSach());
            values.put("MaTheLoai", obj.getMaTheLoai());
            values.put("TomTat", obj.getTomTat());
            values.put("MaTacGia",obj.getMatacGia());
            values.put("NgayXuatBan",obj.getNgayXuatBan());
            values.put("MaNXB", obj.getManxb());
            values.put("SoLuong", obj.getSoLuong());
            values.put("Anh", obj.getHinhAnh());
            return database.insert("Sach", null, values);
        }
    }

    public int sua_sach(Sach obj){
        String sql = "select TenSach from Sach where TenSach=? and MaTheLoai=? and MaTacGia=? and NgayXuatBan=? and MaNXB=?";
        Cursor c = database.rawQuery(sql, new String[]{obj.getTenSach(),obj.getMaTheLoai()+"", obj.getMatacGia()+"", obj.getNgayXuatBan(), obj.getManxb()+""});
        if(c != null && c.moveToFirst()){
            String tensach = obj.getTenSach().toLowerCase().trim();
            String tensachtrave = c.getString(0).toLowerCase().trim();
            c.close();
            if(tensach.equals(tensachtrave)){
                return -2;
            }
            else{
                ContentValues values = new ContentValues();
                values.put("TenSach", obj.getTenSach());
                values.put("MaTheLoai", obj.getMaTheLoai());
                values.put("TomTat", obj.getTomTat());
                values.put("NgayXuatBan", obj.getNgayXuatBan());
                values.put("MaTacGia",obj.getMatacGia());
                values.put("MaNXB", obj.getManxb());
                values.put("SoLuong", obj.getSoLuong());
                values.put("Anh", obj.getHinhAnh());
                return database.update("Sach",  values, "MaSach=?", new String[]{obj.getMaSach()+""});
            }
        }
        else{
            ContentValues values = new ContentValues();
            values.put("TenSach", obj.getTenSach());
            values.put("MaTheLoai", obj.getMaTheLoai());
            values.put("TomTat", obj.getTomTat());
            values.put("NgayXuatBan", obj.getNgayXuatBan());
            values.put("MaTacGia",obj.getMatacGia());
            values.put("MaNXB", obj.getManxb());
            values.put("SoLuong", obj.getSoLuong());
            values.put("Anh", obj.getHinhAnh());
            return database.update("Sach",  values, "MaSach=?", new String[]{obj.getMaSach()+""});
        }
    }

    public int xoa_sach(int id){
        String sql = "select count(*) from MuonSach where MaSach=? group by MaSach";
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(id)});
        if(c != null && c.moveToFirst()){
            return -1;
        }
        else{
            return database.delete("Sach","MaSach=?", new String[]{id+""});
        }
    }

    @SuppressLint("Range")
    public List<Sach> getListSach(String sql, String ...selectionArgs){
        List<Sach> list = new ArrayList<>();
        Cursor c = database.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            Sach obj = new Sach();
            obj.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("MaSach"))));
            obj.setTenSach(c.getString(c.getColumnIndex("TenSach")));
            obj.setMaTheLoai(Integer.parseInt(c.getString(c.getColumnIndex("MaTheLoai"))));
            obj.setTomTat(c.getString(c.getColumnIndex("TomTat")));
            obj.setMatacGia(Integer.parseInt(c.getString(c.getColumnIndex("MaTacGia"))));
            obj.setNgayXuatBan(c.getString(c.getColumnIndex("NgayXuatBan")));
            obj.setManxb(Integer.parseInt(c.getString(c.getColumnIndex("MaNXB"))));
            obj.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("SoLuong"))));
            byte[] hinhAnh = c.getBlob(c.getColumnIndex("Anh"));
            if (hinhAnh != null) {
                obj.setHinhAnh(hinhAnh);
            }
            list.add(obj);
        }
        c.close();
        return list;
    }

    public List<Sach> getAll(){
        String sql = "select * from Sach";
        return getListSach(sql);
    }

    public String gettenthloai(int ma){
        String ten="";
        String sql = "select TenTheLoai from TheLoaiSach where MaTheLoai=?";
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(ma)});
        if (c != null && c.moveToFirst()) {
            ten = c.getString(0);
        }
        return ten;
    }
    public String gettennhaxb(int ma){
        String ten="";
        String sql = "select TenNXB from NhaXuatBan where MaNXB=?";
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(ma)});
        if (c != null && c.moveToFirst()) {
            ten = c.getString(0);
        }
        return ten;
    }


    @SuppressLint("Range")
    public String get_tacgia(int ma){
        String tenTacGia = "";
        String sql = "select TenTacGia from TacGia where MaTacGia=?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(ma)});
        if (cursor != null && cursor.moveToFirst()) {
            tenTacGia = cursor.getString(0);
            cursor.close();
        }
        return tenTacGia;
    }

    public String getTenSach (int ma){
        String name = "";
        String sql = "select TenSach from Sach where MaSach=?";
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(ma)});
        if(c!= null && c.moveToFirst()){
            name = c.getString(0);
        }
        return name;
    }
}
