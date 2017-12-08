package com.example.aman.table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Created by aman on 29/11/17.
 */

public class ActivityQuiz extends AppCompatActivity {

    int correctanswer;
    Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        final Button btAns1 = (findViewById(R.id.bt_ans1));
        final Button btAns2 = (findViewById(R.id.bt_ans2));
        final Button btAns3 = (findViewById(R.id.bt_ans3));
        final Button btAns4 = (findViewById(R.id.bt_ans4));
        final ImageButton imbNext = (findViewById(R.id.next_button));
        final TextView tvAnswer = (findViewById(R.id.tv_answer));

        final TextView tvNumber1 = (findViewById(R.id.tv_number1));
        final TextView tvNumber2 = (findViewById(R.id.tv_number2));


        final int number1 = random.nextInt(21) + 1;
        tvNumber1.setText("" + number1);

        final int number2 = random.nextInt(10) + 1;
        tvNumber2.setText("" + number2);

        final int number3 = random.nextInt(4) + 1;


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
                if (btAns1.getText().toString().equals(Integer.toString(correctanswer))) {
                    Toast.makeText(ActivityQuiz.this, "Correct answer", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityQuiz.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }

        });
        btAns2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText(Integer.toString(correctanswer));
                if (btAns2.getText().toString().equals(Integer.toString(correctanswer))) {
                    Toast.makeText(ActivityQuiz.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityQuiz.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btAns3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText(Integer.toString(correctanswer));
                if (btAns3.getText().toString().equals(Integer.toString(correctanswer))) {
                    Snackbar.make(btAns3, "Correct Answer", Snackbar.LENGTH_SHORT).show();

                } else {
                    Snackbar.make(btAns3,"Wrong Answer", Snackbar.LENGTH_SHORT).show();


                }


            }
        });
        btAns4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText(Integer.toString(correctanswer));
                if (btAns4.getText().toString().equals(Integer.toString(correctanswer))) {
                    Toast.makeText(ActivityQuiz.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityQuiz.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }

            }
        });

        imbNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(ActivityQuiz.this, "Next Question", Toast.LENGTH_SHORT).show();

                int number1 = random.nextInt(20) + 1;
                tvNumber1.setText("" + number1);

                final int number2 = random.nextInt(10) + 1;
                tvNumber2.setText("" + number2);

                correctanswer = number1 * number2;

                int number4 = random.nextInt(4) + 1;

                tvAnswer.setText("?");


                if (number4 == 1) {
                    btAns1.setText(Integer.toString(correctanswer));
                    btAns2.setText(Integer.toString(correctanswer + 2));
                    btAns3.setText(Integer.toString(correctanswer + 4));
                    btAns4.setText(Integer.toString(correctanswer + 6));

                } else if (number4 == 2) {

                    btAns2.setText(Integer.toString(correctanswer));
                    btAns1.setText(Integer.toString(correctanswer + 2));
                    btAns3.setText(Integer.toString(correctanswer + 4));
                    btAns4.setText(Integer.toString(correctanswer + 6));

                } else if (number4 == 3) {

                    btAns3.setText(Integer.toString(correctanswer));
                    btAns2.setText(Integer.toString(correctanswer + 2));
                    btAns1.setText(Integer.toString(correctanswer + 4));
                    btAns4.setText(Integer.toString(correctanswer + 6));

                } else if (number4 == 4) {

                    btAns4.setText(Integer.toString(correctanswer));
                    btAns2.setText(Integer.toString(correctanswer + 2));
                    btAns3.setText(Integer.toString(correctanswer + 4));
                    btAns1.setText(Integer.toString(correctanswer + 6));
                }


            }
        });
    }
}
