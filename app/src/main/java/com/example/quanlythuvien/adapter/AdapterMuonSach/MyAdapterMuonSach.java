package com.example.quanlythuvien.adapter.AdapterMuonSach;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.DAOMuonSach;
import com.example.quanlythuvien.dao.DAOSach;
import com.example.quanlythuvien.fragment.TraSachFragment;

import java.util.List;

public class MyAdapterMuonSach extends RecyclerView.Adapter<MyAdapterMuonSach.MyViewHolderMuonSach> {
    List<MuonSach> listmuonsach;
    Context mcontext;

    public MyAdapterMuonSach(Context mcontext) {
        this.mcontext = mcontext;
         daoSach= new DAOSach(mcontext);
         daoMuonSach = new DAOMuonSach(mcontext);
    }

    public void setData(List<MuonSach> list){
        this.listmuonsach = list;
        notifyDataSetChanged();
    }
    DAOSach daoSach;
    DAOMuonSach daoMuonSach;

    @NonNull
    @Override
    public MyViewHolderMuonSach onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_muon_sach, parent,false);
        return new MyViewHolderMuonSach(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMuonSach holder, int position) {
        MuonSach obj = listmuonsach.get(position);
        holder.txtngaymuonsach.setText("Ngày mượn: "+obj.getNgaymuon());

        int masach = obj.getMasach();
        holder.txttensachmuon.setText(daoSach.getTenSach(masach));
        holder.txtngaytrasach.setText("Ngày trả: "+obj.getNgaytra());
        holder.txttinhtrang.setText("Tình trạng: "+obj.getTinhtrang());
        holder.txtphuphi.setText("Phụ phí: "+obj.getPhuphi());
        holder.txtslmuon.setText("Số lượng mượn: "+daoMuonSach.getSoSachMuonTheoSach(obj.getMamuonsach()));

        if(daoMuonSach.getTinhTrangMuon(obj.getMamuonsach())==0){
            holder.btntrasach.setVisibility(View.GONE);
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }
        else{
            holder.cardView.setCardBackgroundColor(Color.YELLOW);
            holder.btntrasach.setVisibility(View.VISIBLE);
            holder.btntrasach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager trasachfragment = ((AppCompatActivity)mcontext).getSupportFragmentManager();
                    TraSachFragment a = new TraSachFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("masach",masach);
                    bundle.putInt("mamuon",obj.getMamuonsach());
                    //String maquanly,String tensach, String ngaymuon, int soluong, String ngaytra
                    bundle.putString("tensach",daoSach.getTenSach(masach));
                    bundle.putString("ngaymuon",obj.getNgaymuon());
                    bundle.putInt("soluongmuon",daoMuonSach.getSoSachMuonTheoSach(obj.getMamuonsach()));
                    a.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = trasachfragment.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, a);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listmuonsach.size();
    }

    public class MyViewHolderMuonSach extends RecyclerView.ViewHolder {
        CardView  cardView;
        TextView txttensachmuon, txtngaymuonsach, txtngaytrasach, txttinhtrang, txtphuphi, txtslmuon;
        Button btntrasach;
        public MyViewHolderMuonSach(@NonNull View itemView) {
            super(itemView);
            txtngaymuonsach = itemView.findViewById(R.id.edtngaydamuon);
            txttensachmuon = itemView.findViewById(R.id.edttensachdamuon);
            txtngaytrasach = itemView.findViewById(R.id.edtngaytra);
            txttinhtrang = itemView.findViewById(R.id.edttinhtrangtra);
            txtphuphi = itemView.findViewById(R.id.edtphuphi);
            btntrasach = itemView.findViewById(R.id.btntrasach);
            txtslmuon = itemView.findViewById(R.id.txtsoluongmuonsach);
            cardView = itemView.findViewById(R.id.cardviewmuonsach);
        }
    }
}
