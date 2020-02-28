package com.jlkh.gravedigger;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameHelpActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView idTvTitle;
    protected ImageView idIvHelp;
    protected ImageView idIvClose;
    protected ImageView idIvContent;
    protected TextView idTvHelpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setContentView(R.layout.activity_game_help);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.id_iv_close) {
            finish();
        }
    }

    private void initView() {
        idTvTitle = (TextView) findViewById(R.id.id_tv_title);
        idIvHelp = (ImageView) findViewById(R.id.id_iv_help);
        idIvClose = (ImageView) findViewById(R.id.id_iv_close);
        idIvClose.setOnClickListener(GameHelpActivity.this);
        idIvContent = (ImageView) findViewById(R.id.id_iv_content);
        idTvHelpContent = (TextView) findViewById(R.id.id_tv_help_content);
    }
}
