package com.example.quanlythuvien;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuvien.databinding.ActivitySignupBinding;
import com.example.quanlythuvien.model.Validate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignupActivity extends AppCompatActivity {

    Validate validate;
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;
    String DATABASE_NAME="library_manager.db";
    //End
    SQLiteDatabase sqLiteDatabase = null;

    private ActivitySignupBinding binding;

    EditText edtBirth;

    private SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        validate = new Validate();
        sqLiteDatabase=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        edtBirth = findViewById(R.id.edtBirth);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        edtBirth.setText(sdf.format(new Date()));
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = binding.edtFullName.getText().toString();
                String birth = binding.edtBirth.getText().toString();
                String sex = binding.edtSex.getText().toString();
                String address = binding.edtAddress.getText().toString();
                String phone = binding.edtPhone.getText().toString();
                String gmail = binding.edtGmail.getText().toString();
                String username = binding.edtUserSignup.getText().toString();
                String password = binding.edtPassSignup.getText().toString();

                validate = new Validate();
                if(fullname.equals("") || birth.equals("") || sex.equals("")|| address.equals("") || phone.equals("") || gmail.equals("") || username.equals("") || password.equals("")){
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ thông tin trước khi đăng ký", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkAccount =validate. checkAccountValid(username, password, sqLiteDatabase);
                    if(checkAccount==false){
                        Boolean phoneNew = validate.checkPhone(phone);
                        Boolean emailValid = validate.checkGmail(gmail);
                        if (phoneNew){
                            if (emailValid) { // Kiểm tra email hợp lệ
                                Boolean insert = validate.insertData(fullname, birth, sex, address, phone, gmail, username, password, sqLiteDatabase);
                                if (insert) {
                                    Toast.makeText(SignupActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent_Login = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent_Login);
                                } else {
                                    Toast.makeText(SignupActivity.this, "Tạo tài khoản mới thất bại", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignupActivity.this, "Email không hợp lệ! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignupActivity.this, "Số điện thoại không hợp lệ! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Tài khoản đã ồn tại! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_Login);
            }
        });
        edtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(SignupActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                c.set(year, month, dayOfMonth);
                                edtBirth.setText(sdf.format(c.getTime()));
                            }
                        }, mYear, mMonth, mDay);
                d.show();
            }
        });
    }
}