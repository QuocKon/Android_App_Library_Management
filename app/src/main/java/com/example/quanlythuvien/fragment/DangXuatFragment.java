package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.MainActivity;
import com.example.quanlythuvien.R;

public class DangXuatFragment extends Fragment {
    private Dialog dialog;
    private FragmentManager fragmentManager;
    Button btnXacNhanDangXuat, btnThoatDangXuat;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_xuat, container, false);
        btnThoatDangXuat = view.findViewById(R.id.HuyDangXuat);
        btnXacNhanDangXuat = view.findViewById(R.id.XacNhanDangXuat);

        btnThoatDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().finish();
                dialog.dismiss();
            }
        });

        btnXacNhanDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().finish();
            }
        });
        return view;
    }
}