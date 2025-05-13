package com.example.quanlythuvien.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public boolean checkUserPassword(String username, String password, SQLiteDatabase sqLiteDatabase  ) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuanLy WHERE TenDangNhap = ? AND MatKhau = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public  boolean checkAccountValid(String username, String password, SQLiteDatabase sqLiteDatabase){
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM QuanLy WHERE TenDangNhap = ? AND MatKhau = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public static boolean checkPhone(String phone) {
        // Định dạng số điện thoại Việt Nam: 10 hoặc 11 chữ số, bắt đầu bằng số 0
        String phonePattern = "^(0[1-9]\\d{8}|\\+?84[1-9]\\d{8})$";

        // Tạo mẫu cho biểu thức chính quy
        Pattern pattern = Pattern.compile(phonePattern);

        // So khớp chuỗi với mẫu
        Matcher matcher = pattern.matcher(phone);

        // Trả về true nếu chuỗi khớp với mẫu, ngược lại trả về false
        return matcher.matches();
    }
    public static boolean checkGmail(String gmail) {
        // Định dạng email có thể sử dụng biểu thức chính quy
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Tạo mẫu cho biểu thức chính quy
        Pattern pattern = Pattern.compile(emailPattern);

        // So khớp chuỗi với mẫu
        Matcher matcher = pattern.matcher(gmail);

        // Trả về true nếu chuỗi khớp với mẫu, ngược lại trả về false
        return matcher.matches();
    }

    public boolean insertData(String fullname, String birth, String sex, String address,String phone, String gmail, String username, String password, SQLiteDatabase sqLiteDatabase) {
//        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenQuanLy", fullname);
        contentValues.put("NgaySinh", birth);
        contentValues.put("GioiTinh", sex);
        contentValues.put("DiaChi", address);
        contentValues.put("SDT", phone);
        contentValues.put("Gmail", gmail);
        contentValues.put("TenDangNhap", username);
        contentValues.put("MatKhau", password);
        long result = sqLiteDatabase.insert("QuanLy", null, contentValues);
        return result != -1;
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }
}
