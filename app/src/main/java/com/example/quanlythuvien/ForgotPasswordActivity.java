package com.example.quanlythuvien;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuvien.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edtUserNameForgot,edtNew1Password,edtNew2Password;
    private ActivityForgotPasswordBinding binding;
    //    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
    SQLiteDatabase database=null;
    String DATABASE_NAME="library_manager.db";
    //End
    SQLiteDatabase sqLiteDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sqLiteDatabase=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        edtUserNameForgot = binding.edtUserNameForgot;
        edtNew1Password = binding.edtNew1Password;
        edtNew2Password = binding.edtNew2Password;



        binding.btnXacNhanDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameforgot = binding.edtUserNameForgot.getText().toString();
                String new1password = binding.edtNew1Password.getText().toString();
                String new2password = binding.edtNew2Password.getText().toString();
                if (usernameforgot.isEmpty() || new1password.isEmpty() || new2password.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!new1password.equals(new2password)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean usernameExists = checkUsernameExist(usernameforgot);
                if (!usernameExists) {
                    Toast.makeText(ForgotPasswordActivity.this, "Tên người dùng không tồn tại hoặc không chính xác", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean passwordChanged = changePassword(usernameforgot, new1password);
                if (passwordChanged) {
                    Toast.makeText(ForgotPasswordActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent_Login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent_Login);
                    finish(); // Kết thúc activity hiện tại để ngăn người dùng quay lại màn hình đổi mật khẩu
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Không thể thay đổi mật khẩu. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnQuayLaiLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_Login);
            }
        });
    }

    // Hàm kiểm tra xem tên đăng nhập có tồn tại trong cơ sở dữ liệu không
    private boolean checkUsernameExist(String username) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuanLy WHERE TenDangNhap = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Hàm thực hiện thay đổi mật khẩu trong cơ sở dữ liệu
    private boolean changePassword(String username, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("MatKhau", newPassword);
        int rowsAffected = sqLiteDatabase.update("QuanLy", values, "TenDangNhap = ?", new String[]{username});
        return rowsAffected > 0;
    }
}