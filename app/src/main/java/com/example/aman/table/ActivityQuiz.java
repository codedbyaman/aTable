package com.example.aman.table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.Random;

/**
 * Created by aman on 29/11/17.
 */

public class ActivityQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        final TextView tvNumber1 = (findViewById(R.id.tv_number1));
        Random random = new Random();
        int number1 = random.nextInt(21) + 1;
        tvNumber1.setText("" + number1);

        TextView tvNumber2 = (findViewById(R.id.tv_number2));
        int number2 = random.nextInt(10) + 1;
        tvNumber2.setText("" + number2);


        Button btAns1 = (findViewById(R.id.bt_ans1));
        btAns1.setText("" + number1 * number2);

        Button btAns2 = (findViewById(R.id.bt_ans2));
        btAns2.setText("" + random.nextInt(20) + 1);

        Button btAns3 = (findViewById(R.id.bt_ans3));
        btAns3.setText("" + random.nextInt(20) + 2);

        Button btAns4 = (findViewById(R.id.bt_ans4));
        btAns4.setText("" + random.nextInt(20) + 3);



        btAns1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}
