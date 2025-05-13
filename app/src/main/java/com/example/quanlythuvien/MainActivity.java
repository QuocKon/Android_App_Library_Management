package com.example.quanlythuvien;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlythuvien.dao.DAOQuanLy;
import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.fragment.DangXuatFragment;
import com.example.quanlythuvien.fragment.DoiMatKhauFragment;
import com.example.quanlythuvien.fragment.DoiThongTinCaNhanFragment;
import com.example.quanlythuvien.fragment.HomeFragment;
import com.example.quanlythuvien.fragment.QuanLyNXBFragment;
import com.example.quanlythuvien.fragment.QuanLyTacGiaFragment;
import com.example.quanlythuvien.fragment.QuyDinhFragment;
import com.example.quanlythuvien.fragment.ThongKeFragment;
import com.example.quanlythuvien.fragment.VeUngDungFragment;
import com.example.quanlythuvien.fragment.XemThongTinTraSachFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_XEMTHONGTIN = 1;
    private static final int FRAGMENT_THONGKE = 2;
    private static final int FRAGMENT_QLTG= 3;
    private static final int FRAGMENT_QLNXB = 4;
    private static final int FRAGMENT_VEUNGDUNG = 6;
    private static final int FRAGMENT_QUYDINH= 5;
    private static final int FRAGMENT_DOIMATKHAU = 7;
    private static final int FRAGMENT_DOITTCANHAN = 8;
    private static final int FRAGMENT_DANGXUAT= 9;
    private int currentFragment = FRAGMENT_HOME;
    Intent myIntent;
    String data="";
    DAOQuanLy daoQuanLy;
    int ma = 0;
    private DrawerLayout drawer;
    public  static SQLiteDatabase sqLiteDatabase = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        daoQuanLy = new DAOQuanLy(this);
        myIntent = getIntent();
        if (myIntent != null && myIntent.getExtras() != null) {
            data = (myIntent.getStringExtra("tenTaiKhoan"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteDatabase = openOrCreateDatabase("library_manager.db", MODE_PRIVATE, null);
        // Khởi tạo singleton database
        DatabaseSingleton.initialize(sqLiteDatabase);

        sqLiteDatabase = openOrCreateDatabase("library_manager.db", MODE_PRIVATE, null);
        // Khởi tạo singleton database
        DatabaseSingleton.initialize(sqLiteDatabase);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());
        setTitle("PTIT-Library");
        navigationView.getMenu().findItem(R.id.nav_Home).setChecked(true);

        checkBatteryLevel();
    }

    private void checkBatteryLevel() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level / (float) scale * 100;

            if (batteryPct < 20) {
                // Hiển thị thông báo "Pin yếu!"
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Pin yếu!");
                builder.setMessage("Pin của bạn đang yếu. Vui lòng sạc pin để tiếp tục sử dụng.");
                builder.setIcon(R.drawable.baseline_battery_alert_24);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        ma = daoQuanLy.getMaQuanLy(data);
        if(id==R.id.nav_Home){
            if(currentFragment!=FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                setTitle("PTIT-Library");
                currentFragment = FRAGMENT_HOME;
            }
        }
        else if(id == R.id.nav_DangXuat){
            if(currentFragment!=FRAGMENT_DANGXUAT){
                replaceFragment(new DangXuatFragment());
                setTitle("Đăng xuất");
                currentFragment = FRAGMENT_DANGXUAT;
            }
        }
        else if(id == R.id.nav_ThongKe){
            if(currentFragment!=FRAGMENT_THONGKE){
                replaceFragment(new ThongKeFragment());
                setTitle("Thống kê");
                currentFragment = FRAGMENT_THONGKE;
            }
        }else if(id == R.id.nav_NhaXuatBan){
            if(currentFragment!=FRAGMENT_QLNXB){
                replaceFragment(new QuanLyNXBFragment());
                setTitle("Quản lý nhà xuất bản");
                currentFragment = FRAGMENT_QLNXB;
            }
        }else if(id == R.id.nav_TacGia){
            if(currentFragment!=FRAGMENT_QLTG){
                replaceFragment(new QuanLyTacGiaFragment());
                setTitle("Quản lý tác giả");
                currentFragment = FRAGMENT_QLTG;
            }
        }else if(id == R.id.nav_QuyDinh){
            if(currentFragment!=FRAGMENT_QUYDINH){
                replaceFragment(new QuyDinhFragment());
                setTitle("Quy định thư viên");
                currentFragment = FRAGMENT_QUYDINH;
            }
        }else if(id == R.id.nav_VeUngDung){
            if(currentFragment!=FRAGMENT_VEUNGDUNG){
                replaceFragment(new VeUngDungFragment());
                setTitle("Thông tin về ứng dụng");
                currentFragment = FRAGMENT_VEUNGDUNG;
            }
        }else if(id == R.id.nav_ChangePass){
            if(currentFragment!=FRAGMENT_DOIMATKHAU){
                replaceFragment(new DoiMatKhauFragment());
                setTitle("Đổi mật khẩu");
                currentFragment = FRAGMENT_DOIMATKHAU;
            }
        }else if(id == R.id.nav_XemThongTinTraSach){
            if(currentFragment!=FRAGMENT_XEMTHONGTIN){
                if(ma == daoQuanLy.getMaQuanLy("tuan")){
                    replaceFragment(new XemThongTinTraSachFragment());
                    setTitle("Xem mã trả sách");
                    currentFragment = FRAGMENT_XEMTHONGTIN;
                }
                else {
                    Toast.makeText(this, "Bạn không có chức năng này", Toast.LENGTH_SHORT).show();
                }

            }
        }
        else if(id == R.id.nav_DoiThongTinTaiKhoan){
            if(currentFragment!=FRAGMENT_DOITTCANHAN){
                replaceFragment(new DoiThongTinCaNhanFragment());
                setTitle("Đổi thông tin cá nhân");
                currentFragment = FRAGMENT_DOITTCANHAN;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }


}