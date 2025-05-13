package com.example.quanlythuvien.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.fragment.QuyDinhFragment;
import com.example.quanlythuvien.model.QuyDinh;

import java.util.List;

public class QuyDinhAdapter extends  RecyclerView.Adapter<QuyDinhAdapter.QuyDinhViewHolder> {
    private List<QuyDinh> listQuyDinh;
    private Context context;
    SQLiteDatabase sqLiteDatabase = null;
    private QuyDinhFragment quyDinhFragment;

    public QuyDinhAdapter(List<QuyDinh> listQuyDinh, Context context, QuyDinhFragment quyDinhFragment) {
        this.listQuyDinh = listQuyDinh;
        this.context = context;
        this.quyDinhFragment = quyDinhFragment;
    }

    @NonNull
    @Override
    public QuyDinhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_quydinh, parent, false);
        return new QuyDinhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuyDinhViewHolder holder, int position) {
            QuyDinh quyDinh = listQuyDinh.get(position);
            holder.txtMaQuyDinh.setText(quyDinh.getMaQuyDinh()+"");
            holder.edtNoiDungQuyDinh.setText(quyDinh.getNoiDung());
            byte[] imgQuyDinh = quyDinh.getAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgQuyDinh, 0, imgQuyDinh.length);
            if(imgQuyDinh.length>0){
                holder.imgQuyDinh.setImageBitmap(bitmap);
            }

            holder.imgSuaQuyDinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quyDinhFragment.showDialogUpdate(quyDinh.getMaQuyDinh());
                }
            });
            holder.imgXoaQuyDinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quyDinhFragment.showDialogDelete(quyDinh.getMaQuyDinh());

                }
            });

    }

    @Override
    public int getItemCount() {
        return listQuyDinh.size();
    }

    public class QuyDinhViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaQuyDinh;
        ImageView imgSuaQuyDinh, imgXoaQuyDinh, imgQuyDinh;
        EditText edtNoiDungQuyDinh;
        public QuyDinhViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaQuyDinh = itemView.findViewById(R.id.txtSTTQuyDinh);
            edtNoiDungQuyDinh = itemView.findViewById(R.id.edtNoiDungQuyDinh);
            imgSuaQuyDinh = itemView.findViewById(R.id.imgSuaQuyDinh);
            imgXoaQuyDinh = itemView.findViewById(R.id.imgXoaQuyDinh);
            imgQuyDinh = itemView.findViewById(R.id.imgQuyDinh);
        }
    }
}
