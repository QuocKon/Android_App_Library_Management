package com.example.quanlythuvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import android.database.Cursor;

import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.LoaiSach;

public class LoaiSachDAO {
    private Context context;
    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();

    public LoaiSachDAO(Context context) {
        this.context = context;
    }


    public  long insert(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("TenTheLoai", loaiSach.getTenTheLoai());
        try {
            return sqLiteDatabase.insert("TheLoaiSach", null, values);
        } catch (Exception e){
            Log.d("Can't insert new The Loai Sach", e.getMessage());
            return 0;
        }
    }


    public  long update(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("TenTheLoai", loaiSach.getTenTheLoai());
        try {
            return sqLiteDatabase.update("TheLoaiSach", values, "MaTheLoai=?", new String[]{String.valueOf(loaiSach.getMaTheLoai())});
        } catch (Exception e){
            Log.d("Can't update new The Loai Sach", e.getMessage());
            return 0;
        }
    }

    public int delete(Integer id){
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        LoaiSach loaiSach = new LoaiSach();
        int soluong = loaiSach.countSach(id);
        if(soluong==0){
            return sqLiteDatabase.delete("TheLoaiSach", "MaTheLoai=?", new String[]{String.valueOf(id)});
        }else{
            return 0;
        }
    }
    public ArrayList<LoaiSach> getAll() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TheLoaiSach", null);
        while (cursor.moveToNext()) {
            list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
        }
        cursor.close();
        return list;
    }

    public String getTenTheLoaiById(int id) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT TenTheLoai FROM TheLoaiSach WHERE MaTheLoai=?", new String[]{String.valueOf(id)});
        String name = "";
        if (cursor.moveToFirst()) name = cursor.getString(0);
        cursor.close();
        return name;
    }


}
