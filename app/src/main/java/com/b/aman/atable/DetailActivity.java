package com.b.aman.atable;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.b.aman.atable.data.CacheHelper;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private TextToSpeech textToSpeech;
    private int result;
    private long number;
    private String currentLanguage;
    private static final String DEFAULT_LANGUAGE = "english";

    private TextView tvSpeech;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setupToolbar();
        setupUI();
        loadIntentData();
        createTable();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PracticeTable.class));
            overridePendingTransition(R.anim.slideout_back, R.anim.slidein_back);
        });
    }

    private void setupUI() {
        tvSpeech = findViewById(R.id.tv_table_data);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/chalk.ttf");
        tvSpeech.setTypeface(myFont);

        ImageButton homeButton = findViewById(R.id.ib_home);
        ImageButton returnButton = findViewById(R.id.ib_return);
        ImageButton settingButton = findViewById(R.id.ib_settingIcon);

        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(DetailActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_out_home, R.anim.slide_in_home);
        });

        returnButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_out_home, R.anim.slide_in_home);
        });

        settingButton.setOnClickListener(v -> {
            if (textToSpeech != null) textToSpeech.stop();
            PopupMenu popup = new PopupMenu(DetailActivity.this, v);
            popup.setOnMenuItemClickListener(this);
            popup.inflate(R.menu.popup_menu);
            popup.show();
        });
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        number = intent.getLongExtra("number", 0);
        TextView titleView = findViewById(R.id.tv_table_title);
        titleView.setText("Table of " + number);
    }

    private void createTable() {
        String lang = getCurrentLanguage();
        String tableData = lang.equals("hindi") ? createTableForHindi(number) : createTableForEnglish(number);
        tvSpeech.setText(tableData);
    }

    private String createTableForEnglish(long number) {
        StringBuilder tableData = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            tableData.append(number).append(" x ").append(i).append(" = ").append(number * i).append("\n");
        }
        return tableData.toString();
    }

    private String createTableForHindi(long number) {
        StringBuilder tableData = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            tableData.append(number).append(" x ").append(i).append("za = ").append(number * i).append("\n");
        }
        return tableData.toString();
    }

    public void doSomething(View v) {
        if (v.getId() == R.id.ib_speech) {
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(getApplicationContext(), "Your device is not compatible", Toast.LENGTH_SHORT).show();
            } else {
                String lang = getCurrentLanguage();
                String text = lang.equals("hindi") ?
                        getTextForHindi(createTableForHindi(number)) :
                        getTextForEnglish(createTableForEnglish(number));

                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }
    }

    private String getTextForEnglish(String text) {
        return text.replace("x", "into");
    }

    private String getTextForHindi(String text) {
        return text.replace("x", "").replace("=", "");
    }

    private String getCurrentLanguage() {
        if (currentLanguage == null) {
            currentLanguage = CacheHelper.getCurrentLanguage(getApplicationContext());
            if (currentLanguage == null || currentLanguage.isEmpty()) {
                currentLanguage = DEFAULT_LANGUAGE;
            }
        }
        return currentLanguage;
    }

    @Override
    protected void onStart() {
        super.onStart();
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                result = textToSpeech.setLanguage(Locale.ENGLISH);
            } else {
                Toast.makeText(getApplicationContext(), "Text-to-Speech not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
        super.onStop();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        currentLanguage = item.getTitle().toString().toLowerCase();
        CacheHelper.setCurrentLanguage(getApplicationContext(), currentLanguage);
        createTable(); // Refresh table after language change
        return true;
    }
}
