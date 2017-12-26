package com.b.aman.atable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

/**
 * Created by aman on 21/11/17.
 */

public class PracticeTable extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pratice);
        Button button = findViewById(R.id.bt_submit);
        final EditText editText = findViewById(R.id.et_input);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numberText = editText.getText().toString();
                if (numberText.isEmpty()) {
                    Toast.makeText(PracticeTable.this, "Please enter any number", Toast.LENGTH_LONG).show();

                    return;
                }

                if (numberText.length() > 4) {
                    Toast.makeText(PracticeTable.this, "Please enter a number less than 10000", Toast.LENGTH_SHORT).show();
                    return;
                }

                long number = Long.parseLong(numberText);


                Intent intent = new Intent(PracticeTable.this, DetailActivity.class);
                intent.putExtra("number", number);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);

            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slideout_back, R.anim.slidein_back);
            }
        });

//        TextView textView = findViewById(R.id.toolbar_text);
//        textView.setVisibility(View.GONE);


    }
}
