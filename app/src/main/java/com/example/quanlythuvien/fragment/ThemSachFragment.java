package com.example.quanlythuvien.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.AdapterSach.Sach;
import com.example.quanlythuvien.dao.DAOSach;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ThemSachFragment extends Fragment {
    Button btnthem;
    private DAOSach daoSach;
    LocalDate localDate = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    ImageView imgthemsach;
    ImageButton btncam, btnfolder;
    Spinner theloai, nhaxb, tacgia;
    EditText edttensach, edttomtat, edtngayxb,edtsoluong;
    Calendar myCalendar;
    int REQUEST_CODE_CAMERA = 1;
    int REQUEST_CODE_FOLDER = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quan__them__sach_, container, false);
        anhXa(v);
        myCalendar = Calendar.getInstance();
        updateLabel();
        daoSach = new DAOSach(v.getContext());

        //Lấy tên thể loại đổ vào spinner
        List<String> listtheloai = daoSach.getTenTheLoai();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(v.getContext(),android.R.layout.simple_spinner_item,listtheloai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theloai.setAdapter(arrayAdapter);

        // Lấy tác giả đổ vào spinner
        List<String> listtacgia = daoSach.getAlltacgia();
        ArrayAdapter<String> arrayAdaptertacgia = new ArrayAdapter<>(v.getContext(),android.R.layout.simple_spinner_item,listtacgia);
        arrayAdaptertacgia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tacgia.setAdapter(arrayAdaptertacgia);

        //Lấy nhà xuất bản dổ vào spinner
        List<String> listnhaxb = daoSach.getNhaXB();
        ArrayAdapter<String> arrayAdapterNhaXB = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, listnhaxb);
        arrayAdapterNhaXB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nhaxb.setAdapter(arrayAdapterNhaXB);
        edtngayxb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(v.getContext(), dateSetListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
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

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable)imgthemsach.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
                byte[] hinhanh = byteArrayOutputStream.toByteArray();
                String tacgiathem = tacgia.getSelectedItem().toString().trim();
                String idtacgia = daoSach.getMaTacGia(tacgiathem);
                String tensach = edttensach.getText().toString().trim();
                String theloaithem = theloai.getSelectedItem().toString().trim();
                String matheloai = daoSach.getMaTheLoai(theloaithem);
                String tomtat = edttomtat.getText().toString().trim();
                String ngayxuatban = edtngayxb.getText().toString().trim();
                String nhaxban = nhaxb.getSelectedItem().toString().trim();
                String manhaxb = daoSach.getManhaXB(nhaxban);
                String soluongStr = edtsoluong.getText().toString().trim();
                int soluong = 0;
                if (soluongStr.isEmpty()==false) {
                    soluong = Integer.parseInt(soluongStr);
                }
                boolean check = true;

                if(tensach.isEmpty()){
                    check = false;
                    showDialogNotiFail(" Tên sách không được để trống");
                    return;
                }
                if(tomtat.isEmpty()){
                    check = false;
                    showDialogNotiFail("Tóm tắt không được để rỗng");
                    return;
                }
                if(localDate.isAfter(chuyenDoiNgay(ngayxuatban,"yyyy-MM-dd"))==false){
                    check = false;
                    showDialogNotiFail("Ngày không được lớn hơn ngày hiên tại");
                    return;
                }
                if(soluong<=0){
                    check = false;
                    showDialogNotiFail("Số lượng không được nhỏ hơn 0");
                    return;
                }
                //String tenSach, int maTheLoai, String tomTat, int matacGia, String ngayXuatBan, int manxb, int soLuong, byte[] hinhAnh
                if(check){
                    Sach obj = new Sach(
                            tensach,
                            Integer.parseInt(matheloai),
                            tomtat,
                            Integer.parseInt(idtacgia),
                            ngayxuatban,
                            Integer.parseInt(manhaxb),
                            soluong,
                            hinhanh
                    );
                    long id = daoSach.them_sach(obj);
                    if(id != -1) {
                        showDialogNotiSuccess("Thêm sách thành công");
                        HomeFragment fragment = new HomeFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else{
                        showDialogNotiFail("Sách đã bị trùng");
                    }

                }
            }
        });

        return v;
    }


    public LocalDate chuyenDoiNgay(String ngayChuoi, String dinhDang) {
        LocalDate ngay = null;
        try {
            // Tạo một đối tượng DateTimeFormatter để chỉ định định dạng của ngày
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dinhDang);

            // Sử dụng phương thức parse để chuyển đổi chuỗi thành LocalDate
            ngay = LocalDate.parse(ngayChuoi, formatter);
        } catch (DateTimeParseException e) {
            // Xử lý ngoại lệ nếu chuỗi không đúng định dạng
            System.out.println("Không thể chuyển đổi chuỗi thành ngày: " + e.getMessage());
        }
        return ngay;
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
            imgthemsach.setImageBitmap(bitmap);
        }

        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                Bitmap scaledBitmap = scaleBitmap(bitmap,800);
                imgthemsach.setImageBitmap(scaledBitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ContentResolver getContentResolver() {
        return null;
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
    private void anhXa(View k) {
        imgthemsach = k.findViewById(R.id.imganhthemsach);
        btncam = k.findViewById(R.id.btncamera);
        btnfolder = k.findViewById(R.id.btnfolder);
        theloai = k.findViewById(R.id.spinnertheloai);
        nhaxb = k.findViewById(R.id.spinnernhaxb);
        edttensach = k.findViewById(R.id.edtthemtensach);
        edttomtat = k.findViewById(R.id.edtthemtomtat);
        edtsoluong = k.findViewById(R.id.edtthemsoluong);
        edtngayxb = k.findViewById(R.id.edtthemngayxb);
        btnthem = k.findViewById(R.id.btnthemthemsach);
        tacgia = k.findViewById(R.id.spinnertacgia);
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