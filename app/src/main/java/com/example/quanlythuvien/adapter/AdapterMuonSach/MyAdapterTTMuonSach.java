package com.example.quanlythuvien.adapter.AdapterMuonSach;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.DAOQuanLy;
import com.example.quanlythuvien.dao.DAOSach;

import java.util.ArrayList;

public class MyAdapterTTMuonSach extends ArrayAdapter<MuonSach> {
    Activity context;
    DAOQuanLy daoQuanLy;
    DAOSach daoSach;
    int id;
    ArrayList<MuonSach> listmuon;
    public MyAdapterTTMuonSach(Activity context, int resource, ArrayList<MuonSach> objects) {
        super(context, resource, objects);
        this.context = context;
        this.id = resource;
        this.listmuon = objects;
        this.daoQuanLy = new DAOQuanLy(context);
        this.daoSach = new DAOSach(context);
    }
    public void setData(ArrayList<MuonSach> newData) {
        clear(); // Xóa dữ liệu hiện tại
        if (newData != null) {
            addAll(newData); // Thêm dữ liệu mới
            notifyDataSetChanged(); // Thông báo cho ListView cập nhật
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(id, parent, false);
        }


        MuonSach muonSach = listmuon.get(position);

        TextView txtmamuonsach = convertView.findViewById(R.id.txtmamuonsach);
        txtmamuonsach.setText(muonSach.getMamuonsach()+"");

        TextView txtnguoimuon = convertView.findViewById(R.id.txttendangnhapmuon);
        int maquanlymuonsach = muonSach.getMaquanly();
        txtnguoimuon.setText(daoQuanLy.getTenQuanLy(maquanlymuonsach));

        TextView txtsachmuon = convertView.findViewById(R.id.txttensachmuon);
        String tenSach = daoSach.getTenSach(muonSach.getMasach());
        txtsachmuon.setText(tenSach);

        TextView txtsoluong = convertView.findViewById(R.id.txtsoluongmuon);
        txtsoluong.setText(muonSach.getSoluong()+"");

        TextView txtngaymuonsach = convertView.findViewById(R.id.txtngaydamuon);
        txtngaymuonsach.setText(muonSach.getNgaymuon());

        return convertView;
    }
}
