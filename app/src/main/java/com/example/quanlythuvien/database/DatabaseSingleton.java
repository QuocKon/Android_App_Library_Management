package com.example.quanlythuvien.database;

import android.database.sqlite.SQLiteDatabase;

public class DatabaseSingleton {
    private static SQLiteDatabase sqLiteDatabase;

    public static void initialize(SQLiteDatabase db) {
        sqLiteDatabase = db;
    }
    public static SQLiteDatabase getDatabase() {
        return sqLiteDatabase;
    }
}
