package com.example.quanlythuvien.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.model.LoaiSach;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder>{
  private List<LoaiSach> listLoaiSach;
  private Context context;
 SQLiteDatabase sqLiteDatabase = null;

    public LoaiSachAdapter(Context context) {
        this.context = context;
    }

    public LoaiSachAdapter( List<LoaiSach> listLoaiSach) {
        this.listLoaiSach = listLoaiSach;
    }
    public void setData (List<LoaiSach> list){
        this.listLoaiSach = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_sach, parent, false);
        return new LoaiSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {
            LoaiSach loaiSach =  listLoaiSach.get(position);
//            Sach sach = new Sach();
            if(loaiSach== null){
                return;
            }
            holder.txtTenTheLoai.setText(loaiSach.getTenTheLoai());
            holder.txtSoLuongSach.setText(loaiSach.countSach(loaiSach.getMaTheLoai()) + " cuốn sách");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(loaiSach);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        if(listLoaiSach!=null)return listLoaiSach.size();
        return 0;
    }

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLoaiSach;
        private TextView txtTenTheLoai, txtSoLuongSach;
        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLoaiSach = itemView.findViewById(R.id.imageLoaiSach);
            txtTenTheLoai = itemView.findViewById(R.id.nameLoaiSach);
            txtSoLuongSach = itemView.findViewById(R.id.soLuongSach);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(LoaiSach loaiSach);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
