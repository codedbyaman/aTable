package com.b.aman.atable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PracticeTable extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout tilInput;
    private TextInputEditText etInput;
    private MaterialButton btSubmit;
    private ChipGroup cgQuick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        // 1) TOOLBAR
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // Hide automatic title / back arrow
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        // 2) BIND VIEWS
        tilInput = findViewById(R.id.til_input);
        etInput = findViewById(R.id.et_input);
        btSubmit = findViewById(R.id.bt_submit);
        cgQuick = findViewById(R.id.cg_quick);

        // 3) INITIAL STATE
        btSubmit.setEnabled(false);               // disabled until input
        tilInput.setErrorEnabled(true);

        // 4) SETUP
        setupChips();
        setupInputWatcher();
        setupSubmitAction();
    }

    @SuppressLint("StringFormatInvalid")
    private void setupChips() {
        int[] presets = {2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 15, 20};
        for (int n : presets) {
            Chip chip = new Chip(this);
            chip.setText(String.valueOf(n));
            chip.setCheckable(false);
            chip.setChipBackgroundColorResource(R.color.colorPrimary);
            chip.setTextColor(ContextCompat.getColor(this, R.color.white));
            chip.setContentDescription(getString(R.string.quick_pick, n));
            chip.setOnClickListener(v -> etInput.setText(String.valueOf(n)));
            cgQuick.addView(chip);
        }
    }

    private void setupInputWatcher() {
        // max 4 digits
        etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void onTextChanged(CharSequence s, int st, int bef, int c) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String txt = s.toString().trim();
                // enable/disable button
                btSubmit.setEnabled(!txt.isEmpty());
                // clear any previous error
                if (!txt.isEmpty() && tilInput.isErrorEnabled()) {
                    tilInput.setError(null);
                }
            }
        });
    }

    private void setupSubmitAction() {
        btSubmit.setOnClickListener(v -> {
            String numberText = etInput.getText().toString().trim();

            if (numberText.isEmpty()) {
                tilInput.setError(getString(R.string.error_enter_number));
                etInput.requestFocus();
                return;
            }

            long number;
            try {
                number = Long.parseLong(numberText);
            } catch (NumberFormatException e) {
                tilInput.setError(getString(R.string.error_invalid_number));
                etInput.requestFocus();
                return;
            }

            // all good → launch DetailActivity
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("number", number);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein, R.anim.slideout);
        });
    }

    // convenience
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
