package com.example.quanlythuvien.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    private static final String DB_NAME = "library_manager.db";
    private static final int DB_VERSION = 1;

    private final String CREATE_TABLE_MuonSach = "CREATE TABLE IF NOT EXISTS \"MuonSach\" (\n" +
            "\t\"MaMuonSach\"\tINTEGER PRIMARY KEY,\n" +
            "\t\"MaSinhVien\"\tINTEGER NOT NULL,\n" +
            "\t\"MaSach\"\tINTEGER NOT NULL,\n" +
            "\t\"NgayMuon\"\tdate NOT NULL,\n" +
            "\t\"NgayTra\"\tdate,\n" +
            "\t\"TinhTrang\"\tvarchar(255) NOT NULL,\n" +
            "\t\"PhuPhi\"\tINTEGER,\n" +
            "\tFOREIGN KEY(\"MaSinhVien\") REFERENCES \"SinhVien\"(\"MaSinhVien\"),\n" +
            "\tFOREIGN KEY(\"MaSach\") REFERENCES \"Sach\"(\"MaSach\")\n" +
            ");";

    private final String CREATE_TABLE_NhaXuatBan = "CREATE TABLE IF NOT EXISTS \"NhaXuatBan\" (\n" +
            "\t\"MaNXB\"\tINTEGER PRIMARY KEY,\n" +
            "\t\"TenNXB\"\tvarchar(255) NOT NULL,\n" +
            "\t\"DiaChi\"\tvarchar(255) NOT NULL,\n" +
            "\t\"QuocGia\"\tvarchar(255) NOT NULL\n" +
            ");";

    private final String CREATE_TABLE_QuanLy = "CREATE TABLE IF NOT EXISTS \"QuanLy\" (\n" +
            "\t\"MaQuanLy\"\tINTEGER PRIMARY KEY,\n" +
            "\t\"TenQuanLy\"\tvarchar(255) NOT NULL,\n" +
            "\t\"NgaySinh\"\tdate NOT NULL,\n" +
            "\t\"GioiTinh\"\tvarchar(10) NOT NULL,\n" +
            "\t\"DiaChi\"\tvarchar(255) NOT NULL,\n" +
            "\t\"SDT\"\tvarchar(10) NOT NULL,\n" +
            "\t\"Gmail\"\tvarchar(255) NOT NULL,\n" +
            "\t\"TenDangNhap\"\tvarchar(255) NOT NULL,\n" +
            "\t\"MatKhau\"\tvarchar(255) NOT NULL\n" +
            ");";

    private final String CREATE_TABLE_QuyDinh = "CREATE TABLE IF NOT EXISTS \"QuyDinh\" (\n" +
            "\t\"MaQuyDinh\"\tINTEGER PRIMARY KEY,\n" +
            "\t\"NoiDung\"\tTEXT NOT NULL,\n" +
            "\t\"Anh\"\tvarchar(255)\n" +
            ");";

    private final String CREATE_TABLE_TacGia = "CREATE TABLE IF NOT EXISTS \"TacGia\" (\n" +
            "\t\"MaTacGia\"\tINTEGER PRIMARY KEY,\n" +
            "\t\"TenTacGia\"\tvarchar(255) NOT NULL,\n" +
            "\t\"GioiTinh\"\tvarchar(10) NOT NULL\n" +
            ");";

    private final String CREATE_TABLE_TheLoaiSach = "CREATE TABLE IF NOT EXISTS \"TheLoaiSach\" (\n" +
            "\t\"MaTheLoai\"\tINTEGER PRIMARY KEY,\n" +
            "\t\"TenTheLoai\"\tTEXT NOT NULL\n" +
            ");";

    private final String CREATE_TABLE_SinhVien = "CREATE TABLE IF NOT EXISTS \"SinhVien\" (\n" +
            "\t\"MaSinhVien\"\tINTEGER NOT NULL,\n" +
            "\t\"TenSinhVien\"\tvarchar(255) NOT NULL,\n" +
            "\t\"NgaySinh\"\tdate NOT NULL,\n" +
            "\t\"GioiTinh\"\tvarchar(10) NOT NULL,\n" +
            "\t\"DiaChi\"\tvarchar(255) NOT NULL,\n" +
            "\t\"SDT\"\tvarchar(12) NOT NULL,\n" +
            "\t\"Gmail\"\tvarchar(255) NOT NULL,\n" +
            "\tPRIMARY KEY(\"MaSinhVien\")\n" +
            ");";

    private final String CREATE_TABLE_Sach = "CREATE TABLE IF NOT EXISTS \"Sach\" (\n" +
            "\t\"MaSach\"\tINTEGER PRIMARY KEY,\n" +
            "\t\"TenSach\"\tvarchar(255) NOT NULL,\n" +
            "\t\"MaTheLoai\"\tINTEGER NOT NULL,\n" +
            "\t\"TomTat\"\tNUMERIC NOT NULL,\n" +
            "\t\"MaTacGia\"\tINTEGER NOT NULL,\n" +
            "\t\"NgayXuatBan\"\tdate NOT NULL,\n" +
            "\t\"MaNXB\"\tINTEGER NOT NULL,\n" +
            "\t\"SoLuong\"\tINTEGER NOT NULL,\n" +
            "\t\"Anh\"\tBLOB,\n" +
            "\tFOREIGN KEY(\"MaTacGia\") REFERENCES \"TacGia\"(\"MaTacGia\"),\n" +
            "\tFOREIGN KEY(\"MaTheLoai\") REFERENCES \"TheLoaiSach\"(\"MaTheLoai\"),\n" +
            "\tFOREIGN KEY(\"MaNXB\") REFERENCES \"NhaXuatBan\"(\"MaNXB\")\n" +
            ");";

    public DB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Quan trọng là cần đảm bảo thứ tự tạo bảng không gây lỗi ràng buộc khóa ngoại
        sqLiteDatabase.execSQL(CREATE_TABLE_SinhVien);
        sqLiteDatabase.execSQL(CREATE_TABLE_QuanLy);
        sqLiteDatabase.execSQL(CREATE_TABLE_NhaXuatBan);
        sqLiteDatabase.execSQL(CREATE_TABLE_TheLoaiSach);
        sqLiteDatabase.execSQL(CREATE_TABLE_TacGia);
        sqLiteDatabase.execSQL(CREATE_TABLE_Sach);
        sqLiteDatabase.execSQL(CREATE_TABLE_QuyDinh);
        sqLiteDatabase.execSQL(CREATE_TABLE_MuonSach);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu chúng tồn tại
        String dropTableMuonSach = "DROP TABLE IF EXISTS MuonSach";
        sqLiteDatabase.execSQL(dropTableMuonSach);

        String dropTableSach = "DROP TABLE IF EXISTS Sach";
        sqLiteDatabase.execSQL(dropTableSach);

        String dropTableTacGia = "DROP TABLE IF EXISTS TacGia";
        sqLiteDatabase.execSQL(dropTableTacGia);

        String dropTableTheLoaiSach = "DROP TABLE IF EXISTS TheLoaiSach";
        sqLiteDatabase.execSQL(dropTableTheLoaiSach);

        String dropTableNhaXuatBan = "DROP TABLE IF EXISTS NhaXuatBan";
        sqLiteDatabase.execSQL(dropTableNhaXuatBan);

        String dropTableQuanLy = "DROP TABLE IF EXISTS QuanLy";
        sqLiteDatabase.execSQL(dropTableQuanLy);

        String dropTableSinhVien = "DROP TABLE IF EXISTS SinhVien";
        sqLiteDatabase.execSQL(dropTableSinhVien);

        String dropTableQuyDinh = "DROP TABLE IF EXISTS QuyDinh";
        sqLiteDatabase.execSQL(dropTableQuyDinh);

        // Sau khi xóa tất cả các bảng, tạo lại chúng
        onCreate(sqLiteDatabase);
    }
}