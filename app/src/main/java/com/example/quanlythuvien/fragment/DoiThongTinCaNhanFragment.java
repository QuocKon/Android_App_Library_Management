package com.example.quanlythuvien.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.DAOQuanLy;
import com.example.quanlythuvien.model.Validate;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DoiThongTinCaNhanFragment extends Fragment {
    TextView tvTenQuanLy, tvMaQuanLy, tvTenDangNhap, tvNgaySinh, tvGioiTinh, tvDiaChi, tvSoDienThoai, tvEmail;
    Intent myIntent;
    private Dialog dialog;
    private SimpleDateFormat sdf;
    Spinner spinnerGioiTinh;
    DAOQuanLy daoQuanLy;
    Validate validate;
    Button btnSuaThongTinCaNhan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_thong_tin_ca_nhan, container, false);
        daoQuanLy = new DAOQuanLy(getContext());
        sdf = new SimpleDateFormat("dd/MM/yyyy");

        myIntent = getActivity().getIntent();
        String data = "";
        if (myIntent != null && myIntent.getExtras() != null) {
            data = myIntent.getStringExtra("tenTaiKhoan");
        }

        Cursor cursor = daoQuanLy.getThongTinQuanLyTheoTenDangNhap(data);

        tvMaQuanLy = view.findViewById(R.id.tvMaQuanLy);
        tvTenDangNhap = view.findViewById(R.id.tvTenDangNhap);
        tvTenQuanLy = view.findViewById(R.id.tvTenQuanLy);
        tvNgaySinh = view.findViewById(R.id.tvNgaySinh);
        tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        tvSoDienThoai = view.findViewById(R.id.tvSoDienThoai);
        tvEmail = view.findViewById(R.id.tvEmail);

        btnSuaThongTinCaNhan = view.findViewById(R.id.btnSuaThongTinCaNhan);

        if (cursor.moveToFirst()) {
            tvMaQuanLy.setText(String.valueOf(cursor.getInt(0)));
            tvTenQuanLy.setText(cursor.getString(1));
            tvTenDangNhap.setText(cursor.getString(2));
            tvNgaySinh.setText(cursor.getString(3));
            tvGioiTinh.setText(cursor.getString(4));
            tvDiaChi.setText(cursor.getString(5));
            tvSoDienThoai.setText(cursor.getString(6));
            tvEmail.setText(cursor.getString(7));
        }
        cursor.close();

        String finalData = data;
        btnSuaThongTinCaNhan.setOnClickListener(v -> showDialog(finalData));

        return view;
    }

    public void showDialog(String tenDangNhap) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.suathongtincanhan_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);
        dialog.show();

        Button btnXacNhanSuaThongTinCaNhan = dialog.findViewById(R.id.btnXacNhanSuaThongTinCaNhan);
        Button btnThoatSuaThongTinCaNhan = dialog.findViewById(R.id.btnThoatSuaThongTinCaNhan);

        TextView tvMaQuanLymoi = dialog.findViewById(R.id.tvMaQuanLymoi);
        TextView tvTenQuanLymoi = dialog.findViewById(R.id.tvTenQuanLymoi);
        TextView tvNgaySinhmoi = dialog.findViewById(R.id.tvNgaySinhmoi);
        TextView tvDiaChimoi = dialog.findViewById(R.id.tvDiaChimoi);
        TextView tvSoDienThoaimoi = dialog.findViewById(R.id.tvSoDienThoaimoi);
        TextView tvEmailmoi = dialog.findViewById(R.id.tvEmailmoi);
        spinnerGioiTinh = dialog.findViewById(R.id.spinnerGioiTinh);

        String[] gioiTinhArray = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> gioiTinhAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gioiTinhArray);
        gioiTinhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGioiTinh.setAdapter(gioiTinhAdapter);

        Cursor cursor = daoQuanLy.getThongTinQuanLyTheoTenDangNhap(tenDangNhap);
        String gioiTinhQuanLy = "";
        if (cursor.moveToFirst()) {
            tvMaQuanLymoi.setText(String.valueOf(cursor.getInt(0)));
            tvTenQuanLymoi.setText(cursor.getString(1));
            tvNgaySinhmoi.setText(cursor.getString(3));
            gioiTinhQuanLy = cursor.getString(4);
            tvDiaChimoi.setText(cursor.getString(5));
            tvSoDienThoaimoi.setText(cursor.getString(6));
            tvEmailmoi.setText(cursor.getString(7));
        }
        cursor.close();

        if (!gioiTinhQuanLy.isEmpty()) {
            int position = gioiTinhAdapter.getPosition(gioiTinhQuanLy);
            spinnerGioiTinh.setSelection(position);
        }

        tvNgaySinhmoi.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog d = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                    (view, year, month, dayOfMonth) -> {
                        c.set(year, month, dayOfMonth);
                        tvNgaySinhmoi.setText(sdf.format(c.getTime()));
                    },
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            d.show();
        });

        btnXacNhanSuaThongTinCaNhan.setOnClickListener(v -> {
            String tenQuanLyMoi = tvTenQuanLymoi.getText().toString();
            String ngaySinhMoi = tvNgaySinhmoi.getText().toString();
            String gioiTinhMoi = spinnerGioiTinh.getSelectedItem().toString();
            String diaChiMoi = tvDiaChimoi.getText().toString();
            String soDienThoaiMoi = tvSoDienThoaimoi.getText().toString();
            String emailMoi = tvEmailmoi.getText().toString();

            ContentValues values = new ContentValues();
            values.put("TenQuanLy", tenQuanLyMoi);
            values.put("NgaySinh", ngaySinhMoi);
            values.put("GioiTinh", gioiTinhMoi);
            values.put("DiaChi", diaChiMoi);
            values.put("SDT", soDienThoaiMoi);
            values.put("Gmail", emailMoi);

            Boolean phoneNew = validate.checkPhone(soDienThoaiMoi);
            Boolean emailquanLy = validate.checkGmail(emailMoi);

            if (soDienThoaiMoi.isEmpty() || emailMoi.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập thông tin đầy đủ", Toast.LENGTH_SHORT).show();
            } else if (!phoneNew) {
                tvSoDienThoaimoi.setError("Số điện thoại không hợp lệ! Vui lòng nhập lại");
            } else if (!emailquanLy) {
                tvEmailmoi.setError("Email không hợp lệ! vui lòng nhập lại");
            } else {
                boolean success = daoQuanLy.capNhatThongTinQuanLy(tenDangNhap, values);
                if (success) {
                    dialog.dismiss();
                    showDialogNotifi("Cập nhật thông tin thành công");
                    reloadData(tenDangNhap);
                } else {
                    Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnThoatSuaThongTinCaNhan.setOnClickListener(v -> dialog.dismiss());
    }

    private void reloadData(String tenDangNhap) {
        Cursor cursor = daoQuanLy.getThongTinQuanLyTheoTenDangNhap(tenDangNhap);
        if (cursor.moveToFirst()) {
            tvMaQuanLy.setText(String.valueOf(cursor.getInt(0)));
            tvTenDangNhap.setText(cursor.getString(2));
            tvTenQuanLy.setText(cursor.getString(1));
            tvNgaySinh.setText(cursor.getString(3));
            tvGioiTinh.setText(cursor.getString(4));
            tvDiaChi.setText(cursor.getString(5));
            tvSoDienThoai.setText(cursor.getString(6));
            tvEmail.setText(cursor.getString(7));
        }
        cursor.close();
    }

    public void showDialogNotifi(String notit) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);

        Button btnCancelNotit = dialog.findViewById(R.id.btnCancelNotit);
        TextView txtnoti = dialog.findViewById(R.id.txtNotification);
        txtnoti.setText(notit);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(() -> dialog.dismiss(), 2000);

        btnCancelNotit.setOnClickListener(v -> dialog.dismiss());
    }
}