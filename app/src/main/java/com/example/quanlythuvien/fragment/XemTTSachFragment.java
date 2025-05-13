package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.AdapterMuonSach.MuonSach;
import com.example.quanlythuvien.dao.DAOMuonSach;
import com.example.quanlythuvien.dao.DAOQuanLy;
import com.example.quanlythuvien.dao.DAOSach;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XemTTSachFragment extends Fragment {
    ImageView img;
    Intent myIntent;
    String data="";
    DAOQuanLy daoQuanLy;
    int ma = 0;
    ImageButton btnquaylai, btnxoa,btnsua;
    Button btnmuon;
    DAOMuonSach daoMuonSach;
    DAOSach daoSach;
    TextView txttensach, txttacgia, txttheloai, txttomtat, txtngayxb, txtnhaxb, txtslkho, txtslsachdangmuon, txtsosachcon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quan__xem_tt__sach, container, false);
        anhxa(v);
        myIntent = getActivity().getIntent();

        if (myIntent != null && myIntent.getExtras() != null) {
            data = (myIntent.getStringExtra("tenTaiKhoan"));
        }
        ma = daoQuanLy.getMaQuanLy(data);
        Bundle nhan = getArguments();

        byte[] hinhanh = nhan.getByteArray("anh");
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        img.setImageBitmap(bitmap);

        txttensach.setText(nhan.getString("tensach"));
        int masach = daoSach.getMaSach(txttensach.getText().toString());

        int matacgia = nhan.getInt("tg");
        String tentacgia = daoSach.get_tacgia(matacgia);
        txttacgia.setText(tentacgia);

        int theloai = nhan.getInt("theloai");
        String tentheloai = daoSach.gettenthloai(theloai);
        txttheloai.setText(tentheloai);

        txttomtat.setText(nhan.getString("tomtat"));
        txtngayxb.setText(nhan.getString("ngayxuatban"));
        txtslsachdangmuon.setText(daoMuonSach.getSoSachdangmuon(daoSach.getMaSach(txttensach.getText().toString()))+"");

        int sosachconlai = nhan.getInt("soluong") - daoMuonSach.getSoSachdangmuon(daoSach.getMaSach(txttensach.getText().toString()));
        txtsosachcon.setText(sosachconlai+"");

        int nhaxb = nhan.getInt("nxb");
        String tennhaxb = daoSach.gettennhaxb(nhaxb);
        txtnhaxb.setText(tennhaxb);

        txtslkho.setText(nhan.getInt("soluong")+"");

        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFeedbackXoa(masach, txttensach.getText().toString());
            }
        });

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("masach",masach);
                bundle.putByteArray("anh", hinhanh);
                bundle.putString("tacgia",tentacgia);
                bundle.putString("nhaxuatban",tennhaxb);
                bundle.putString("theloai",tentheloai);
                bundle.putString("tomtat",nhan.getString("tomtat"));
                bundle.putInt("soluong",nhan.getInt("soluong"));
                bundle.putString("ngayxuatban", nhan.getString("ngayxuatban"));
                SuaSachFragment fragment = new SuaSachFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnmuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sosachconlai > 0) {
                    OpenFeedbackMuon(ma,txttensach.getText().toString(),tentacgia,sosachconlai,nhan.getInt("soluong"));
                }
                else{
                    showDialogNotiFail("Số lượng sách còn lại trong kho không đủ. Mong bạn thông cảm");
                }
            }
        });


        return v;
    }


    private void OpenFeedbackMuon(int matk,String ts, String tg, int sosachconlai, int soluong){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_muon_sach);
        Window win = dialog.getWindow();
        if(win == null){
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowattribute = win.getAttributes();
        windowattribute.gravity = Gravity.CENTER;
        win.setAttributes(windowattribute);
        Button btnmuon = dialog.findViewById(R.id.btnmuonsach);
        Button btnhuy = dialog.findViewById(R.id.btnhuymuon);
        TextView txttens = dialog.findViewById(R.id.edttensachmuon);
        txttens.setText(ts);
        TextView txttacgias = dialog.findViewById(R.id.edttacgiamuon);
        txttacgias.setText(tg);

        EditText sl = dialog.findViewById(R.id.edtsoluongmuon);
        sl.setText("1");

        TextView txtngaymuon = dialog.findViewById(R.id.edtngaymuon);
        Date datecurrent = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(datecurrent);
        txtngaymuon.setText(format);
        dialog.show();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnmuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongt = Integer.parseInt(sl.getText().toString());
                if(soluongt<=0){
                    showDialogNotiFail("Số lượng mượn không được nhỏ hơn 1");
                }
                else if(soluongt > sosachconlai){
                    showDialogNotiFail("Số lượng không đủ cho bạn mượn");
                }
                else{
                    int masach = Integer.parseInt(daoMuonSach.getmasach(txttens.getText().toString()));
                    String ngaymuon= txtngaymuon.getText().toString();
                    MuonSach obj = new MuonSach(
                            matk,
                            masach,
                            ngaymuon,
                            "",
                            "Chưa trả",
                            0,
                            soluongt
                    );
                    if(daoMuonSach.insert_muonsach(obj) > 0){
                        showDialogNotiSuccess("Mượn sách thành công");
                        dialog.dismiss();
                        txtslsachdangmuon.setText(daoMuonSach.getSoSachdangmuon(daoSach.getMaSach(txttensach.getText().toString()))+"");
                        int sosachconlai = soluong - daoMuonSach.getSoSachdangmuon(daoSach.getMaSach(txttensach.getText().toString()));
                        txtsosachcon.setText(sosachconlai+"");
                        HomeFragment fragment = new HomeFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else {
                        showDialogNotiFail("Mượn sách thất bại. Vui lòng kiểm tra lại");
                    }
                }
            }
        });
    }
    public void showDialogNotiSuccess(String notit){
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
    public void showDialogNotiFail(String notit){
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
    private void OpenFeedbackXoa(int masach, String tensach) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_xoa_sach);
        Window win = dialog.getWindow();
        if (win == null) {
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowattribute = win.getAttributes();
        windowattribute.gravity = Gravity.CENTER;
        win.setAttributes(windowattribute);
        dialog.show();
        TextView edtthongbaoxoa = dialog.findViewById(R.id.txtthongbaoxoasach);
        edtthongbaoxoa.setText("Bạn chắc chắn muốn xóa sách "+tensach+" chứ?");
        Button btnhuy = dialog.findViewById(R.id.btnhuydialogxoasach);
        Button btnxoa = dialog.findViewById(R.id.btndialogxoasach);
        btnxoa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(daoSach.xoa_sach(masach)>0){
                    showDialogNotiSuccess("Xóa sách thành công");
                    dialog.dismiss();
                    SachFragment sach = new SachFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, sach)
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    showDialogNotiFail("Xóa sách thất bại");
                    dialog.dismiss();
                }
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    private void anhxa(View v) {
        img = v.findViewById(R.id.imgxemchitiet);
        txttensach = v.findViewById(R.id.txtchitiettensach);
        txttacgia = v.findViewById(R.id.txtchitiettacgia);
        txttheloai = v.findViewById(R.id.txtchitiettheloai);
        txttomtat = v.findViewById(R.id.txtchitiettomtat);
        txtngayxb = v.findViewById(R.id.txtchitietngayxb);
        txtnhaxb = v.findViewById(R.id.txtchitietnhaxb);
        txtslkho = v.findViewById(R.id.txtchitietslkho);
        txtslsachdangmuon = v.findViewById(R.id.txtchitietslmuon);
        txtsosachcon = v.findViewById(R.id.txtchitietsoluoncon);
        daoSach = new DAOSach(v.getContext());
        btnquaylai = v.findViewById(R.id.btnquaylai);
        btnsua = v.findViewById(R.id.btnsuasach);
        btnxoa = v.findViewById(R.id.btnxoasach);
        btnmuon = v.findViewById(R.id.btnmuonsach);
        daoMuonSach = new DAOMuonSach(v.getContext());
        daoQuanLy = new DAOQuanLy(v.getContext());
    }
}