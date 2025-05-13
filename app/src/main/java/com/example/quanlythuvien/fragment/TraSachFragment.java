package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.DAOMuonSach;
import com.example.quanlythuvien.dao.DAOQuanLy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TraSachFragment extends Fragment {
    Button btnxacnhantra;
    Intent myIntent;
    TextView txtthongtin;
    EditText edtsl;
    EditText edtnhapma;
    DAOQuanLy daoQuanLy;
    String data="";
    int ma = 0;
    DAOMuonSach daoMuonSach;
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = dateFormat.format(currentDate);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quan_tra_sach, container, false);
        anhXa(v);

        // Nhận Bundle truyền từ fragment trước
        Bundle bundlenhan = getArguments();
        int manhan = bundlenhan.getInt("mamuon");
        int slmuon = bundlenhan.getInt("soluongmuon", 0);
        String tensach = bundlenhan.getString("tensach");
        String ngaymuon = bundlenhan.getString("ngaymuon");

        // Điền thông tin mã mượn vào EditText
        edtnhapma.setText(String.valueOf(manhan));
        edtnhapma.setEnabled(false); // Không cho sửa

        // Ghi chú thông tin người dùng đang mượn sách
        txtthongtin.setText("Bạn đang mượn " + slmuon + " quyển sách \"" + tensach + "\"");

        // Lấy tên tài khoản đăng nhập
        myIntent = getActivity().getIntent();
        if (myIntent != null && myIntent.getExtras() != null) {
            data = myIntent.getStringExtra("tenTaiKhoan");
        }
        ma = daoQuanLy.getMaQuanLy(data); // Lấy mã quản lý

        // Xử lý khi bấm nút xác nhận trả sách
        btnxacnhantra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sltra = edtsl.getText().toString().trim();

                if (sltra.isEmpty()) {
                    showDialogNotiFail("Vui lòng nhập số lượng sách muốn trả");
                    return;
                }

                int sluongtra = Integer.parseInt(sltra);
                if (sluongtra > slmuon) {
                    showDialogNotiFail("Số lượng trả không hợp lệ");
                    return;
                }

                // Thực hiện trả sách
                int kq = daoMuonSach.update_trasach(ma, tensach, ngaymuon, sluongtra, formattedDate);
                if (kq > 0) {
                    showDialogNotiSuccess("Trả sách thành công");

                    // Chuyển về trang chủ sau khi trả sách
                    HomeFragment fragment = new HomeFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    showDialogNotiFail("Trả sách thất bại. Vui lòng kiểm tra lại thông tin");
                }
            }
        });

        return v;
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

    private void anhXa(View v) {
        btnxacnhantra = v.findViewById(R.id.btnxacnhantrasach);
        edtnhapma = v.findViewById(R.id.edtnhapmatrasach);
        daoMuonSach = new DAOMuonSach(v.getContext());
        daoQuanLy = new DAOQuanLy(v.getContext());
        txtthongtin = v.findViewById(R.id.txtthongtinmuon);
        edtsl = v.findViewById(R.id.edtnhapsltrasach);
    }
}
