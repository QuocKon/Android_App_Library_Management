package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.VeUngDungDAO;
import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.VeUngDung;

public class VeUngDungFragment extends Fragment {
    SQLiteDatabase sqLiteDatabase = null;

    EditText edtVeUngDung,edtNoiDungMoi;

    private VeUngDungDAO veUngDungDAO;
    private Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ve_ung_dung, container, false);

        sqLiteDatabase = DatabaseSingleton.getDatabase();

        edtVeUngDung = view.findViewById(R.id.edtVeUngDung);

        Button btnSuaVeUngDung = view.findViewById(R.id.btnSuaVeUngDung);


        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ThongTinUngDung", null);

        if (cursor.moveToFirst()) {
            String noiDung = cursor.getString(1);
            edtVeUngDung.setText(noiDung);
        }

        cursor.close();
        btnSuaVeUngDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noiDungHienTai = edtVeUngDung.getText().toString();
                showDialog(noiDungHienTai);
            }
        });

        return view;
    }
    public void showDialog(String noiDung) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_suathongtinungdung);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        Button btnXacNhanSuaThongTinUngDung = dialog.findViewById(R.id.btnXacNhanSuaThongTinUngDung);
        Button btnThoatSuaThongTinUngDung = dialog.findViewById(R.id.btnThoatSuaThongTinUngDung);
        EditText edtNoiDungMoi = dialog.findViewById(R.id.edtNoiDungMoi);
        edtNoiDungMoi.setText(noiDung);
        dialog.show();

        btnXacNhanSuaThongTinUngDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NoiDungMoi = edtNoiDungMoi.getText().toString().trim();
                if (NoiDungMoi.isEmpty()) {
                    edtNoiDungMoi.setError("Vui lòng nhập mội dung mới");
//                    Toast.makeText(getContext(), "Vui lòng nhập nội dung mới", Toast.LENGTH_SHORT).show();
                } else {
                    VeUngDung veUngDung = new VeUngDung();
                    veUngDung.setID(1);
                    veUngDung.setNoiDung(NoiDungMoi);
                    veUngDungDAO = new VeUngDungDAO(getContext());
                    int flag = 0;
                    long rowsAffected = veUngDungDAO.update(veUngDung);

                    if (rowsAffected > 0) {
                        edtVeUngDung.setText(NoiDungMoi);
                        flag = 1;
                    } else {
                        Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    if(flag == 1){
                        showDialogNotifi("Cập nhật thông tin thành công");
                    }
                }
            }
        });
        btnThoatSuaThongTinUngDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public  void showDialogNotifi(String notit){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);
        Button btnCancelNotit = dialog.findViewById(R.id.btnCancelNotit);
        TextView txtnoti = dialog.findViewById(R.id.txtNotification);
        txtnoti.setText(notit);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
        btnCancelNotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}