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

        Button btAns1 = (findViewById(R.id.bt_ans1));
        final Button btAns2 = (findViewById(R.id.bt_ans2));
        Button btAns3 = (findViewById(R.id.bt_ans3));
        Button btAns4 = (findViewById(R.id.bt_ans4));
        final TextView tvAnswer = (findViewById(R.id.tv_answer));

        final TextView tvNumber1 = (findViewById(R.id.tv_number1));
        TextView tvNumber2 = (findViewById(R.id.tv_number2));


        Random random = new Random();
        int number1 = random.nextInt(21) + 1;
        tvNumber1.setText("" + number1);

        int number2 = random.nextInt(10) + 1;
        tvNumber2.setText("" + number2);

        int number3 = random.nextInt(4) + 1;

        final int correctanswer;

        correctanswer = number1 * number2;


        if (number3 == 1) {
            btAns1.setText(Integer.toString(correctanswer));
            btAns2.setText(Integer.toString(correctanswer + 2));
            btAns3.setText(Integer.toString(correctanswer + 4));
            btAns4.setText(Integer.toString(correctanswer + 6));


        } else if (number3 == 2) {
            btAns2.setText(Integer.toString(correctanswer));
            btAns1.setText(Integer.toString(correctanswer + 2));
            btAns3.setText(Integer.toString(correctanswer + 4));
            btAns4.setText(Integer.toString(correctanswer + 6));


        } else if (number3 == 3) {
            btAns3.setText(Integer.toString(correctanswer));
            btAns2.setText(Integer.toString(correctanswer + 2));
            btAns1.setText(Integer.toString(correctanswer + 4));
            btAns4.setText(Integer.toString(correctanswer + 6));


        } else if (number3 == 4) {
            btAns4.setText(Integer.toString(correctanswer));
            btAns2.setText(Integer.toString(correctanswer + 2));
            btAns3.setText(Integer.toString(correctanswer + 4));
            btAns1.setText(Integer.toString(correctanswer + 6));


        }
        btAns1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               tvAnswer.setText(Integer.toString(correctanswer));

            }

        });
        btAns2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText(Integer.toString(correctanswer));
            }
        });

        btAns3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText(Integer.toString(correctanswer));
            }
        });
        btAns4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText(Integer.toString(correctanswer));
            }
        });
    }
}
