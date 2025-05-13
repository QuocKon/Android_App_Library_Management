package com.example.quanlythuvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.AdapterMuonSach.MuonSach;
import com.example.quanlythuvien.adapter.AdapterMuonSach.MyAdapterMuonSach;
import com.example.quanlythuvien.dao.DAOMuonSach;
import com.example.quanlythuvien.dao.DAOQuanLy;

import java.util.ArrayList;
import java.util.List;

public class MuonSachFragment extends Fragment {
    TextView txtsoluongdamuon, txtsoluongdangmuon, txtsoluongquahan, txttienphat;
    RecyclerView recyclerView;
    Intent myIntent;
    MyAdapterMuonSach adapter;
    String data="";
    int ma = 0;
    DAOQuanLy daoQuanLy;
    DAOMuonSach daoMuonSach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_muon_sach, container, false);
        anhXa(v);
        myIntent = getActivity().getIntent();

        if (myIntent != null && myIntent.getExtras() != null) {
            data = (myIntent.getStringExtra("tenTaiKhoan"));
        }

        ma = daoQuanLy.getMaQuanLy(data);
        LinearLayoutManager liner = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(liner);
        adapter = new MyAdapterMuonSach(v.getContext());
        adapter.setData(getData());
        recyclerView.setAdapter(adapter);
        txtsoluongdamuon.setText(daoMuonSach.getsosachdamuon(ma)+"");
        txtsoluongdangmuon.setText(daoMuonSach.getsosachdangmuon(ma)+"");
        txtsoluongquahan.setText(daoMuonSach.getsosachquahan(ma)+"");
        txttienphat.setText((daoMuonSach.getsosachquahan(ma)*10000)+"");
        return v;
    }

    private List<MuonSach> getData() {
        List<MuonSach> listmuon = new ArrayList<>();
        listmuon = daoMuonSach.getAllSachMuon(ma);
        return listmuon;
    }


    private void anhXa(View v) {
        txtsoluongdamuon = v.findViewById(R.id.edtsosachdamuon);
        txtsoluongquahan = v.findViewById(R.id.edtsosachquahan);
        txttienphat = v.findViewById(R.id.edtsotienphaitra);
        txtsoluongdangmuon = v.findViewById(R.id.edtsodangmuon);
        recyclerView = v.findViewById(R.id.recycleviewsachmuon);
        daoMuonSach = new DAOMuonSach(getContext());
        daoQuanLy = new DAOQuanLy(getContext());
    }
}