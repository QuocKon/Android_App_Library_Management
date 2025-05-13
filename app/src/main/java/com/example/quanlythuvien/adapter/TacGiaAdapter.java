package com.example.quanlythuvien.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.fragment.QuanLyTacGiaFragment;
import com.example.quanlythuvien.model.TacGia;

import java.util.List;

public class TacGiaAdapter extends RecyclerView.Adapter<TacGiaAdapter.TacGiaViewHolder> {
    private List<TacGia> listTacGia;
    private Context context;
    SQLiteDatabase sqLiteDatabase = null;
    private QuanLyTacGiaFragment quanLyTacGiaFragment;

    public TacGiaAdapter(Context context, QuanLyTacGiaFragment fragment) {
        this.context = context;
        this.quanLyTacGiaFragment = fragment;
    }

    public TacGiaAdapter(Context context, QuanLyTacGiaFragment fragment, List<TacGia> listTacGia) {
        this.context = context;
        this.quanLyTacGiaFragment = fragment;
        this.listTacGia = listTacGia; // Cập nhật danh sách tác giả
    }

    public TacGiaAdapter(List<TacGia> listTacGia) {
        this.listTacGia = listTacGia;
    }

    @NonNull
    @Override
    public TacGiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_tacgia, parent, false);

        return new TacGiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TacGiaViewHolder holder, int position) {
        TacGia tacGia = listTacGia.get(position);
        if(tacGia== null){
            return;
        }
        holder.txtMaTacGia.setText(tacGia.getMaTacGia()+"");
        holder.txtTenTacGia.setText(tacGia.getTenTacGia());
        holder.txtGioiTinhTacGia.setText(tacGia.getGioiTinh());

        holder.txtXoaTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quanLyTacGiaFragment.showDialogDelete(tacGia.getMaTacGia());
            }
        });

        holder.txtSuaTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quanLyTacGiaFragment.showDialogUpdtae(tacGia.getMaTacGia());

            }
        });
    }

    @Override
    public int getItemCount() {
        if(listTacGia!=null)return listTacGia.size();
        return 0;
    }

    public class TacGiaViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaTacGia, txtTenTacGia, txtGioiTinhTacGia, txtSuaTacGia, txtXoaTacGia;
        Button btnThemTacGia;
        public TacGiaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTacGia = itemView.findViewById(R.id.txtMaTacGia);
            txtTenTacGia = itemView.findViewById(R.id.txtTenTacGia);
            txtGioiTinhTacGia = itemView.findViewById(R.id.txtGioiTinhTacGia);
            txtSuaTacGia = itemView.findViewById(R.id.txtSuaTacGia);
            txtXoaTacGia = itemView.findViewById(R.id.txtXoaTacGia);
            btnThemTacGia = itemView.findViewById(R.id.btnAddTacGia);
        }
    }
}
