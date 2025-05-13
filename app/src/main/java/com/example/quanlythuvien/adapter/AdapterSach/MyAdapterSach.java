package com.example.quanlythuvien.adapter.AdapterSach;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.DAOSach;
import com.example.quanlythuvien.fragment.XemTTSachFragment;

import java.util.List;

public class MyAdapterSach extends RecyclerView.Adapter<MyAdapterSach.ViewHolderSach> {
    List<Sach> listsach;
    Context mcontext;

    private DAOSach daosach;


    public MyAdapterSach( Context mcontext) {
        this.mcontext = mcontext;
        daosach = new DAOSach(mcontext);
    }
    public void setData(List<Sach> l){
        this.listsach = l;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolderSach onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsach, parent,false);
        return new ViewHolderSach(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSach holder, int position) {
        Sach a = listsach.get(position);
        byte[] hinhanh = a.getHinhAnh();
        if (hinhanh != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
            holder.imgsach.setImageBitmap(bitmap);
        }

        int matacgia = a.getMatacGia();
        holder.txttensach.setText(a.getTenSach());
        holder.txttacgia.setText("Tác giả: "+daosach.get_tacgia(matacgia));
        holder.txtsoluong.setText("Số lượng: "+a.getSoLuong());
        holder.txtnamxb.setText("Ngày xuất bản: "+a.getNgayXuatBan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                FragmentManager xemchitietsach = ((AppCompatActivity)mcontext).getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putByteArray("anh",a.getHinhAnh());
                bundle.putString("tensach",a.getTenSach());
                bundle.putInt("theloai",a.getMaTheLoai());
                bundle.putString("tomtat",a.getTomTat());
                bundle.putString("ngayxuatban",a.getNgayXuatBan());
                bundle.putInt("soluong",a.getSoLuong());
                bundle.putInt("nxb",a.getManxb());
                bundle.putInt("tg",a.getMatacGia());
                XemTTSachFragment a = new XemTTSachFragment();
                a.setArguments(bundle);
                FragmentTransaction fragmentTransaction = xemchitietsach.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, a);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listsach.size();
    }

    public class ViewHolderSach extends RecyclerView.ViewHolder{
        ImageView imgsach;
        TextView txttensach, txtsoluong, txttacgia, txtnamxb;
        public ViewHolderSach(@NonNull View itemView) {
            super(itemView);
            imgsach = itemView.findViewById(R.id.imgitemsach);
            txttensach = itemView.findViewById(R.id.txtitemtensach);
            txtsoluong = itemView.findViewById(R.id.txtitemsoluong);
            txttacgia = itemView.findViewById(R.id.txtitemtacgia);
            txtnamxb = itemView.findViewById(R.id.txtitemnamxuatban);
        }
    }

}
