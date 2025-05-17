package com.b.aman.atable;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ActivityQuiz extends AppCompatActivity {
    private static final int TOTAL_QUESTIONS = 10;
    private static final long QUESTION_DURATION_MS = 15_000;
    private final Random random = new Random();
    private TextView tvTimer, tvNumber1, tvNumber2, tvAnswer;
    private Button[] answerButtons;
    private Button btStart, btReset, btNext, btResult;
    private CountDownTimer timer;
    private int currentIndex, score, correctAnswer;
    private boolean quizStarted = false;
    private Typeface chalkFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // ① load your chalk font
        chalkFace = Typeface.createFromAsset(getAssets(), "fonts/chalk.ttf");

        // ② toolbar
        setupToolbar();

        // ③ make all your findViewById() calls before using the views
        bindViews();

        // ④ now it's safe to apply your custom font
        applyChalkFont();

        // ⑤ set up gif in your timer icon
        ImageView iv = findViewById(R.id.iv_timer_icon);
        Glide.with(this)
                .asGif()
                .load(R.drawable.hourglass_icon)    // your GIF in drawable/
                .into(iv);

        // ⑥ rest of your wiring
        setupListeners();
        resetQuizState();
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void applyChalkFont() {
        tvNumber1.setTypeface(chalkFace);
        tvNumber2.setTypeface(chalkFace);
        tvAnswer.setTypeface(chalkFace);
        tvTimer.setTypeface(chalkFace);

        for (Button b : answerButtons) {
            b.setTypeface(chalkFace);
        }
    }

    private void bindViews() {
        tvTimer = findViewById(R.id.tv_timer);
        tvNumber1 = findViewById(R.id.tv_number1);
        tvNumber2 = findViewById(R.id.tv_number2);
        tvAnswer = findViewById(R.id.tv_answer);

        answerButtons = new Button[]{
                findViewById(R.id.bt_ans1),
                findViewById(R.id.bt_ans2),
                findViewById(R.id.bt_ans3),
                findViewById(R.id.bt_ans4)
        };

        btStart = findViewById(R.id.bt_start);
        btReset = findViewById(R.id.bt_reset);
        btNext = findViewById(R.id.bt_next);
        btResult = findViewById(R.id.bt_result);
    }

    private void setupListeners() {
        btStart.setOnClickListener(v -> {
            quizStarted = true;
            score = 0;
            currentIndex = 0;
            btStart.setEnabled(false);
            btReset.setEnabled(true);
            btNext.setEnabled(true);
            btResult.setEnabled(false);
            launchQuestionWithTimer();
        });

        btReset.setOnClickListener(v -> {
            quizStarted = false;
            resetQuizState();
        });

        btNext.setOnClickListener(v -> {
            if (!quizStarted) {
                // preview next question without timer
                nextQuestionCore();
            } else {
                launchQuestionWithTimer();
            }
        });

        btResult.setOnClickListener(v -> showResult());

        for (Button b : answerButtons) {
            b.setOnClickListener(this::onAnswerSelected);
        }
    }

    private void launchQuestionWithTimer() {
        if (timer != null) timer.cancel();
        timer = new CountDownTimer(QUESTION_DURATION_MS, 1_000) {
            @Override
            public void onTick(long m) {
                tvTimer.setText((m / 1_000) + "s");
            }

            @Override
            public void onFinish() {
                tvTimer.setText("0s");
                // mark wrong and auto-advance
                markWrongAndContinue();
            }
        }.start();

        nextQuestionCore();
    }

    /**
     * Generates the next question but does NOT advance currentIndex here.
     */
    private void nextQuestionCore() {
        if (currentIndex >= TOTAL_QUESTIONS) {
            finishQuiz();
            return;
        }

        int n1 = random.nextInt(20) + 1;
        int n2 = random.nextInt(10) + 1;
        correctAnswer = n1 * n2;

        tvNumber1.setText(String.valueOf(n1));
        tvNumber2.setText(String.valueOf(n2));
        tvAnswer.setText("?");

        // 1) collect 1 correct + 3 unique wrong answers
        List<Integer> opts = new ArrayList<>();
        opts.add(correctAnswer);
        while (opts.size() < 4) {
            // pick a random wrong answer somewhere in [1 .. 2×correct]
            int wrong = random.nextInt(correctAnswer * 2) + 1;
            if (wrong != correctAnswer && !opts.contains(wrong)) {
                opts.add(wrong);
            }
        }

// 2) shuffle so correctAnswer lands randomly
        Collections.shuffle(opts);

// 3) assign to buttons
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(String.valueOf(opts.get(i)));
            answerButtons[i].setEnabled(true);
        }


    }

    private void onAnswerSelected(View view) {
        if (timer != null) timer.cancel();

        Button b = (Button) view;
        int chosen = Integer.parseInt(b.getText().toString());
        boolean wasCorrect = (chosen == correctAnswer);

        if (wasCorrect) score++;

        tvAnswer.setText(String.valueOf(correctAnswer));
        Toast.makeText(
                this,
                wasCorrect ? "Correct!" : "Wrong!",
                Toast.LENGTH_SHORT
        ).show();

        for (Button btn : answerButtons) btn.setEnabled(false);

        // **Only now** bump to the next question index:
        currentIndex++;

        // Auto-advance if we're mid-quiz:
        if (quizStarted) {
            launchQuestionWithTimer();
        }
    }

    private void markWrongAndContinue() {
        for (Button b : answerButtons) b.setEnabled(false);
        if (quizStarted) {
            currentIndex++;
            launchQuestionWithTimer();
        }
    }

    private void finishQuiz() {
        if (timer != null) timer.cancel();
        for (Button b : answerButtons) b.setEnabled(false);
        btNext.setEnabled(false);
        btResult.setEnabled(true);
    }

    private void showResult() {
        // always out of 10
        int percent = (score * 100) / TOTAL_QUESTIONS;
        String msg = (percent >= 60 ? "Pass: " : "Fail: ") + percent + "%";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void resetQuizState() {
        if (timer != null) timer.cancel();
        tvTimer.setText("15s");
        tvNumber1.setText("1");
        tvNumber2.setText("0");
        tvAnswer.setText("?");
        for (Button b : answerButtons) {
            b.setText("0");
            b.setEnabled(false);
        }
        btStart.setEnabled(true);
        btReset.setEnabled(false);
        btNext.setEnabled(true);
        btResult.setEnabled(false);
    }
}