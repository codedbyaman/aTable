package com.b.aman.atable;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());


        setContentView(R.layout.activity_main);

        setLogo();

        Button btPractice = (findViewById(R.id.bt_practice));
        Button btQuiz = (findViewById(R.id.bt_quiz));
        ImageButton shareButton = (findViewById(R.id.ib_sharebutton));
        ImageButton likeButton = (findViewById(R.id.ib_likeButton));


        btPractice.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PracticeTable.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
            }
        });

        btQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityQuiz.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);


            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey checkout this useful app for Math Tables learning :https://play.google.com/store/apps/details?id=com.example.aman.table");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent likeIntent = new Intent(Intent.ACTION_VIEW);
                likeIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.example.aman.table"));
                startActivity(likeIntent);


            }
        });


//
//        TextView textView = findViewById(R.id.toolbar_text);
//        textView.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    private void setLogo() {
        ImageView imageViewBg = (ImageView) findViewById(R.id.iv_background);

        try {
            Glide.with(this).load(getResources().getDrawable(R.drawable.welcome1)).dontAnimate().fitCenter().into(imageViewBg);
        } catch (Exception e) {
        }
    }

}



