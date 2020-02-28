package com.jlkh.gravedigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView idTvDeveloper;
    protected ImageView idIvSkip;
    protected ConstraintLayout idClRoot;

    private Animation mAnimWelcomeIn;
    private Animation mAnimWelcomeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setContentView(R.layout.activity_welcome);
        initView();
        String[] boardArray = getResources().getStringArray(R.array.text_board_size);
        String[] mineArray = getResources().getStringArray(R.array.text_mine_size);
        SPTool.getInstanse().setParam(Constants.BOARD_SIZE, boardArray[0]);
        SPTool.getInstanse().setParam(Constants.MINE_SIZE, mineArray[0]);
        idTvDeveloper.setText("Welcome to GraveDigger \n by Jonathan Li & Kent Huang");
        // animation adapted from https://blog.csdn.net/u012864297/article/details/64481479
        mAnimWelcomeIn = AnimationUtils.loadAnimation(this, R.anim.anim_welcome_in);
        mAnimWelcomeOut = AnimationUtils.loadAnimation(this, R.anim.anim_welcome_out);
        idClRoot.startAnimation(mAnimWelcomeIn);
        mAnimWelcomeIn.setFillEnabled(true);
        mAnimWelcomeIn.setFillAfter(true);
        mAnimWelcomeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                idClRoot.startAnimation(mAnimWelcomeOut);
                mAnimWelcomeOut.setFillAfter(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mAnimWelcomeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void initView() {
        idTvDeveloper = (TextView) findViewById(R.id.id_tv_developer);
        idIvSkip = (ImageView) findViewById(R.id.id_iv_skip);
        idIvSkip.setOnClickListener(WelcomeActivity.this);
        idClRoot = (ConstraintLayout) findViewById(R.id.id_cl_root);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.id_iv_skip) {
            toMain();
        }
    }

    private void toMain() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
