package com.b.aman.atable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setLogo();

        Button btPractice = findViewById(R.id.bt_practice);
        Button btQuiz = findViewById(R.id.bt_quiz);
        ImageButton shareButton = findViewById(R.id.ib_shareButton);
        ImageButton likeButton = findViewById(R.id.ib_likeButton);

        btPractice.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PracticeTable.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein, R.anim.slideout);
        });

        btQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityQuiz.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein, R.anim.slideout);
        });

        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey checkout this useful app for Math Tables learning: https://play.google.com/store/apps/details?id=com.example.aman.table");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        });

        likeButton.setOnClickListener(v -> {
            Intent likeIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.example.aman.table"));
            startActivity(likeIntent);
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            // 1️⃣  Hide the automatic title coming from the activity label
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            // 2️⃣  Disable / hide the default “up” (back) arrow
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
    }

    private void setLogo() {
        ImageView imageViewBg = findViewById(R.id.iv_background);
        try {
            Glide.with(this)
                    .load(R.drawable.welcome1)
                    .dontAnimate()
                    .fitCenter()
                    .into(imageViewBg);
        } catch (Exception ignored) {
        }
    }
}
