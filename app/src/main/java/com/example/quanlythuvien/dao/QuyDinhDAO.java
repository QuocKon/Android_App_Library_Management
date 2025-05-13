package com.example.quanlythuvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.QuyDinh;

public class QuyDinhDAO {
    private Context context;
    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();

    public QuyDinhDAO(Context context) {
        this.context = context;
    }

    public  long insert(QuyDinh quyDinh){
        ContentValues values = new ContentValues();
        values.put("NoiDung", quyDinh.getNoiDung());
        values.put("Anh", quyDinh.getAnh());
        try {
            return sqLiteDatabase.insert("QuyDinh", null, values);
        } catch (Exception e){
            Log.d("Can't insert new Quy Dinh", e.getMessage());
            return 0;
        }
    }

    public  long update(QuyDinh quyDinh){
        ContentValues values = new ContentValues();
        values.put("NoiDung", quyDinh.getNoiDung());
        values.put("Anh", quyDinh.getAnh());
        try {
            return sqLiteDatabase.update("QuyDinh", values, "MaQuyDinh=?", new String[]{String.valueOf(quyDinh.getMaQuyDinh())});
        } catch (Exception e){
            Log.d("Can't update new Quy Dinh", e.getMessage());
            return 0;
        }
    }
    public int delete(Integer id){
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        return sqLiteDatabase.delete("QuyDinh", "MaQuyDinh=?", new String[]{String.valueOf(id)});
    }

}
