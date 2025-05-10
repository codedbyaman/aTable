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

        // Button click listener for number validation
        button.setOnClickListener(v -> {
            String numberText = editText.getText().toString().trim();

            // Validate input is not empty
            if (numberText.isEmpty()) {
                showToast("Please enter a number");
                return;
            }

            // Validate input is within expected length
            if (numberText.length() > 4) {
                showToast("Please enter a number less than 10000");
                return;
            }

            try {
                // Parse the input number
                long number = Long.parseLong(numberText);

                // Create and start an intent to DetailActivity with the number
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("number", number);  // Pass number to DetailActivity
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid number
                showToast("Invalid number");
            }
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

    // Utility method for displaying toasts
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}