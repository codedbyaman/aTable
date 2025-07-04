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
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ActivityQuiz extends AppCompatActivity {
    private static final int TOTAL_QUESTIONS = 10;
    private static final long QUESTION_DURATION_MS = 15_000L;

    private final Random random = new Random();
    private Typeface chalkFace;

    private ImageView ivTimerIcon;
    private TextView tvTimer, tvNumber1, tvNumber2, tvAnswer;
    private Button[] answerButtons;
    private Button btStart, btReset, btNext, btResult;

    private MaterialCardView cardResult;
    private TextView tvResultDisplay;

    private CountDownTimer timer;
    private int currentIndex, score, correctAnswer;
    private boolean quizStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // 1️⃣  Load custom “chalk” font
        chalkFace = Typeface.createFromAsset(getAssets(), "fonts/chalk.ttf");

        // 2️⃣  Toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 3️⃣  Find all views
        ivTimerIcon = findViewById(R.id.iv_timer_icon);
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

        cardResult = findViewById(R.id.card_result);
        tvResultDisplay = findViewById(R.id.tv_result_display);

        // 4️⃣  Apply “chalk” font
        tvTimer.setTypeface(chalkFace);
        tvNumber1.setTypeface(chalkFace);
        tvNumber2.setTypeface(chalkFace);
        tvAnswer.setTypeface(chalkFace);
        for (Button b : answerButtons) b.setTypeface(chalkFace);

        // 5️⃣  Set static hourglass initially
        ivTimerIcon.setImageResource(R.drawable.hourglass_iconpng);

        // 6️⃣  Wire up all listeners
        setupListeners();

        // 7️⃣  Reset UI to start state
        resetQuizState();
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

            // swap to animated GIF
            Glide.with(this)
                    .asGif()
                    .load(R.drawable.hourglass_icon)        // GIF in drawable
                    .into(ivTimerIcon);

            launchQuestionWithTimer();
        });

        btReset.setOnClickListener(v -> {
            quizStarted = false;
            resetQuizState();
            // restore static PNG
            ivTimerIcon.setImageResource(R.drawable.hourglass_iconpng);
        });

        btNext.setOnClickListener(v -> {
            if (!quizStarted) {
                // “preview” next question
                nextQuestionCore();
            } else {
                launchQuestionWithTimer();
            }
        });

        btResult.setOnClickListener(v -> {
            showResult();
            // after showing result, allow new run
            quizStarted = false;
            btStart.setEnabled(true);
            btNext.setEnabled(true);
            btReset.setEnabled(true);
            btResult.setEnabled(false);
        });

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
                Toast.makeText(ActivityQuiz.this, "Time's up!", Toast.LENGTH_SHORT).show();
                markWrongAndContinue();
            }
        }.start();

        nextQuestionCore();
    }

    private void nextQuestionCore() {
        if (currentIndex >= TOTAL_QUESTIONS) {
            finishQuiz();
            return;
        }

        // generate operands
        int n1 = random.nextInt(20) + 1;
        int n2 = random.nextInt(10) + 1;
        correctAnswer = n1 * n2;

        tvNumber1.setText(String.valueOf(n1));
        tvNumber2.setText(String.valueOf(n2));
        tvAnswer.setText("?");

        // correct + 3 unique wrong answers
        List<Integer> opts = new ArrayList<>();
        opts.add(correctAnswer);
        while (opts.size() < 4) {
            int w = random.nextInt(correctAnswer * 2 + 1);
            if (w > 0 && w != correctAnswer && !opts.contains(w)) {
                opts.add(w);
            }
        }
        Collections.shuffle(opts);

        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(String.valueOf(opts.get(i)));
            answerButtons[i].setEnabled(true);
        }

        currentIndex++;
    }

    private void onAnswerSelected(View view) {
        if (timer != null) timer.cancel();

        Button b = (Button) view;
        int chosen = Integer.parseInt(b.getText().toString());
        boolean correct = (chosen == correctAnswer);

        if (correct) score++;
        tvAnswer.setText(String.valueOf(correctAnswer));
        Toast.makeText(this, correct ? "Correct!" : "Wrong!", Toast.LENGTH_SHORT).show();

        for (Button btn : answerButtons) btn.setEnabled(false);

        if (quizStarted) {
            launchQuestionWithTimer();
        }
    }

    private void markWrongAndContinue() {
        for (Button b : answerButtons) b.setEnabled(false);
        if (quizStarted) {
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
        int percent = (score * 100) / TOTAL_QUESTIONS;
        boolean passed = percent >= 60;
        String text = percent + "% — " + (passed ? "Pass" : "Fail");

        // reveal and style the result card
        cardResult.setVisibility(View.VISIBLE);
        tvResultDisplay.setText(text);
        int color = ContextCompat.getColor(this,
                passed ? R.color.pass_green : R.color.fail_red);
        tvResultDisplay.setTextColor(color);
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

        cardResult.setVisibility(View.GONE);
    }
}
