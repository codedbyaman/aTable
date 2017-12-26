package com.b.aman.atable;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.widget.PopupMenu;
import com.b.aman.atable.data.CacheHelper;
import java.util.Locale;
/**
 * Created by aman on 17/11/17.
 */

public class DetailActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    TextToSpeech textToSpeech;
    int result;
    TextView tv_speech;
    String text;
    TextView tfont;
    long number;
    String currentLanguage;
    String DEFAULT_LANGUAGE = "english";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        TextView textView = findViewById(R.id.tv_table_title);
        ImageButton imageButton = findViewById(R.id.ib_home);
        ImageButton imageButton1 = findViewById(R.id.ib_return);
        ImageButton settingButton = findViewById(R.id.ib_settingIcon);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textToSpeech.stop();

                PopupMenu popup = new PopupMenu(DetailActivity.this, v);

                popup.setOnMenuItemClickListener(DetailActivity.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });

//        TextView toolbar_text = (findViewById(R.id.toolbar_text));
//        toolbar_text.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        tfont = (TextView) findViewById(R.id.tv_table_data);
        Typeface MyFont = Typeface.createFromAsset(getAssets(), "fonts/chalk.ttf");
        tfont.setTypeface(MyFont);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PracticeTable.class));
                overridePendingTransition(R.anim.slideout_back, R.anim.slidein_back);
            }
        });

        tv_speech = (TextView) findViewById(R.id.tv_table_data);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_home, R.anim.slide_in_home);
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();    /*to destroy activity*/
                overridePendingTransition(R.anim.slide_out_home, R.anim.slide_in_home);
            }
        });

        Intent intent = getIntent();

        number = intent.getLongExtra("number", 0);
        textView.setText("table of " + number);

        /*String tableData = createTable(number);
        String data = tableData.replace("za", "");
        textView1.setText(data);*/

        createTable();

    }

    private void createTable() {
        String tableData = createTableForEnglish(number);
        TextView textView1 = findViewById(R.id.tv_table_data);
        textView1.setText(tableData);
    }


    private String createTableForHindi(long number) {
        String tableData = "";
        for (long i = 1; i <= 10; i++) {
            String line = number + " x " + i + "za = " + (number * i) + "\n";
            tableData = tableData + line;
        }
        return tableData;
    }

    private String createTableForEnglish(long number) {
        String tableData = "";
        for (long i = 1; i <= 10; i++) {
            String line = number + " x " + i + " = " + (number * i) + "\n";
            tableData = tableData + line;
        }
        return tableData;
    }

    public void doSomething(View v) {
        switch (v.getId()) {

            case R.id.ib_speech:

                if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {

                    Toast.makeText(getApplicationContext(), "Your device is not compatible", Toast.LENGTH_SHORT).show();


                } else {
                    String language = CacheHelper.getCurrentLanguage(getApplicationContext());
                    if (language.equals("english")) {
                        text = createTableForEnglish(number);
                        text = getTextForEnglish(text);

                    } else if (language.equals("hindi")) {
                        text = createTableForHindi(number);
                        text = getTextForHindi(text);

                    }
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }

                break;
        }
    }

    private String getTextForHindi(String text) {
        text = text.replace("x", "");
        text = text.replace("=", "");
        return text;
    }

    private String getTextForEnglish(String text) {
        text = text.replace("x", "into");
        return text;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        textToSpeech = new TextToSpeech(DetailActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = textToSpeech.setLanguage(Locale.ENGLISH);
                } else {
                    Toast.makeText(getApplicationContext(), "Your device is not compatible", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String title = item.getTitle().toString();


        currentLanguage = title.toLowerCase();
        CacheHelper.setCurrentLanguage(getApplicationContext(), currentLanguage);


        return false;
    }
}


