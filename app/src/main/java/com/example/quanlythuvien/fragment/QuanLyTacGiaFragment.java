package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.TacGiaAdapter;
import com.example.quanlythuvien.dao.TacGiaDAO;
import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.TacGia;

import java.util.ArrayList;


public class QuanLyTacGiaFragment extends Fragment {
    Dialog dialog;
    private RecyclerView recyclerViewTacGia;
    private TacGiaDAO tacGiaDAO;
    private TacGiaAdapter tacGiaAdapter;
    private TextView txtXoaTacGia, txtSuaTacGia;
    Button btnThemTacGia;
    ArrayList<TacGia> listTacGia;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_tac_gia, container, false);

        listTacGia = new ArrayList<>();
        recyclerViewTacGia = view.findViewById(R.id.recyclerViewTacGia);
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TacGia", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            TacGia tacGia = new TacGia(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            listTacGia.add(tacGia);
            cursor.moveToNext();
        }
        cursor.close();
        tacGiaAdapter = new TacGiaAdapter(getContext(), this, listTacGia);
        recyclerViewTacGia.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerViewTacGia.setAdapter(tacGiaAdapter); // Di chuyển vào đây

        btnThemTacGia = view.findViewById(R.id.btnAddTacGia);
        btnThemTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogInsertTacGia();
//                Toast.makeText(getContext(), "Them", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public  void showDialogDelete(Integer ID){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_xoa_tacgia);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);
        Button btnCancelXoa = dialog.findViewById(R.id.btnCancelXoa);
        Button btnXacNhanXoaTacGia = dialog.findViewById(R.id.btnXacNhanXoaTacGia);
        dialog.show();
        btnCancelXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhanXoaTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer flag = 0;
                tacGiaDAO  = new TacGiaDAO(getContext());
                Integer delete = tacGiaDAO.delete(ID);
                if(delete==0){
                    flag = 1;
                }
                else{
                    listTacGia.clear();
                    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TacGia", null);
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        TacGia tacGia = new TacGia(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                        listTacGia.add(tacGia);
                        cursor.moveToNext();
                    }
                    cursor.close();
                    tacGiaAdapter.notifyDataSetChanged();
                    flag = 2;
                }
                dialog.dismiss();
                if(flag==2){
                    showDialogNotifi("Xóa tác giả thành công!");
                } else if (flag ==1) {
                    showDialogNotiFail("Thư viện còn sách của tác giả nên không thể xóa!!");
                }
            }
        });
    }
    public  void showDialogUpdtae(Integer ID){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_sua_tacgia);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);

        Button btnCancelSuaTG = dialog.findViewById(R.id.btnCancelSuaTG);
        Button btnXacNhanSuaTG = dialog.findViewById(R.id.btnXacNhanSuaTG);
        Spinner spinnerGioiTinh = dialog.findViewById(R.id.spinnerSuaGioiTinhTacGia);
        TextView txtTenSuaMoiiTacGia= dialog.findViewById(R.id.txtTenSuaMoiiTacGia);

        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TacGia WHERE MaTacGia=?", new String[]{String.valueOf(ID)});
        String currentName = "";
        String currentGender="";
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            currentName = cursor.getString(1);
            currentGender = cursor.getString(2);
            cursor.moveToNext();
        }
        cursor.close();
        txtTenSuaMoiiTacGia.setText(currentName);
//        spinnerGioiTinh.set
        String[] gioiTinhArray = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> gioiTinhAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gioiTinhArray);
        gioiTinhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGioiTinh.setAdapter(gioiTinhAdapter);


        if (!currentGender.isEmpty()) {
            int position = gioiTinhAdapter.getPosition(currentGender);
            spinnerGioiTinh.setSelection(position);
        }
        dialog.show();
        btnCancelSuaTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhanSuaTG.setOnClickListener(new View.OnClickListener() {
            Integer flag = 0;
            @Override
            public void onClick(View v) {
                TacGia tacGia = new TacGia();

                String tenTacGia = txtTenSuaMoiiTacGia.getText().toString();
                String gioiTinh = spinnerGioiTinh.getSelectedItem().toString();
                tacGia.setMaTacGia(ID);
                tacGia.setTenTacGia(tenTacGia);
                tacGia.setGioiTinh(gioiTinh);

                if(tenTacGia.trim().length()==0){
                    flag = 3;
                }
                else{
                    tacGiaDAO  = new TacGiaDAO(getContext());
                    long update = tacGiaDAO.update(tacGia);
                    if(update==0){
                        flag = 1;
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        listTacGia.clear();
                        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
                        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TacGia", null);
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            tacGia = new TacGia(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                            listTacGia.add(tacGia);
                            cursor.moveToNext();
                        }
                        cursor.close();
                        tacGiaAdapter.notifyDataSetChanged();
                        flag = 2;
                    }
                }
                dialog.dismiss();
                if(flag==2){
                    showDialogNotifi("Sửa tác giả thành công!");
                } else if (flag ==1) {
                    showDialogNotiFail("Sửa  không thành công!!");
                }
                else if(flag==3){
                    showDialogNotiFail("Tên tác giả không được để trống!");
                }
            }
        });
    }
    public  void showDialogInsertTacGia(){


        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_tacgia);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);

        Button btnCancelThem = dialog.findViewById(R.id.btnCancelThemTG);
        Button btnXacNhanThemTacGia = dialog.findViewById(R.id.btnXacNhanThemTG);
        Spinner spinnerGioiTinh = dialog.findViewById(R.id.spinnerGioiTinhTacGia);
        String[] gioiTinhArray = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> gioiTinhAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gioiTinhArray);
        gioiTinhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGioiTinh.setAdapter(gioiTinhAdapter);

        dialog.show();
        btnCancelThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhanThemTacGia.setOnClickListener(new View.OnClickListener() {
            Integer flag = 0;
            @Override
            public void onClick(View v) {
                TacGia tacGia = new TacGia();
                TextView txtTenTacGiaNew= dialog.findViewById(R.id.txtTenThemMoiTacGia);
                String tenTacGia = txtTenTacGiaNew.getText().toString();
                String gioiTinh = spinnerGioiTinh.getSelectedItem().toString();
                tacGia.setTenTacGia(tenTacGia);
                tacGia.setGioiTinh(gioiTinh);
                if(tenTacGia.trim().length()==0){
                    flag = 1;
                }else{
                    tacGiaDAO  = new TacGiaDAO(getContext());
                    long insert = tacGiaDAO.insert(tacGia);
                    if(insert==0){
                        flag = 1;
                    }
                    else{
                        listTacGia.clear();
                        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
                        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TacGia", null);
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            tacGia = new TacGia(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                            listTacGia.add(tacGia);
                            cursor.moveToNext();
                        }
                        cursor.close();
                        tacGiaAdapter.notifyDataSetChanged();
                        flag = 2;
                    }
                }

                dialog.dismiss();
                if(flag==2){
                    showDialogNotifi("Thêm tác giả thành công!");
                } else if (flag ==1) {
                    showDialogNotiFail("Tên tác giả không được để trống");
                }
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
    public  void showDialogNotiFail(String notit ){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_fail);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);
        Button btnCancelFail = dialog.findViewById(R.id.btnCancelFail);
        TextView txtFail = dialog.findViewById(R.id.txtFail);
        txtFail.setText(notit);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng dialog sau 2 giây
                dialog.dismiss();
            }
        }, 2000);
        btnCancelFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}