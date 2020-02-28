package com.jlkh.gravedigger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class GameOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GameOptionsActivity";

    protected ImageView idIvSetting;
    protected ImageView idIvClose;
    protected TextView idTvTitle;
    protected ImageView idIvContent;
    protected TextView idTvBoardSizeDesc;
    protected Spinner idSBoardSize;
    protected TextView idTvMineSizeDesc;
    protected Spinner idSMineSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setContentView(R.layout.activity_game_options);
        initView();
        String[] boardArray = getResources().getStringArray(R.array.text_board_size);
        String[] mineArray = getResources().getStringArray(R.array.text_mine_size);
        String boardSelected = (String) SPTool.getInstanse().getParam(Constants.BOARD_SIZE, boardArray[0]);
        String mineSelected = (String) SPTool.getInstanse().getParam(Constants.MINE_SIZE, mineArray[0]);
        idSBoardSize.setSelection(Arrays.asList(boardArray).indexOf(boardSelected));
        idSMineSize.setSelection(Arrays.asList(mineArray).indexOf(mineSelected));
        idSBoardSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemSelected: idSBoardSize == " + parent.getSelectedItem());
                SPTool.getInstanse().setParam(Constants.BOARD_SIZE, parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        idSMineSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemSelected: idSMineSize == " + parent.getSelectedItem());
                SPTool.getInstanse().setParam(Constants.MINE_SIZE, parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.id_iv_close) {
            this.finish();
        }
    }

    private void initView() {
        idIvSetting = (ImageView) findViewById(R.id.id_iv_setting);
        idIvClose = (ImageView) findViewById(R.id.id_iv_close);
        idIvClose.setOnClickListener(GameOptionsActivity.this);
        idTvTitle = (TextView) findViewById(R.id.id_tv_title);
        idIvContent = (ImageView) findViewById(R.id.id_iv_content);
        idTvBoardSizeDesc = (TextView) findViewById(R.id.id_tv_board_size_desc);
        idSBoardSize = (Spinner) findViewById(R.id.id_s_board_size);
        idTvMineSizeDesc = (TextView) findViewById(R.id.id_tv_mine_size_desc);
        idSMineSize = (Spinner) findViewById(R.id.id_s_mine_size);
    }
}
