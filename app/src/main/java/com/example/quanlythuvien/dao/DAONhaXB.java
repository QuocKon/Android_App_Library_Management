package com.example.quanlythuvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlythuvien.model.NhaXB;
import com.example.quanlythuvien.databases.DB;

import java.util.ArrayList;

public class DAONhaXB {
    private SQLiteDatabase database;
    public  DAONhaXB (Context context){
        DB db = new DB(context);
        database = db.getWritableDatabase();
    }
    public ArrayList<NhaXB> getAllNhaXB(){
        ArrayList<NhaXB> listanhaxb = new ArrayList<>();
        String sql = "select * from NhaXuatBan";
        Cursor c = database.rawQuery(sql, null);
        if(c==null){
            return null;
        }
        else{
            while (c.moveToNext()){
                NhaXB nhaxb = new NhaXB();
                nhaxb.setManxb(c.getInt(0));
                nhaxb.setTennxb(c.getString(1));
                nhaxb.setDiachi(c.getString(2));
                nhaxb.setQuocgia(c.getString(3));
                listanhaxb.add(nhaxb);
            }
            c.close();
        }
        return listanhaxb;
    }

    public long insert_nhaxb(NhaXB obj){
        String sqlkiemtra = "select * from NhaXuatBan where TenNXB=? and DiaChi=? and QuocGia=?";
        Cursor c = database.rawQuery(sqlkiemtra, new String[]{obj.getTennxb(), obj.getDiachi(), obj.getQuocgia()});
        if(c!=null && c.moveToFirst()){
            return 0;
        }
        else{
            ContentValues contentValues = new ContentValues();
            contentValues.put("TenNXB",obj.getTennxb());
            contentValues.put("DiaChi", obj.getDiachi());
            contentValues.put("QuocGia",obj.getQuocgia());
            return database.insert("NhaXuatBan",null,contentValues);
        }
    }

    public int update_nhaxb(NhaXB obj){
            ContentValues contentValues = new ContentValues();
            contentValues.put("TenNXB",obj.getTennxb());
            contentValues.put("DiaChi", obj.getDiachi());
            contentValues.put("QuocGia",obj.getQuocgia());
            return database.update("NhaXuatBan",contentValues,"MaNXB=?", new String[]{obj.getManxb()+""});
    }

    public int delete_nhaXB(int ma){
        String ktra = "select * from Sach where MaNXB=?";
        Cursor c = database.rawQuery(ktra, new String[]{ma+""});
        if(c!=null && c.moveToFirst()){
            return 0;
        }
        else{
            return database.delete("NhaXuatBan","MaNXB=?", new String[]{ma+""});
        }
    }
}
