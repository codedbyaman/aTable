package com.b.aman.atable;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ActivityQuiz extends AppCompatActivity {

    private static final long QUESTION_DURATION_MS = 15_000; // 15 seconds
    private final Random random = new Random();
    private TextView tvNumber1, tvNumber2, tvAnswer, tvTimer;
    private Button btAns1, btAns2, btAns3, btAns4;
    private ImageButton ibNext;
    private View root;
    private int correctAnswer;
    private CountDownTimer timer;
    private Button[] answerButtons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // 1) findViewById
        root = findViewById(R.id.quiz_root);
        tvNumber1 = findViewById(R.id.tv_number1);
        tvNumber2 = findViewById(R.id.tv_number2);
        tvAnswer = findViewById(R.id.tv_answer);
        tvTimer = findViewById(R.id.tv_timer);
        btAns1 = findViewById(R.id.bt_ans1);
        btAns2 = findViewById(R.id.bt_ans2);
        btAns3 = findViewById(R.id.bt_ans3);
        btAns4 = findViewById(R.id.bt_ans4);
        ibNext = findViewById(R.id.next_button);

        answerButtons = new Button[]{btAns1, btAns2, btAns3, btAns4};

        // 2) setup toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        // 3) wire up click listeners
        for (Button b : answerButtons) {
            b.setOnClickListener(v -> checkAnswer((Button) v));
        }
        ibNext.setOnClickListener(v -> generateQuestion());

        // 4) first question
        generateQuestion();
    }

    private void generateQuestion() {
        // cancel any running timer
        if (timer != null) timer.cancel();

        // 1. Reset UI
        tvAnswer.setText("?");
        for (Button b : answerButtons) {
            b.setEnabled(true);
        }

        // 2. Pick two random factors
        int n1 = random.nextInt(20) + 1;
        int n2 = random.nextInt(12) + 1;
        correctAnswer = n1 * n2;

        tvNumber1.setText(String.valueOf(n1));
        tvNumber2.setText(String.valueOf(n2));

        // 3. Build a shuffled answer pool
        List<Integer> pool = new ArrayList<>();
        pool.add(correctAnswer);
        pool.add(correctAnswer + random.nextInt(5) + 1);
        pool.add(Math.max(1, correctAnswer - (random.nextInt(3) + 1)));
        pool.add(correctAnswer + random.nextInt(5) + 2);
        Collections.shuffle(pool);

        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(String.valueOf(pool.get(i)));
        }

        // 4. Start countdown
        startTimer();
    }

    private void startTimer() {
        timer = new CountDownTimer(QUESTION_DURATION_MS, 1_000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(String.format("%d s", millisUntilFinished / 1_000));
            }

            @Override
            public void onFinish() {
                tvTimer.setText("0 s");
                disableAnswers();
                Snackbar.make(root, "⏰ Time’s up!", Snackbar.LENGTH_SHORT).show();
                // allow manual Next tap or auto-advance after delay:
                new Handler(Looper.getMainLooper())
                        .postDelayed(ActivityQuiz.this::generateQuestion, 1_500);
            }
        }.start();
    }

    private void disableAnswers() {
        for (Button b : answerButtons) {
            b.setEnabled(false);
        }
    }

    private void checkAnswer(Button picked) {
        // stop timer immediately
        if (timer != null) timer.cancel();

        String chosenText = picked.getText().toString();
        boolean isCorrect = chosenText.equals(String.valueOf(correctAnswer));

        // reveal answer
        tvAnswer.setText(isCorrect ? chosenText : "?");

        Snackbar.make(root,
                        isCorrect ? "🎉 Correct!" : "❌ Wrong!",
                        Snackbar.LENGTH_SHORT)
                .show();

        // disable further taps
        disableAnswers();

        if (isCorrect) {
            // auto-advance after a short pause
            new Handler(Looper.getMainLooper())
                    .postDelayed(this::generateQuestion, 500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}
