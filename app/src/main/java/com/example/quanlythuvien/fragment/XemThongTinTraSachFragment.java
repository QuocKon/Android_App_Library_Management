package com.example.quanlythuvien.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.AdapterMuonSach.MuonSach;
import com.example.quanlythuvien.adapter.AdapterMuonSach.MyAdapterTTMuonSach;
import com.example.quanlythuvien.dao.DAOMuonSach;
import com.example.quanlythuvien.dao.DAOQuanLy;

import java.util.ArrayList;

public class XemThongTinTraSachFragment extends Fragment {
    ListView listView;
    DAOQuanLy daoQuanLy;
    EditText txttimkiem;
    ArrayList<MuonSach> listmuon;
    DAOMuonSach daoMuonSach;
    MyAdapterTTMuonSach myadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_xem_thong_tin_tra_sach, container, false);
        anhXa(v);
        listmuon = new ArrayList<>();
        listmuon = daoMuonSach.getTatCaSachMuon();
        myadapter = new MyAdapterTTMuonSach((Activity) v.getContext(),R.layout.item_xem_tra_sach,listmuon);
        listView.setAdapter(myadapter);
        txttimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterListView(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;
    }
    private void filterListView(String searchText){
        ArrayList<MuonSach> fillSach = new ArrayList<>();
        int ma = daoQuanLy.getMaQuanLy(searchText);
        for(MuonSach muonsach: daoMuonSach.getTatCaSachMuon()){
            if(ma == muonsach.getMaquanly()){
                fillSach.add(muonsach);
            }
        }
        myadapter.setData(fillSach);
    }

    private void anhXa(View v) {
        listView =  v.findViewById(R.id.listviewtrasach);
        txttimkiem = v.findViewById(R.id.txttimkiemtrasach);
        daoMuonSach = new DAOMuonSach(v.getContext());
        daoQuanLy = new DAOQuanLy(v.getContext());
    }
}