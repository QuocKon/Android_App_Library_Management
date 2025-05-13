package com.example.quanlythuvien.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.MainActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.DAOQuanLy;

public class DoiMatKhauFragment extends Fragment {

    EditText edtpassHienTai, edtpassMoi1, edtpassMoi2;
    Button btnXacNhanDoiPass, btnThoatDoiMatKhau;
    Intent myIntent;
    DAOQuanLy daoQuanLy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);

        daoQuanLy = new DAOQuanLy(getContext());

        edtpassHienTai = view.findViewById(R.id.edtpassHienTai);
        edtpassMoi1 = view.findViewById(R.id.edtpassMoi1);
        edtpassMoi2 = view.findViewById(R.id.edtpassMoi2);

        btnXacNhanDoiPass = view.findViewById(R.id.btnXacNhanDoiPass);
        btnThoatDoiMatKhau = view.findViewById(R.id.btnThoatDoiMatKhau);

        myIntent = getActivity().getIntent();
        String data = "";
        if (myIntent != null && myIntent.getExtras() != null) {
            data = myIntent.getStringExtra("tenTaiKhoan");
        }

        Cursor cursor = daoQuanLy.getThongTinQuanLyTheoTenDangNhap(data);

        if (cursor != null && cursor.moveToFirst()) {
            String matkhau = cursor.getString(cursor.getColumnIndex("MatKhau"));
            cursor.close();

            String finalData = data;
            btnXacNhanDoiPass.setOnClickListener(v -> {
                String passHienTai = edtpassHienTai.getText().toString().trim();
                String passMoi1 = edtpassMoi1.getText().toString().trim();
                String passMoi2 = edtpassMoi2.getText().toString().trim();

                if (!passHienTai.equals(matkhau)) {
                    edtpassHienTai.setError("Mật khẩu không đúng!");
                    edtpassHienTai.requestFocus();
                } else if (!passMoi1.equals(passMoi2)) {
                    edtpassMoi2.setError("Mật khẩu mới không trùng khớp!");
                    edtpassMoi2.requestFocus();
                } else {
                    ContentValues values = new ContentValues();
                    values.put("MatKhau", passMoi1);

                    boolean success = daoQuanLy.capNhatThongTinQuanLy(finalData, values);
                    if (success) {
                        Toast.makeText(getActivity(), "Đổi mật khẩu thành công! Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnThoatDoiMatKhau.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            });
        }
        return view;
    }
}
