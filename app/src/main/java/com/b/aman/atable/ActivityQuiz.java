package com.b.aman.atable;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class ActivityQuiz extends AppCompatActivity {

    private final Random random = new Random();
    private int correctAnswer;
    private Button btAns1, btAns2, btAns3, btAns4;
    private ImageButton imbNext;
    private TextView tvAnswer, tvNumber1, tvNumber2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupViews();
        setupToolbar();
        generateQuestion();

        btAns1.setOnClickListener(v -> checkAnswer(btAns1));
        btAns2.setOnClickListener(v -> checkAnswer(btAns2));
        btAns3.setOnClickListener(v -> checkAnswer(btAns3));
        btAns4.setOnClickListener(v -> checkAnswer(btAns4));
        imbNext.setOnClickListener(v -> generateQuestion());
    }

    private void setupViews() {
        btAns1 = findViewById(R.id.bt_ans1);
        btAns2 = findViewById(R.id.bt_ans2);
        btAns3 = findViewById(R.id.bt_ans3);
        btAns4 = findViewById(R.id.bt_ans4);
        imbNext = findViewById(R.id.next_button);
        tvAnswer = findViewById(R.id.tv_answer);
        tvNumber1 = findViewById(R.id.tv_number1);
        tvNumber2 = findViewById(R.id.tv_number2);
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

    private void generateQuestion() {
        int number1 = random.nextInt(20) + 1;
        int number2 = random.nextInt(10) + 1;
        correctAnswer = number1 * number2;

        tvNumber1.setText(String.valueOf(number1));
        tvNumber2.setText(String.valueOf(number2));
        tvAnswer.setText("?");

        int correctPosition = random.nextInt(4);
        int[] answers = {
                correctAnswer + 2,
                correctAnswer + 4,
                correctAnswer + 6,
                correctAnswer
        };

        Button[] buttons = {btAns1, btAns2, btAns3, btAns4};
        for (int i = 0; i < 4; i++) {
            buttons[i].setText(String.valueOf(answers[(i + correctPosition) % 4]));
        }
    }

    private void checkAnswer(Button button) {
        if (button.getText().toString().equals(String.valueOf(correctAnswer))) {
            tvAnswer.setText(String.valueOf(correctAnswer));
            Snackbar.make(button, "Correct Answer", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(button, "Wrong Answer", Snackbar.LENGTH_SHORT).show();
        }
    }
}