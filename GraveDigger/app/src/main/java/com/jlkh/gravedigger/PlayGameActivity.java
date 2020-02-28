package com.jlkh.gravedigger;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PlayGameActivity";
    protected TextView idTvBack;


    private Context mContext;
    private PlayGameActivity mActivity;

    protected TextView idTvMineNum;
    protected TextView idTvScanStep;
    protected RecyclerView idRvCells;

    private GameAdapter mGameAdapter;
    private Random mRandom;

    private int mMineCount;
    private int mMineCountVar;
    private int mScanSteps;

    private Dialog mGameOverDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.mActivity = PlayGameActivity.this;
        this.mContext = PlayGameActivity.this;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setContentView(R.layout.activity_play_game);


        idTvMineNum = (TextView) findViewById(R.id.id_tv_mine_num);
        idTvScanStep = (TextView) findViewById(R.id.id_tv_scan_step);
        idRvCells = (RecyclerView) findViewById(R.id.id_rv_cells);

        String[] boardArray = getResources().getStringArray(R.array.text_board_size);
        String[] mineArray = getResources().getStringArray(R.array.text_mine_size);
        String boardSize = (String) SPTool.getInstanse().getParam(Constants.BOARD_SIZE, boardArray[0]);
        String[] boardArr = boardSize.split("\\*");
        int rowLen = Integer.parseInt(boardArr[0]); // Y coordinate
        int colLen = Integer.parseInt(boardArr[1]); //  X coordinate
        mMineCount = Integer.parseInt((String) SPTool.getInstanse().getParam(Constants.MINE_SIZE, mineArray[0]));

        mRandom = new Random();
        List<GameCell> gameCellList = new ArrayList<>();
        SparseIntArray colSparse = new SparseIntArray();
        SparseIntArray rowSparse = new SparseIntArray();
        SparseArrayCompat<List<Integer>> colSparseList = new SparseArrayCompat();
        SparseArrayCompat<List<Integer>> rowSparseList = new SparseArrayCompat();
        mMineCountVar = 0;
        mScanSteps = 0;
        idTvMineNum.setText("Found " + mMineCountVar + " of " + mMineCount + " zombies.");
        idTvScanStep.setText("# Scans used: " + mScanSteps);
        Set ranSet = new LinkedHashSet(mMineCount);
        Random random = new Random();
        while (ranSet.size() < mMineCount) {
            int index = random.nextInt(colLen * rowLen);
            ranSet.add(index);
            int x = index % colLen;
            int y = index / colLen;
            getIndexList(colSparseList, index, x);
            getIndexList(rowSparseList, index, y);
        }
        colSparse.put(Constants.MINE_TYPE_COUNT, mMineCount);
        rowSparse.put(Constants.MINE_TYPE_COUNT, mMineCount);
        Log.i(TAG, "onCreate: ranSet==" + ranSet.toString());
        Log.i(TAG, "onCreate: colSparseList==" + colSparseList.toString());
        Log.i(TAG, "onCreate: rowSparseList==" + rowSparseList.toString());

        for (int i = 0; i < rowLen; i++) {
            int x = i * colLen;
            for (int j = 0; j < colLen; j++) {
                int index = x + j;
//                Log.i(TAG, "onCreate: j==" + j + "====" + colSparse.indexOfKey(j));
                int colVal = colSparseList.get(j) == null ? 0 : colSparseList.get(j).size();
//                Log.i(TAG, colSparse.indexOfValue(colVal) + "==onCreate: j==" + j + "====" + colSparse.indexOfKey(j));
                colSparse.put(j, colVal);
//                if (colSparse.indexOfKey(j)<0 && colSparse.indexOfValue(colVal)<0){
//                    colSparse.put(j,  colVal);
//                }
                GameCell gameCell = new GameCell();
                gameCell.setCellVal(0);
                gameCell.setType(ranSet.contains(index) ? Constants.MINE_TYPE : 0);
                gameCell.setClicked(false);
                gameCell.setIndex(new Point(j, i));
                gameCell.setBgImgId(R.mipmap.ic_tombstone);
                gameCellList.add(gameCell);
            }
            int rowVal = rowSparseList.get(i) == null ? 0 : rowSparseList.get(i).size();
//            Log.i(TAG, rowSparse.indexOfValue(rowVal) + "==onCreate: i==" + i + "====" + rowSparse.indexOfKey(i));
            rowSparse.put(i, rowVal);
//            if (rowSparse.indexOfKey(i)<0  && rowSparse.indexOfValue(rowVal)<0){
//
//            }
        }
//        printIntArr(colSparse);
//        printIntArr(rowSparse);
        mGameAdapter = new GameAdapter(gameCellList, colSparse, rowSparse);
        mGameAdapter.setIRVOnItemListener(new IRVOnItemListener<GameCell>() {

            @Override
            public void onItemClick(GameCell gameCell, int position) {
                if (gameCell.getType() == Constants.MINE_TYPE) {
                    mMineCountVar += 1;
                    idTvMineNum.setText("Found " + mMineCountVar + " of " + mMineCount + " zombies.");
                }else{
                    mScanSteps += 1;
                    idTvScanStep.setText("# Scans used: " + mScanSteps);
                }
                mGameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onGameWinCallBack(GameCell gameCell, int position) {
                if (gameCell.getType() == Constants.MINE_TYPE) {
                    mMineCountVar += 1;
                    idTvMineNum.setText("Found " + mMineCountVar + " of " + mMineCount + " zombies.");
                }else{
                    mScanSteps += 1;
                    idTvScanStep.setText("# Scans used: " + mScanSteps);
                }
                mGameAdapter.notifyDataSetChanged();
                showGameOver();

            }

            @Override
            public void onGameOverCallBack(GameCell gameCell, int position) {

            }
        });
        idRvCells.setLayoutManager(new GridLayoutManager(mContext, colLen));
        idRvCells.setAdapter(mGameAdapter);
        initView();
    }

    private void showGameOver() {
        mGameOverDialog = new Dialog(mContext, R.style.ShowImageDialog);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_game_over, null);
        ImageView idIvOk = (ImageView) dialogView.findViewById(R.id.id_iv_ok);
        idIvOk.setOnClickListener(v -> {
            for (MediaPlayer mediaPlayer : MyApp.getInstance().getMediaPlayerList()) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                }
            }
            MyApp.getInstance().getMediaPlayerList().clear();
            mGameOverDialog.dismiss();
            mActivity.finish();
        });
        mGameOverDialog.setContentView(dialogView);
        mGameOverDialog.setCancelable(false);
        mGameOverDialog.setCanceledOnTouchOutside(false); // Sets whether this dialog is
        mGameOverDialog.show();
    }


    private void printIntArr(SparseIntArray sparse) {
        for (int i = 0; i < sparse.size(); i++) {
            int keyTmp = sparse.keyAt(i);
            int valTmp = sparse.get(keyTmp);
            Log.e(TAG, "key = " + keyTmp);
            Log.e(TAG, "valTmp = " + valTmp);
        }
    }


    private void getIndexList(SparseArrayCompat<List<Integer>> colSparseList, int index, int x) {
        List<Integer> xList = colSparseList.get(x);
        xList = xList == null ? new ArrayList<>() : xList;
        if (!xList.contains(index)) xList.add(index);
        colSparseList.put(x, xList);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.id_tv_back) {
            mActivity.finish();
        }
    }

    private void initView() {
        idTvMineNum = (TextView) findViewById(R.id.id_tv_mine_num);
        idTvBack = (TextView) findViewById(R.id.id_tv_back);
        idTvBack.setOnClickListener(PlayGameActivity.this);
        idTvScanStep = (TextView) findViewById(R.id.id_tv_scan_step);
        idRvCells = (RecyclerView) findViewById(R.id.id_rv_cells);
    }
}
