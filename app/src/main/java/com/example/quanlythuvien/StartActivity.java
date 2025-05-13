package com.example.quanlythuvien;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartActivity extends AppCompatActivity {

    TextView txtstart;
    private ImageView logostart, imageView3;
    private TextView textView4, introText;
    private View startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Ánh xạ các thành phần từ layout
        logostart = findViewById(R.id.logostart);
        imageView3 = findViewById(R.id.imageView3);
        textView4 = findViewById(R.id.textView4);
        introText = findViewById(R.id.textView7);
        startButton = findViewById(R.id.imageView6);

        // Khởi tạo animation
        animateComponents();
        txtstart = findViewById(R.id.txtstart);
        txtstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_start = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_start);
            }
        });
    }
    private void animateComponents() {

        ObjectAnimator logoAnimator = ObjectAnimator.ofFloat(logostart, "translationY", -500f, 0f);
        logoAnimator.setDuration(2000);
        logoAnimator.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator welcomeAnimator = ObjectAnimator.ofFloat(textView4, "alpha", 0f, 1f);
        welcomeAnimator.setDuration(2000);
        welcomeAnimator.setStartDelay(700);

        ObjectAnimator introAnimator = ObjectAnimator.ofFloat(introText, "alpha", 0f, 1f);
        introAnimator.setDuration(2000);
        introAnimator.setStartDelay(800);

        ObjectAnimator buttonAnimator = ObjectAnimator.ofFloat(startButton, "translationY", 300f, 0f);
        buttonAnimator.setDuration(2000);
        buttonAnimator.setStartDelay(0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(logoAnimator, welcomeAnimator, introAnimator, buttonAnimator);
        animatorSet.start();
    }
}