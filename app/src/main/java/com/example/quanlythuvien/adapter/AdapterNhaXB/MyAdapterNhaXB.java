package com.example.quanlythuvien.adapter.AdapterNhaXB;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.DAONhaXB;
import com.example.quanlythuvien.fragment.QuanLyNXBFragment;

import java.util.ArrayList;

public class MyAdapterNhaXB extends ArrayAdapter<NhaXB> {
    ArrayList<NhaXB> listnxb;
    Activity context;
    DAONhaXB daoNhaXB;
    int id;

    public MyAdapterNhaXB(Activity context, int resource, ArrayList<NhaXB> list) {
        super(context, resource, list);
        this.context = context;
        this.id = resource;
        this.listnxb = list;
        this.daoNhaXB = new DAONhaXB(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(id, parent, false);
        }

        NhaXB mynxb = listnxb.get(position);

        TextView txtmaxb = convertView.findViewById(R.id.txtmanhaxb);
        txtmaxb.setText(String.valueOf(mynxb.getManxb()));

        TextView txttennxb = convertView.findViewById(R.id.txttennhaxb);
        txttennxb.setText(mynxb.getTennxb());

        TextView txtdiachi = convertView.findViewById(R.id.txtdiachinxb);
        txtdiachi.setText(mynxb.getDiachi());

        TextView txtquocgia = convertView.findViewById(R.id.txtquocgianhaxb);
        txtquocgia.setText(mynxb.getQuocgia());

        ImageButton btnsua = convertView.findViewById(R.id.btnsuanxb);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFeedbackSua(mynxb.getManxb(), mynxb.getTennxb(), mynxb.getDiachi(), mynxb.getQuocgia());
            }
        });

        ImageButton btnxoa = convertView.findViewById(R.id.btnxoanxb);
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFeedbackXoa(mynxb.getManxb(),mynxb.getTennxb());
            }
        });

        return convertView;
    }

    private void OpenFeedbackSua(int ma, String ten, String diachi, String quocgia) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_nxb);
        Window win = dialog.getWindow();
        if (win == null) {
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowattribute = win.getAttributes();
        windowattribute.gravity = Gravity.CENTER;
        win.setAttributes(windowattribute);
        EditText edttennhaxb = dialog.findViewById(R.id.txtsuatennhaxb);
        edttennhaxb.setText(ten);
        EditText edtdiachinhaxb = dialog.findViewById(R.id.txtsuadiachinxb);
        edtdiachinhaxb.setText(diachi);
        EditText edtquocgianhaxb = dialog.findViewById(R.id.txtsuaquocgianxb);
        edtquocgianhaxb.setText(quocgia);
        Button btnsua = dialog.findViewById(R.id.btnsuanhaxb);
        Button btnhuy = dialog.findViewById(R.id.btnThoatsuanhaxb);
        dialog.show();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edttennhaxb.getText().toString().trim();
                String diachi = edtdiachinhaxb.getText().toString().trim();
                String quocgia = edtquocgianhaxb.getText().toString().trim();
                boolean check = true;
                if(ten.isEmpty()){
                    check = false;
                    showDialogNotiFail("Tên nhà xuất bản không được để trống");
                }
                if(diachi.isEmpty()){
                    check = false;
                    showDialogNotiFail("Địa chỉ nhà xuất bản không được để trống");
                }
                if(quocgia.isEmpty()){
                    check = false;
                    showDialogNotiFail("Quốc gia nhà xuất bản không được để trống");
                }
                if(check){
                    NhaXB obj = new NhaXB();
                    obj.setManxb(ma);
                    obj.setTennxb(ten);
                    obj.setDiachi(diachi);
                    obj.setQuocgia(quocgia);
                    if(daoNhaXB.update_nhaxb(obj)>0){
                        showDialogNotiSuccess("Sửa thông tin nhà xuất bản thành công");
                        dialog.dismiss();
                        FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                        QuanLyNXBFragment fragment = new QuanLyNXBFragment();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else{
                        showDialogNotiFail("Sửa thông tin thất bại");
                    }
                }
            }
        });
    }
    private void OpenFeedbackXoa(int ma,String ten) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_xoa_nxb);
        Window win = dialog.getWindow();
        if (win == null) {
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowattribute = win.getAttributes();
        windowattribute.gravity = Gravity.CENTER;
        win.setAttributes(windowattribute);
        TextView txtthongbao = dialog.findViewById(R.id.edtthongbaoxoa);
        txtthongbao.setText("Bạn chắc chắn muốn xóa nhà xuất bản: "+ten +" này chứ?");
        Button btnxoa = dialog.findViewById(R.id.btndialogxoanxb);
        Button btnhuy = dialog.findViewById(R.id.btnhuydialogxoanxb);
        dialog.show();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(daoNhaXB.delete_nhaXB(ma)>0){
                    showDialogNotiSuccess("Xóa nhà xuất bản thành công");
                    dialog.dismiss();
                    FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                    QuanLyNXBFragment fragment = new QuanLyNXBFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else {
                    showDialogNotiFail("Xóa nhà xuất bản thất bại");
                }
            }
        });
    }
    public  void showDialogNotiFail(String notit){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.thongbao_thatbai);
        Window win = dialog.getWindow();
        if(win == null){
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams window = win.getAttributes();
        window.gravity = Gravity.CENTER;
        win.setAttributes(window);
        TextView txt = dialog.findViewById(R.id.txtFail);
        txt.setText(notit);
        Button btnthoat = dialog.findViewById(R.id.btnCancelFail);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng dialog sau 2 giây
                dialog.dismiss();
            }
        }, 2000);
        dialog.show();
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    public  void showDialogNotiSuccess(String notit){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_thongbao_ok);
        Window win = dialog.getWindow();
        if(win == null){
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams window = win.getAttributes();
        window.gravity = Gravity.CENTER;
        win.setAttributes(window);
        TextView txt = dialog.findViewById(R.id.txtNotification);
        txt.setText(notit);
        Button btnthoat = dialog.findViewById(R.id.btnCancelNotit);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng dialog sau 2 giây
                dialog.dismiss();
            }
        }, 2000);
        dialog.show();
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

}
