package com.example.quanlythuvien.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.QuyDinhAdapter;
import com.example.quanlythuvien.dao.QuyDinhDAO;
import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.QuyDinh;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class QuyDinhFragment extends Fragment {
    Dialog dialog;
    private RecyclerView recyclerViewQuyDinh;
    private QuyDinhDAO quyDinhDAO;
    private QuyDinhAdapter quyDinhAdapter;
    private static final int REQUEST_CODE_FOLDER = 123;
    Button btnThemQuyDinh;
    EditText edtPhoneGiamDoc;
    ImageView imgPhone, imgMess;
    ArrayList<QuyDinh> listQuyDinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_quy_dinh, container, false);

        listQuyDinh = new ArrayList<>();
        recyclerViewQuyDinh = view.findViewById(R.id.recycle_QuyDinh);
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor  cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuyDinh", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            QuyDinh quyDinh = new QuyDinh(cursor.getInt(0), cursor.getString(1), cursor.getBlob(2));
            listQuyDinh.add(quyDinh);
            cursor.moveToNext();
        } 
        cursor.close();
        quyDinhAdapter = new QuyDinhAdapter(listQuyDinh, getContext(), this);
        recyclerViewQuyDinh.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerViewQuyDinh.setAdapter(quyDinhAdapter); // Di chuyển vào đây

        btnThemQuyDinh = view.findViewById(R.id.btnAddQuyDinh);
        edtPhoneGiamDoc = view.findViewById(R.id.edtPhoneGiamDoc);
        imgMess = view.findViewById(R.id.imgMess);
        imgPhone = view.findViewById(R.id.imgPhone);
        edtPhoneGiamDoc.setText("0345678129");
        imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  call_intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+edtPhoneGiamDoc.getText().toString()));
                //Require Comfirm before call
                if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(call_intent);
            }
        });

        imgMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent SmsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+edtPhoneGiamDoc.getText().toString()) );
                    startActivity(SmsIntent);
                }
        });
        btnThemQuyDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogInsert();
            }
        }); 

        return  view;
    }
    public  void showDialogInsert(){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_quydinh);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);

        ImageButton imgfolder = dialog.findViewById(R.id.btnfolder);
        ImageView imgViewQuyDinh = dialog.findViewById(R.id.imgViewQuyDinh);
        imgViewQuyDinh.setImageResource(R.drawable.logo);
        Button btnCancelThemQD = dialog.findViewById(R.id.btnCancelThemQD);
        Button btnXacNhanThemQD = dialog.findViewById(R.id.btnXacNhanThemQD);
        dialog.show();

        imgfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        btnCancelThemQD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhanThemQD.setOnClickListener(new View.OnClickListener() {
            Integer flag =0;
            @Override
            public void onClick(View v) {
                QuyDinh quyDinh = new QuyDinh();
                TextInputEditText txtNoiDungQD= dialog.findViewById(R.id.txtNDThemMoiQD);
                String NoiDung = txtNoiDungQD.getText().toString();
                if(NoiDung.trim().length()==0){
                    flag = 0;
                }
                else{
                    quyDinh.setNoiDung(NoiDung);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable)imgViewQuyDinh.getDrawable();
                    if(bitmapDrawable!=null){
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        byte[] imageByteArray = getByteArrayFromBitmap(bitmap);
                        quyDinh.setAnh(imageByteArray);

                    quyDinhDAO  = new QuyDinhDAO(getContext());
                    long insert = quyDinhDAO.insert(quyDinh);
                    if(insert==0){
                        flag = 1;
                    }
                    else{
                        listQuyDinh.clear();
                        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
                        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuyDinh", null);
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            quyDinh = new QuyDinh(cursor.getInt(0), cursor.getString(1), cursor.getBlob(2));
                            listQuyDinh.add(quyDinh);
                            cursor.moveToNext();
                        }
                        cursor.close();
                        quyDinhAdapter.notifyDataSetChanged();
                        flag = 2;
                    }
                    }else{
                        flag =3;
                    }
                }

                dialog.dismiss();
                if(flag==2){
                    showDialogNotifi("Thêm quy định thành công!");
                } else if (flag ==1) {
                    showDialogNotiFail("Thêm không thành công!!");
                }else if (flag ==0) {
                    showDialogNotiFail("Nội dung không được để trống!!");
                }else if (flag ==3) {
                    showDialogNotiFail("Ảnh không được để trống!!");
                }
            }
        });
    }
    public  void showDialogDelete(Integer ID){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_xoa_quydinh);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);
        Button btnCancelXoa = dialog.findViewById(R.id.btnCancelXoaQD);
        Button btnXacNhanXoaTacGia = dialog.findViewById(R.id.btnXacNhanXoaQD);
        dialog.show();
        btnCancelXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhanXoaTacGia.setOnClickListener(new View.OnClickListener() {
            Integer flag =0;
            @Override
            public void onClick(View v) {
                quyDinhDAO  = new QuyDinhDAO(getContext());
                Integer delete = quyDinhDAO.delete(ID);
                if(delete==0){
                    flag =0;
                }
                else{
                    listQuyDinh.clear();
                    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuyDinh", null);
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        QuyDinh quyDinh = new QuyDinh(cursor.getInt(0), cursor.getString(1), cursor.getBlob(2));
                        listQuyDinh.add(quyDinh);
                        cursor.moveToNext();
                    }
                    cursor.close();
                    quyDinhAdapter.notifyDataSetChanged();
                    flag =1;
                }
                dialog.dismiss();
               if (flag ==1) {
                    showDialogNotifi("Xóa quy định thành công!!");
                }else if (flag ==0) {
                    showDialogNotiFail("Xóa quy định không thành công!");
                }
            }
        });
    }
    public  void showDialogUpdate(Integer ID){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_sua_quydinh);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);

        ImageButton btnfolderUpdate = dialog.findViewById(R.id.btnfolderUpdate);
        ImageView imgViewQuyDinhUpdate = dialog.findViewById(R.id.imgViewQuyDinhUpdate);
        Button btnCancelUpdateQD = dialog.findViewById(R.id.btnCancelUpdateQD);
        Button btnXacNhanUpdateQD = dialog.findViewById(R.id.btnXacNhanUpdateQD);
        TextInputEditText txtNoiDungQD= dialog.findViewById(R.id.txtNDThemMoiQD);

        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuyDinh WHERE MaQuyDinh=?", new String[]{String.valueOf(ID)});
        String currentContent = "";
        byte[] currentImg =null;
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            currentContent = cursor.getString(1);
            currentImg = cursor.getBlob(2);
            cursor.moveToNext();
        }
        cursor.close();
        txtNoiDungQD.setText(currentContent);
        Bitmap bitmap = BitmapFactory.decodeByteArray(currentImg, 0, currentImg.length);
        imgViewQuyDinhUpdate.setImageBitmap(bitmap);


        dialog.show();
        btnfolderUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 12);
            }
        });

        btnCancelUpdateQD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhanUpdateQD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer flag =0;
                String NoiDung  = txtNoiDungQD.getText().toString();
                if(NoiDung.trim().length()==0){
                    flag =0;
                }else {
                    QuyDinh quyDinh = new QuyDinh();
                    quyDinh.setNoiDung(NoiDung);
                    quyDinh.setMaQuyDinh(ID);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable)imgViewQuyDinhUpdate.getDrawable();

                    if(bitmapDrawable!=null){
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        byte[] imageByteArray = getByteArrayFromBitmap(bitmap);
                        quyDinh.setAnh(imageByteArray);
                    }
                    quyDinhDAO  = new QuyDinhDAO(getContext());
                    long update = quyDinhDAO.update(quyDinh);
                    if(update==0){
                        flag = 1;
                    }
                    else{
                        listQuyDinh.clear();
                        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
                        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuyDinh", null);
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            quyDinh = new QuyDinh(cursor.getInt(0), cursor.getString(1), cursor.getBlob(2));
                            listQuyDinh.add(quyDinh);
                            cursor.moveToNext();
                        }
                        cursor.close();
                        quyDinhAdapter.notifyDataSetChanged();
                        flag = 2;
                    }
                }

                dialog.dismiss();
               if(flag==2){
                   showDialogNotifi("Sửa quy định thành công!");
               } else if (flag ==1) {
                   showDialogNotiFail("Sửa không thành công!!");
               }else if (flag ==0) {
                   showDialogNotiFail("Nội dung không được để trống!!");
               }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    ImageView imgViewQuyDinh = dialog.findViewById(R.id.imgViewQuyDinh);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    // Giảm kích thước ảnh
                    Bitmap scaledBitmap = scaleBitmap(bitmap, 800); // Thay đổi 800 thành kích thước tối đa mong muốn
                    imgViewQuyDinh.setImageBitmap(scaledBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == 12 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    ImageView imgViewQuyDinh = dialog.findViewById(R.id.imgViewQuyDinhUpdate);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    // Giảm kích thước ảnh
                    Bitmap scaledBitmap = scaleBitmap(bitmap, 800); // Thay đổi 800 thành kích thước tối đa mong muốn
                    imgViewQuyDinh.setImageBitmap(scaledBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public  void showDialogNotifi(String notit){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);
        Button btnCancelNotit = dialog.findViewById(R.id.btnCancelNotit);
        TextView txtnoti = dialog.findViewById(R.id.txtNotification);
        txtnoti.setText(notit);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
        btnCancelNotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public  void showDialogNotiFail(String notit ){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_fail);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);
        Button btnCancelFail = dialog.findViewById(R.id.btnCancelFail);
        TextView txtFail = dialog.findViewById(R.id.txtFail);
        txtFail.setText(notit);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng dialog sau 2 giây
                dialog.dismiss();
            }
        }, 2000);
        btnCancelFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
