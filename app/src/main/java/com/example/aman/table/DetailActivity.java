

package com.example.aman.table;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import org.w3c.dom.Text;

/**
 * Created by aman on 17/11/17.
 */

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textView = findViewById(R.id.tv_table_title);
        TextView textView1 = findViewById(R.id.tv_table_data);
        ImageButton imageButton = findViewById(R.id.bt_home);
        ImageButton imageButton1 = findViewById(R.id.bt_return);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_home, R.anim.slide_in_home);
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish ();    /*to destroy activity*/
                overridePendingTransition(R.anim.slide_out_home, R.anim.slide_in_home);
            }
        });

        Intent intent = getIntent();

        long number = intent.getLongExtra("number", 0);
        textView.setText("table of "+ number);

        String tableData = createTable(number);
        textView1.setText(tableData);

    }

    private String createTable(long number) {
        String tableData ="";
        for (long i=1; i<=10; i++){
            String line = number + " x " + i + " = " + (number * i) + "\n";
            tableData = tableData+line;
        }
        return tableData;



    }
}


