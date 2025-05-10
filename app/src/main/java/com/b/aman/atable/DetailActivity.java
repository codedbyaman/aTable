package com.b.aman.atable;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
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
import java.util.Objects;

public class DetailActivity extends AppCompatActivity
        implements PopupMenu.OnMenuItemClickListener, TextToSpeech.OnInitListener {

    /* constants */
    private static final String DEFAULT_LANGUAGE = "english";
    private static final Locale LOCALE_HINDI = new Locale("hi", "IN");

    /* fields */
    private TextToSpeech textToSpeech;
    private int ttsStatus = TextToSpeech.ERROR;
    private long number;
    private String currentLanguage;
    private TextView tvSpeech;

    /* lifecycle */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setupToolbar();
        setupUI();
        loadIntentData();
        fillTable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        textToSpeech = new TextToSpeech(this, this);
    }

    @Override
    public void onInit(int status) {
        ttsStatus = status;
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.ENGLISH); // default
        }
    }

    @Override
    protected void onStop() {
        if (textToSpeech != null) textToSpeech.shutdown();
        super.onStop();
    }

    /*toolbar */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(this, PracticeTable.class));
            overridePendingTransition(R.anim.slideout_back, R.anim.slidein_back);
        });
    }

    /* UI */
    private void setupUI() {
        tvSpeech = findViewById(R.id.tv_table_data);
        Typeface chalk = Typeface.createFromAsset(getAssets(), "fonts/chalk.ttf");
        tvSpeech.setTypeface(chalk);

        ImageButton homeButton = findViewById(R.id.ib_home);
        ImageButton settingButton = findViewById(R.id.ib_settingIcon);

        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.slide_out_home, R.anim.slide_in_home);
        });

        settingButton.setOnClickListener(v -> {
            if (textToSpeech != null) textToSpeech.stop();
            PopupMenu popup = new PopupMenu(this, v);
            popup.setOnMenuItemClickListener(this);
            popup.inflate(R.menu.popup_menu);   // “English” / “Hindi”
            popup.show();
        });
    }

    /* data / display */
    private void loadIntentData() {
        number = getIntent().getLongExtra("number", 0);
        ((TextView) findViewById(R.id.tv_table_title))
                .setText(getString(R.string.table_of, number));
    }

    private void fillTable() {
        tvSpeech.setText(buildVisualTable());
    }

    private String buildVisualTable() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            sb.append(number).append(" x ").append(i)
                    .append(" = ").append(number * i).append('\n');
        }
        return sb.toString();
    }

    /*speech */
    public void speakTable(View v) {
        if (v.getId() != R.id.ib_speech) return;

        if (ttsStatus != TextToSpeech.SUCCESS) {
            Toast.makeText(this, "Initializing speech engine….", Toast.LENGTH_SHORT).show();
            return;
        }

        // choose language + male Hindi voice if required
        if (isHindi()) {
            textToSpeech.setLanguage(LOCALE_HINDI);
            setMaleHindiVoiceIfAvailable();
        } else {
            textToSpeech.setLanguage(Locale.ENGLISH);
        }

        String utterance = isHindi() ? buildHindiSpeech() : buildEnglishSpeech();
        Toast.makeText(this, "🔊 Speaking table…", Toast.LENGTH_SHORT).show();
        textToSpeech.speak(utterance, TextToSpeech.QUEUE_FLUSH, null, "table");
    }

    private void setMaleHindiVoiceIfAvailable() {
        for (Voice voice : textToSpeech.getVoices()) {
            if (voice.getLocale().equals(LOCALE_HINDI)
                    && !voice.getName().toLowerCase().contains("female")) {
                textToSpeech.setVoice(voice);
                return;
            }
        }
    }

    private String buildEnglishSpeech() {
        return buildVisualTable()
                .replace(" x ", " into ")
                .replace(" = ", " equals ");
    }

    private String buildHindiSpeech() {
        final String[] words = {
                "one", "two", "three", "four", "five",
                "six", "seven", "eight", "nine", "ten"
        };
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            sb.append(number).append(' ')
                    .append(words[i - 1]).append(" za ")
                    .append(number * i);
            if (i < 10) sb.append(", ");
        }
        return sb.toString();
    }

    /*language helpers */
    private boolean isHindi() {
        return "hindi".equalsIgnoreCase(getCurrentLanguage());
    }

    private String getCurrentLanguage() {
        if (currentLanguage == null) {
            currentLanguage = CacheHelper.getCurrentLanguage(this);
            if (currentLanguage == null || currentLanguage.isEmpty()) {
                currentLanguage = DEFAULT_LANGUAGE;
            }
        }
        return currentLanguage;
    }

    /* popup-menu callback */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        currentLanguage = Objects.requireNonNull(item.getTitle()).toString().toLowerCase(Locale.ROOT);
        CacheHelper.setCurrentLanguage(this, currentLanguage);
        fillTable();                      // refresh screen text
        return true;
    }
}