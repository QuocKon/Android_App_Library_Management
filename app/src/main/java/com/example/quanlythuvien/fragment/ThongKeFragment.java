package com.example.quanlythuvien.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.SachDAO;
import com.example.quanlythuvien.database.DatabaseSingleton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class ThongKeFragment extends Fragment {
    TextView txtTongSoSach, txtSoSachDangMuon, txtSoSachQuaHan, txtNgayBatDau, txtNgayKetThuc, txtSoLuongTheoThang;
    private SachDAO sachDAO;
    private SimpleDateFormat sdf;
    SQLiteDatabase sqLiteDatabase = null;

    Intent myIntent;

    Button btnThongKe;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
         init(view);
        sachDAO = new SachDAO(getContext());
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        myIntent = getActivity().getIntent();
        String data = "";
        if (myIntent != null && myIntent.getExtras() != null) {
            data = myIntent.getStringExtra("tenTaiKhoan");
        }

        sqLiteDatabase = DatabaseSingleton.getDatabase();
        int maQuanLy = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT MaQuanLy FROM QuanLy where TenDangNhap =?", new String[]{data});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            maQuanLy = cursor.getInt(0);
            cursor.moveToNext();
        }

        cursor.close();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day);
         Date startDate = calendar.getTime();
         Date endDate = calendar.getTime();
        final Date[] startDateArray = {startDate, endDate};
        int sosachmuontrongTime = sachDAO.countBorrowingBetweenDate(startDate, endDate, maQuanLy);
        int tongsoluong = sachDAO.countAll();
        int sosachdangmuon = sachDAO.countBorrowing(maQuanLy);
        int sosachquahan = sachDAO.countOverDate(maQuanLy);

        txtNgayBatDau.setText(sdf.format(startDate));
        txtNgayKetThuc.setText(sdf.format(endDate));
        txtTongSoSach.setText(tongsoluong+"");
        txtSoSachDangMuon.setText(sosachdangmuon+"");
        txtSoSachQuaHan.setText(sosachquahan+"");
        txtSoLuongTheoThang.setText(sosachmuontrongTime+"");

        txtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1  = Calendar.getInstance();
                int mYear = calendar1.get(Calendar.YEAR);
                int mMonth= calendar1.get(Calendar.MONTH);
                int mDay= calendar1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        GregorianCalendar calendar2 = new GregorianCalendar(year, month, dayOfMonth);
                        txtNgayBatDau.setText(sdf.format(calendar2.getTime()));
                        startDateArray[0] = calendar2.getTime();
//                        startDate = calendar2.getTime();
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1  = Calendar.getInstance();
                int mYear = calendar1.get(Calendar.YEAR);
                int mMonth= calendar1.get(Calendar.MONTH);
                int mDay= calendar1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        GregorianCalendar calendar2 = new GregorianCalendar(year, month, dayOfMonth);
                        txtNgayKetThuc.setText(sdf.format(calendar2.getTime()));
                        startDateArray[1] = calendar2.getTime();
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        int finalMaQuanLy = maQuanLy;


        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sosachtrongTime = sachDAO.countBorrowingBetweenDate(startDateArray[0], startDateArray[1], finalMaQuanLy);
                if(startDateArray[0].after(startDateArray[1])){
                    Toast.makeText(getContext(), "Ngày bắt đầu phải nhỏ hơn ngày kết thúc", Toast.LENGTH_SHORT).show();
                }else{
                    txtSoLuongTheoThang.setText(sosachtrongTime+"");
                    if(sosachtrongTime==0){
                        Toast.makeText(getContext(), "Bạn không mượn cuốn  sách nào trong khoảng thời gian này", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        return  view;
    }

    public void init(View view){
        txtTongSoSach = view.findViewById(R.id.tongSoSach);
        txtSoSachDangMuon = view.findViewById(R.id.txtSoLuongSachMuon);
        txtSoSachQuaHan = view.findViewById(R.id.txtSoSachQuaHan);
        txtNgayBatDau = view.findViewById(R.id.txtNgayBatDau);
        txtNgayKetThuc = view.findViewById(R.id.txtNgayKetThuc);
        txtSoLuongTheoThang = view.findViewById(R.id.txtSoLuongTheoThang);
        btnThongKe = view.findViewById(R.id.btnThongKe);
    }
}