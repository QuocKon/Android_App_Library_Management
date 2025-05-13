package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.LoaiSachAdapter;
import com.example.quanlythuvien.dao.LoaiSachDAO;
import com.example.quanlythuvien.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoaiSachFragment extends Fragment {
    Dialog dialog;
    private RecyclerView recyclerView;
    private LoaiSachDAO loaiSachDAO;
    private LoaiSachAdapter loaiSachAdapter;
    private FloatingActionButton addNewLoaiSach;
    private ArrayList<LoaiSach> listLoaiSach;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);

        loaiSachDAO = new LoaiSachDAO(getContext());
        listLoaiSach = loaiSachDAO.getAll();

        recyclerView = view.findViewById(R.id.recycle_LoaiSach);
        loaiSachAdapter = new LoaiSachAdapter(listLoaiSach);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(loaiSachAdapter);

        addNewLoaiSach = view.findViewById(R.id.addNewLoaiSach);
        addNewLoaiSach.setOnClickListener(v -> showDialogAddNew());

        loaiSachAdapter.setOnItemClickListener(loaiSach -> showDialog(loaiSach.getMaTheLoai()));

        return view;
    }

    private void reloadData() {
        listLoaiSach.clear();
        listLoaiSach.addAll(loaiSachDAO.getAll());
        loaiSachAdapter.notifyDataSetChanged();
    }

    public void showDialog(Integer ID) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loaisach_action);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        TextView txtSuaTen = dialog.findViewById(R.id.txtSuaTen);
        TextView txtXoa = dialog.findViewById(R.id.txtXoa);
        Button btnCancel = dialog.findViewById(R.id.btnDialogCancel);

        txtXoa.setOnClickListener(v -> {
            dialog.dismiss();
            showDialogDelete(ID);
        });

        txtSuaTen.setOnClickListener(v -> {
            dialog.dismiss();
            showDialogUpdate(ID);
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    public void showDialogDelete(Integer ID) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_xoa_loaisach);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        Button btnCancelXoa = dialog.findViewById(R.id.btnCancelXoa);
        Button btnXacNhanXoa = dialog.findViewById(R.id.btnXacNhanXoa);
        dialog.show();

        btnCancelXoa.setOnClickListener(v -> dialog.dismiss());

        btnXacNhanXoa.setOnClickListener(v -> {
            int result = loaiSachDAO.delete(ID);
            dialog.dismiss();
            if (result > 0) {
                reloadData();
                showDialogNotifi("Xóa kệ sách thành công!");
            } else {
                showDialogNotiFail("Kệ sách phải trống mới có thể xóa!");
            }
        });
    }

    public void showDialogUpdate(Integer ID) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_sua_loaisach);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        String currentName = loaiSachDAO.getTenTheLoaiById(ID);

        Button btnCancelSua = dialog.findViewById(R.id.btnCancelSua);
        Button btnXacNhanSua = dialog.findViewById(R.id.btnXacNhanSua);
        TextInputEditText txtTenMoiLoaiSach = dialog.findViewById(R.id.txtTenMoiLoaiSach);
        txtTenMoiLoaiSach.setText(currentName);
        dialog.show();

        btnCancelSua.setOnClickListener(v -> dialog.dismiss());

        btnXacNhanSua.setOnClickListener(v -> {
            String newName = txtTenMoiLoaiSach.getText().toString().trim();
            if (newName.isEmpty()) {
                showDialogNotiFail("Tên kệ sách không được để trống");
            } else {
                LoaiSach loaiSach = new LoaiSach(ID, newName);
                long update = loaiSachDAO.update(loaiSach);
                dialog.dismiss();
                if (update > 0) {
                    reloadData();
                    showDialogNotifi("Sửa kệ sách thành công!");
                } else {
                    showDialogNotiFail("Sửa không thành công");
                }
            }
        });
    }

    public void showDialogAddNew() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_loaisach);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        Button btnCancelThem = dialog.findViewById(R.id.btnCancelThem);
        Button btnXacNhanThem = dialog.findViewById(R.id.btnXacNhanThem);
        TextInputEditText txtTenMoiLoaiSach = dialog.findViewById(R.id.txtTenThemMoiLoaiSach);
        dialog.show();

        btnCancelThem.setOnClickListener(v -> dialog.dismiss());

        btnXacNhanThem.setOnClickListener(v -> {
            String newName = txtTenMoiLoaiSach.getText().toString().trim();
            if (newName.isEmpty()) {
                showDialogNotiFail("Tên kệ sách không được để trống");
            } else {
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setTenTheLoai(newName);
                long insert = loaiSachDAO.insert(loaiSach);
                dialog.dismiss();
                if (insert > 0) {
                    reloadData();
                    showDialogNotifi("Thêm kệ sách thành công!");
                } else {
                    showDialogNotiFail("Thêm không thành công");
                }
            }
        });
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

        new Handler().postDelayed(dialog::dismiss, 2000);

        btnCancelNotit.setOnClickListener(v -> dialog.dismiss());
    }

    public void showDialogNotiFail(String notit) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_fail);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);

        Button btnCancelFail = dialog.findViewById(R.id.btnCancelFail);
        TextView txtFail = dialog.findViewById(R.id.txtFail);
        txtFail.setText(notit);
        dialog.show();

        new Handler().postDelayed(dialog::dismiss, 2000);

        btnCancelFail.setOnClickListener(v -> dialog.dismiss());
    }
}
