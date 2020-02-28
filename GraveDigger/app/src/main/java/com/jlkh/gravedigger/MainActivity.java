package com.jlkh.gravedigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView idTvPlayGame;
    protected TextView idTvGameOptions;
    protected TextView idTvGameHelp;
    protected TextView idTvExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setContentView(R.layout.activity_main);
        initView();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tv_play_game:
                startActivity(new Intent(this, PlayGameActivity.class));
                break;
            case R.id.id_tv_game_options:
                startActivity(new Intent(this, GameOptionsActivity.class));

                break;
            case R.id.id_tv_game_help:
                startActivity(new Intent(this, GameHelpActivity.class));
                break;
            case R.id.id_tv_exit:
                this.finish();
                break;
        }
    }

    private void initView() {
        idTvPlayGame = (TextView) findViewById(R.id.id_tv_play_game);
        idTvPlayGame.setOnClickListener(MainActivity.this);
        idTvGameOptions = (TextView) findViewById(R.id.id_tv_game_options);
        idTvGameOptions.setOnClickListener(MainActivity.this);
        idTvGameHelp = (TextView) findViewById(R.id.id_tv_game_help);
        idTvGameHelp.setOnClickListener(MainActivity.this);
        idTvExit = (TextView) findViewById(R.id.id_tv_exit);
        idTvExit.setOnClickListener(MainActivity.this);
    }
}
