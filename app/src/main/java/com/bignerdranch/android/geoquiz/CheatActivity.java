package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";  //
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";  //

    private static final String TAG = "CheatActivity";
    private static final String KEY_USER_HAS_CLICKED_SHOW_CHEAT_ANSWER = "cheatHappened";

    private boolean mAnswerIsTrue; //
    private TextView mAnswerTextView; //
    private Button mShowAnswer;  //
    private boolean mCheatHappened = false;
    private TextView mAPITextView; //


    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public void updateCheatAnswer(boolean answerIsTrue) {
        if (answerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_cheat);

        // retrieve the value from the extra and store it
        // getIntent() always returns the Intent that started the activity.
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);  //

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);  //

        mAPITextView = (TextView) findViewById(R.id.api_text_view);
        mAPITextView.setText("API level " + Build.VERSION.SDK_INT);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheatHappened = true;
                updateCheatAnswer(mAnswerIsTrue);  //

                // answer is shown so call method
                setAnswerShownResult(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });

        mCheatHappened = hasUserClickedShowAnswer(savedInstanceState);

        if (mCheatHappened) {

            updateCheatAnswer(mAnswerIsTrue);
        }

        setAnswerShownResult(mCheatHappened);

            // check for saved instance state for mCheatHappened
//        if (savedInstanceState != null) {
//            Log.i(TAG, "onCreate if statement: savedInstanceState does exist");
//            // it exists
//            mCheatHappened = savedInstanceState.getBoolean(KEY_USER_HAS_CLICKED_SHOW_CHEAT_ANSWER, false);
//            setAnswerShownResult(mCheatHappened);
//            if (mCheatHappened) {
//                updateCheatAnswer(mAnswerIsTrue);
//            }
//        }
    }

    private boolean hasUserClickedShowAnswer(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(KEY_USER_HAS_CLICKED_SHOW_CHEAT_ANSWER, false)) {
                return true;
            }
        }

        return false;
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(Activity.RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");

        if (mCheatHappened) {
            savedInstanceState.putBoolean(KEY_USER_HAS_CLICKED_SHOW_CHEAT_ANSWER, mCheatHappened);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onStart();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onStart();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStart();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onStart();
        Log.d(TAG, "onDestroy() called");
    }

}
