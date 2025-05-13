package com.example.quanlythuvien.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.AdapterSach.MyAdapterSach;
import com.example.quanlythuvien.adapter.AdapterSach.Sach;
import com.example.quanlythuvien.dao.DAOSach;

import java.util.ArrayList;
import java.util.List;


public class SachFragment extends Fragment {
    RecyclerView recy;
    DAOSach daoSach ;
    MyAdapterSach adapterSach;
    EditText txttimkiem;
    TextView txtsoluong;
    Button btnthem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sach, container, false);
        anhXa(v);
        adapterSach = new MyAdapterSach(v.getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recy.setLayoutManager(linearLayoutManager);
        recy.setAdapter(adapterSach);
        adapterSach.setData(getData());
        int sl = adapterSach.getItemCount();
        txtsoluong.setText(sl+"");
        btnthem.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                ThemSachFragment fragment = new ThemSachFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        txttimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterRecycleView(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;
    }

    private void filterRecycleView(String searchText){
        List<Sach> fillSach = new ArrayList<>();
        for(Sach sach: getData()){
            if(sach.getTenSach().toLowerCase().contains(searchText.toLowerCase())){
                fillSach.add(sach);
            }
        }
        adapterSach.setData(fillSach);
    }
    private List<Sach> getData() {
        return daoSach.getAll();
    }

    private void anhXa(View k) {
        recy = k.findViewById(R.id.recycleviewsach);
        txttimkiem = k.findViewById(R.id.edttimkiem);
        txtsoluong = k.findViewById(R.id.txtsoluongsach);
        btnthem = k.findViewById(R.id.btnthemsach);
        daoSach = new DAOSach(k.getContext());
    }
}