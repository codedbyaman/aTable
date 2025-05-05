package com.b.aman.atable;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PracticeTable extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pratice);

        setupToolbar();

        EditText editText = findViewById(R.id.et_input);
        Button button = findViewById(R.id.bt_submit);

        button.setOnClickListener(v -> {
            String numberText = editText.getText().toString().trim();

            if (numberText.isEmpty()) {
                Toast.makeText(this, "Please enter any number", Toast.LENGTH_LONG).show();
                return;
            }

            if (numberText.length() > 4) {
                Toast.makeText(this, "Please enter a number less than 10000", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                long number = Long.parseLong(numberText);
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("number", number);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.slideout_back, R.anim.slidein_back);
        });
    }
}
