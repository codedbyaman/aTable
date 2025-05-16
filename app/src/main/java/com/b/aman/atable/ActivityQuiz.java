package com.b.aman.atable;

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
    private static final long QUESTION_DURATION_MS = 15_000; // 15 seconds per question

    private TextView tvTimer, tvNumber1, tvNumber2, tvAnswer;
    private Button[] answerButtons;
    private Button btStart, btReset, btNext, btResult;

    private CountDownTimer timer;
    private int quizIndex, previewIndex, score, correctAnswer;
    private boolean quizStarted = false;
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupToolbar();
        bindViews();
        setupListeners();
        resetAllStates();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        // START: begin full quiz
        btStart.setOnClickListener(v -> {
            quizStarted = true;
            quizIndex = 0;
            score = 0;
            btStart.setEnabled(false);
            btReset.setEnabled(true);
            btNext.setEnabled(true);
            btResult.setEnabled(false);
            launchTimedQuestion();
        });

        // RESET: wipe everything
        btReset.setOnClickListener(v -> {
            quizStarted = false;
            resetAllStates();
        });

        // NEXT: preview when not started, otherwise advance with timer
        btNext.setOnClickListener(v -> {
            if (!quizStarted) {
                previewIndex++;
                showQuestion(false);
            } else {
                launchTimedQuestion();
            }
        });

        // RESULT: only after quiz finishes
        btResult.setOnClickListener(v -> showResult());

        // each answer button funnels through handleAnswer
        for (Button b : answerButtons) {
            b.setOnClickListener(v -> handleAnswer(v, /*countForQuiz=*/ quizStarted));
        }
    }

    /**
     * Common entry both for TIMER‐finish and manual button clicks.
     *
     * @param view         the clicked button, or null on timeout
     * @param countForQuiz true when this should count toward the 10-question quiz
     */
    private void handleAnswer(View view, boolean countForQuiz) {
        // cancel any ongoing timer
        if (timer != null) timer.cancel();

        // if view != null, it was a button click; extract choice
        if (view instanceof Button) {
            int chosen = Integer.parseInt(((Button) view).getText().toString());
            tvAnswer.setText(String.valueOf(correctAnswer));
            if (chosen == correctAnswer && countForQuiz) {
                score++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // timeout path
            tvAnswer.setText(String.valueOf(correctAnswer));
            Toast.makeText(this, "Time's up!", Toast.LENGTH_SHORT).show();
        }

        // disable all answer buttons
        for (Button b : answerButtons) b.setEnabled(false);

        // if we’re in the real quiz flow, advance or finish
        if (quizStarted && countForQuiz) {
            if (quizIndex < TOTAL_QUESTIONS) {
                launchTimedQuestion();
            } else {
                finishQuiz();
            }
        }
    }

    /**
     * Starts a fresh 15s timer and immediately shows the next question.
     */
    private void launchTimedQuestion() {
        if (timer != null) timer.cancel();

        // start countdown
        timer = new CountDownTimer(QUESTION_DURATION_MS, 1000) {
            @Override
            public void onTick(long m) {
                tvTimer.setText((m / 1000) + "s");
            }

            @Override
            public void onFinish() {
                tvTimer.setText("0s");
                handleAnswer(/*view=*/ null, /*countForQuiz=*/ true);
            }
        }.start();

        // bump index and display
        showQuestion(/*countForQuiz=*/ true);
    }

    /**
     * Displays the next question.
     *
     * @param countForQuiz if true, consumes one of the 10 quiz slots; otherwise just previews
     */
    private void showQuestion(boolean countForQuiz) {
        // increment index only when we’re counting
        if (countForQuiz) {
            quizIndex++;
        }
        // bail out if done
        if (quizIndex > TOTAL_QUESTIONS) {
            finishQuiz();
            return;
        }

        // generate new operands
        int n1 = random.nextInt(20) + 1;
        int n2 = random.nextInt(10) + 1;
        correctAnswer = n1 * n2;

        tvNumber1.setText(String.valueOf(n1));
        tvNumber2.setText(String.valueOf(n2));
        tvAnswer.setText("?");

        // shuffle options
        int offset = random.nextInt(4);
        int[] opts = new int[]{correctAnswer,
                correctAnswer + 2,
                correctAnswer + 4,
                correctAnswer + 6};
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(String.valueOf(opts[(i + offset) % 4]));
            answerButtons[i].setEnabled(true);
        }
    }

    /**
     * Called when the 10-question quiz is complete.
     */
    private void finishQuiz() {
        if (timer != null) timer.cancel();
        for (Button b : answerButtons) b.setEnabled(false);
        btNext.setEnabled(false);
        btResult.setEnabled(true);
    }

    /**
     * Shows Pass/Fail based on a 10-question scale, regardless of how many answered.
     */
    private void showResult() {
        int percent = (score * 100) / TOTAL_QUESTIONS;
        String msg = (percent >= 60 ? "Pass: " : "Fail: ") + percent + "%";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Resets all UI and counters to the pre-quiz state.
     */
    private void resetAllStates() {
        if (timer != null) timer.cancel();
        tvTimer.setText("15s");
        tvNumber1.setText("0");
        tvNumber2.setText("0");
        tvAnswer.setText("");
        quizIndex = 0;
        previewIndex = 0;
        score = 0;
        quizStarted = false;

        for (Button b : answerButtons) {
            b.setText("-");
            b.setEnabled(false);
        }

        btStart.setEnabled(true);
        btReset.setEnabled(false);
        btNext.setEnabled(true);
        btResult.setEnabled(false);
    }
}