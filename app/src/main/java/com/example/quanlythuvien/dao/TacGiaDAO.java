package com.example.quanlythuvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.TacGia;

public class TacGiaDAO {

    private Context context;
    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();

    public TacGiaDAO(Context context) {
        this.context = context;
    }

    public  long insert(TacGia tacGia){
        ContentValues values = new ContentValues();
        values.put("TenTacGia", tacGia.getTenTacGia());
        values.put("GioiTinh", tacGia.getGioiTinh());
        try {
            return sqLiteDatabase.insert("TacGia", null, values);
        } catch (Exception e){
            Log.d("Can't insert new TacGia", e.getMessage());
            return 0;
        }
    }

    public  long update(TacGia tacGia){
        ContentValues values = new ContentValues();
        values.put("TenTacGia", tacGia.getTenTacGia());
        values.put("GioiTinh", tacGia.getGioiTinh());
        try {
            return sqLiteDatabase.update("TacGia", values, "MaTacGia=?", new String[]{String.valueOf(tacGia.getMaTacGia())});
        } catch (Exception e){
            Log.d("Can't update new The Loai Sach", e.getMessage());
            return 0;
        }
    }


    public int delete(Integer id){
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        SachDAO sachDAO = new SachDAO();
        int soluong = sachDAO.countWithTTacGia(id);
        if(soluong==0){
            return sqLiteDatabase.delete("TacGia", "MaTacGia=?", new String[]{String.valueOf(id)});
        }else{
            return 0;
        }
    }

}
