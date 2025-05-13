package com.example.quanlythuvien.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.AdapterSach.Sach;
import com.example.quanlythuvien.dao.DAOMuonSach;
import com.example.quanlythuvien.dao.DAOSach;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class SuaSachFragment extends Fragment {
    EditText edttensach, edttomtat, edtngayxb, edtsl;
    TextView edtmasach;
    Calendar myCalendar;
    Spinner spintheloai, spintacgia, spinnhaxb;
    ImageView imganh;
    DAOMuonSach daoMuonSach;
    ImageButton btncam, btnfolder, btnquaylai;
    Button btnsua;
    DAOSach daoSach;
    int REQUEST_CODE_CAMERA = 1;
    int REQUEST_CODE_FOLDER = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quan__sua__sach, container, false);
        myCalendar = Calendar.getInstance();
        anhxa(v);

        Bundle bundle = getArguments();

        //Lấy tên thể loại đổ vào spinner
        List<String> listtheloai = daoSach.getTenTheLoai();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(v.getContext(),android.R.layout.simple_spinner_item,listtheloai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintheloai.setAdapter(arrayAdapter);

        // Lấy tác giả đổ vào spinner
        List<String> listtacgia = daoSach.getAlltacgia();
        ArrayAdapter<String> arrayAdaptertacgia = new ArrayAdapter<>(v.getContext(),android.R.layout.simple_spinner_item,listtacgia);
        arrayAdaptertacgia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintacgia.setAdapter(arrayAdaptertacgia);

        //Lấy nhà xuất bản dổ vào spinner
        List<String> listnhaxb = daoSach.getNhaXB();
        ArrayAdapter<String> arrayAdapterNhaXB = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, listnhaxb);
        arrayAdapterNhaXB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnhaxb.setAdapter(arrayAdapterNhaXB);

        byte[] hinhanh = bundle.getByteArray("anh");
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);


        edtmasach.setText(bundle.getInt("masach")+"");

        imganh.setImageBitmap(bitmap);

        String tensach = daoSach.getTenSach(bundle.getInt("masach"));
        edttensach.setText(tensach);

        String theloai = bundle.getString("theloai");
        for(int i=0; i<spintheloai.getCount(); i++){
            if(theloai.equals(spintheloai.getItemAtPosition(i).toString())){
                spintheloai.setSelection(i);
                break;
            }
        }

        String tacgia = bundle.getString("tacgia");
        for(int i=0; i<spintacgia.getCount(); i++){
            if(tacgia.equals(spintacgia.getItemAtPosition(i).toString())){
                spintacgia.setSelection(i);
                break;
            }
        }

        String nhaxb = bundle.getString("nhaxuatban");
        for(int i=0; i<spinnhaxb.getCount(); i++){
            if(nhaxb.equals(spinnhaxb.getItemAtPosition(i).toString())){
                spinnhaxb.setSelection(i);
                break;
            }
        }
        edttomtat.setText(bundle.getString("tomtat"));
        edtngayxb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(v.getContext(), dateSetListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edtngayxb.setText(bundle.getString("ngayxuatban"));
        edtsl.setText(bundle.getInt("soluong")+"");

        btncam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });
        btnfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });
        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masachs = edtmasach.getText().toString();
                String tensachs = edttensach.getText().toString();
                String tacgias = spintacgia.getSelectedItem().toString();

                int matacgias = Integer.parseInt(daoSach.getMaTacGia(tacgias));
                String tomtats = edttomtat.getText().toString();
                String ngayxbs = edtngayxb.getText().toString();
                String nhaxbs = spinnhaxb.getSelectedItem().toString();
                int manhaxb = Integer.parseInt(daoSach.getManhaXB(nhaxbs));
                String theloais = spintheloai.getSelectedItem().toString();
                int matheloais = Integer.parseInt(daoSach.getMaTheLoai(theloais));
                String soluongString = edtsl.getText().toString();
                int soluongs;
                if (!soluongString.isEmpty()) {
                    soluongs = Integer.parseInt(soluongString);
                } else {
                    soluongs=0;
                }
                int sosachdangmuon = daoMuonSach.getSoSachdangmuon(Integer.parseInt(masachs));
                BitmapDrawable bitmapDrawable = (BitmapDrawable)imganh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
                byte[] hinhanhs = byteArrayOutputStream.toByteArray();
                boolean check = true;
                if(tensachs.isEmpty()){
                    check = false;
                    showDialogNotiFail("Tên sách không được bỏ trống");
                }
                if(tomtats.isEmpty()){
                    check = false;
                    showDialogNotiFail("Tóm tắt không được để trống");
                }
                if(soluongs <=0){
                    check = false;
                    showDialogNotiFail("Số lượng không được nhỏ hơn 0");
                }
                if(soluongs < sosachdangmuon){
                    check = false;
                    showDialogNotiFail("Số sách đang được mượn là "+sosachdangmuon+" .Số sách hiện tại không dược nhỏ hơn số sách đang cho mượn");
                }
                if(check){
                    Sach obj = new Sach();
                    obj.setMaSach(Integer.parseInt(masachs));
                    obj.setTenSach(tensachs);
                    obj.setMaTheLoai(matheloais);
                    obj.setTomTat(tomtats);
                    obj.setMatacGia(matacgias);
                    obj.setNgayXuatBan(ngayxbs);
                    obj.setManxb(manhaxb);
                    obj.setSoLuong(soluongs);
                    obj.setHinhAnh(hinhanhs);
                    if(daoSach.sua_sach(obj)>0){
                        showDialogNotiSuccess("Sửa thông tin sách thành công");
                        HomeFragment a = new HomeFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, a)
                                .addToBackStack(null)
                                .commit();
                    }
                    else{
                        showDialogNotiFail("Sửa thông tin sách thất bại");
                    }
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imganh.setImageBitmap(bitmap);
        }

        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                Bitmap scaledBitmap = scaleBitmap(bitmap,800);
                imganh.setImageBitmap(scaledBitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            // Update the calendar with the selected date
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Update the EditText with the selected date
            updateLabel();
        }
    };
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtngayxb.setText(sdf.format(myCalendar.getTime()));
    }

    private void anhxa(View v) {
        edtmasach = v.findViewById(R.id.edtmasachsua);
        edttensach = v.findViewById(R.id.edttensachsua);
        edttomtat = v.findViewById(R.id.edttomtatsua);
        edtngayxb = v.findViewById(R.id.edtngayxbsua);
        edtsl = v.findViewById(R.id.edtslsua);
        btnquaylai = v.findViewById(R.id.btnquaylaisua);
        spinnhaxb = v.findViewById(R.id.spinnhaxbsua);
        spintacgia = v.findViewById(R.id.spintacgiasua);
        spintheloai = v.findViewById(R.id.spintheloaisua);
        imganh = v.findViewById(R.id.imganhsua);
        btncam = v.findViewById(R.id.btncamsua);
        btnfolder = v.findViewById(R.id.btnfoldersua);
        btnsua = v.findViewById(R.id.btnsuasuasach);
        daoSach = new DAOSach(v.getContext());
        daoMuonSach = new DAOMuonSach(v.getContext());
    }
    private Bitmap scaleBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }


}