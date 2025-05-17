package com.b.aman.atable;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        // ❶ bind all your views before you use them
        bindViews();

        // ❷ load the chalk font
        chalkFace = Typeface.createFromAsset(getAssets(), "fonts/chalk.ttf");

        // ❸ toolbar, fonts, listeners & initial state
        setupToolbar();
        applyChalkFont();
        setupListeners();
        resetQuizState();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @SuppressLint("ResourceType")
    private void applyChalkFont() {
        // question & answer display
        tvNumber1.setTypeface(chalkFace);
        tvNumber2.setTypeface(chalkFace);
        tvAnswer.setTypeface(chalkFace);
        tvTimer.setTypeface(chalkFace);
        tvAnswer.setTypeface(chalkFace);

        // all four option buttons
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

        int offset = random.nextInt(4);
        int[] opts = {correctAnswer, correctAnswer + 2, correctAnswer + 4, correctAnswer + 6};
        for (int i = 0; i < 4; i++) {
            answerButtons[i]
                    .setText(String.valueOf(opts[(i + offset) % 4]));
            answerButtons[i].setEnabled(true);
        }

        // **DO NOT** increment currentIndex here any more.
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
        tvNumber2.setText("1");
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