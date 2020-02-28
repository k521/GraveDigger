package com.jlkh.gravedigger;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private static final String TAG = "GameAdapter";
    private List<GameCell> mData;
    private Context mContext;

    private SparseIntArray mColSparse;
    private SparseIntArray mRowSparse;

    private IRVOnItemListener<GameCell> iRVOnItemListener;

    public void setIRVOnItemListener(IRVOnItemListener<GameCell> iRVOnItemListener) {
        this.iRVOnItemListener = iRVOnItemListener;
    }

    public GameAdapter(List<GameCell> soundBeans, SparseIntArray colSparse, SparseIntArray rowSparse) {
        this.mData = soundBeans;
        //https://developer.android.com/reference/android/util/SparseArray
        this.mColSparse = colSparse;
        this.mRowSparse = rowSparse;
//        printIntArr(colSparse);
//        printIntArr(rowSparse);
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        return new GameViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_game, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameCell gameCell = mData.get(position);
        if (gameCell != null) {
//            Log.i(TAG, "getView: gameCell==" + gameCell.toString());
            if (gameCell.isClicked()) {
                if (gameCell.getType()==Constants.MINE_TYPE){
                    holder.idIvBg.setImageResource(gameCell.getBgImgId());
                    holder.idTvMineVal.setVisibility(View.GONE);
                    holder.idIvBg.setVisibility(View.VISIBLE);
                }else{
                    holder.idIvBg.setVisibility(View.INVISIBLE);
                    holder.idTvMineVal.setText(String.valueOf(gameCell.getCellVal()));
                    holder.idTvMineVal.setVisibility(View.VISIBLE);
                }
            } else {
                holder.idIvBg.setImageResource(gameCell.getBgImgId());
                holder.idTvMineVal.setVisibility(View.GONE);
                holder.idIvBg.setVisibility(View.VISIBLE);
            }
            holder.idClRoot.setOnClickListener(v -> {
//                if (iRVOnItemListener != null) {
//                    iRVOnItemListener.onItemClick(gameCell, position);
//                }
                int mineCount = mColSparse.get(Constants.MINE_TYPE_COUNT);
                int mineCountTmp = mRowSparse.get(Constants.MINE_TYPE_COUNT);
                if (mineCount != mineCountTmp){
                    Log.e(TAG, "onBindViewHolder: Calculation error, contact your administrator!!");
                    return;
                }
                if (!gameCell.isClicked()){
                    for (MediaPlayer mediaPlayer : MyApp.getInstance().getMediaPlayerList()) {
                        if (mediaPlayer != null) {
                            if (mediaPlayer.isPlaying()){
                                mediaPlayer.stop();
                                mediaPlayer.release();
                            }
                        }
                    }
                    MyApp.getInstance().getMediaPlayerList().clear();
                    Point indexPoint = gameCell.getIndex();
                    Log.i(TAG, "onBindViewHolder: indexPoint.x===" + indexPoint.x + "====indexPoint.y==" + indexPoint.y);
                    if (gameCell.getType()==Constants.MINE_TYPE){
                        MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.raw_zombie);
                        mediaPlayer.setOnCompletionListener(mp -> {
                            mp.stop();
                            mp.reset();
                        });
                        mediaPlayer.start();
                        MyApp.getInstance().getMediaPlayerList().add(mediaPlayer);
                        mineCount-=1;
                        calculateMineVal(mColSparse, mineCount, indexPoint.x);
                        calculateMineVal(mRowSparse, mineCount, indexPoint.y);
                        holder.idIvBg.setImageResource(R.mipmap.ic_zombie);
                        gameCell.setBgImgId(R.mipmap.ic_zombie);
                        holder.idTvMineVal.setVisibility(View.GONE);
                        holder.idIvBg.setVisibility(View.VISIBLE);
                        for (int i=0; i < mData.size(); i++) {
                            GameCell gameCellTmp = mData.get(i);
                            Point pointTmp = gameCellTmp.getIndex();
                            gameCellTmp.setCellVal(mColSparse.get(pointTmp.x) + mRowSparse.get(pointTmp.y));
                            mData.set(i, gameCellTmp);
                        }
                        if (mineCount==0){
                            Log.i(TAG, "onBindViewHolder: user won");
                            mColSparse.clear();
                            mRowSparse.clear();
                            if (iRVOnItemListener != null) {
                                iRVOnItemListener.onGameWinCallBack(gameCell, position);
                            }
                            return;
                        }
                    }else{
//                        printIntArr(mColSparse);
//                        printIntArr(mRowSparse);
                        Log.i(TAG, indexPoint.x + "==x==onBindViewHolder: " + mColSparse.get(indexPoint.x));
                        Log.i(TAG, indexPoint.y + "==y==onBindViewHolder: " + mRowSparse.get(indexPoint.y));
                        gameCell.setCellVal(mColSparse.get(indexPoint.x) + mRowSparse.get(indexPoint.y));
                        holder.idIvBg.setVisibility(View.INVISIBLE);
                        holder.idTvMineVal.setText(String.valueOf(gameCell.getCellVal()));
                        holder.idTvMineVal.setVisibility(View.VISIBLE);
                    }
                    gameCell.setClicked(true);
                    mData.set(position, gameCell);
                    if (iRVOnItemListener != null) {
                        iRVOnItemListener.onItemClick(gameCell, position);
                    }
                }

            });
        }
    }

    private void calculateMineVal(SparseIntArray sparse, int mineCount, int key) {
//        printIntArr(sparse);
        int mineVal = sparse.get(key);
        mineVal-=1;
        mineVal = mineVal<0?0:mineVal;
        Log.i(TAG, key + "===calculateMineVal: mineVal==" + mineVal);
        sparse.put(key, mineVal);
        sparse.put(Constants.MINE_TYPE_COUNT, mineCount);
    }


    private void printIntArr(SparseIntArray sparse) {
        for (int i = 0; i < sparse.size(); i++) {
            int keyTmp = sparse.keyAt(i);
            int valTmp = sparse.get(keyTmp);
            Log.e(TAG, "key = " + keyTmp);
            Log.e(TAG, "valTmp = " + valTmp);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView idIvBg;
        TextView idTvMineVal;
        ConstraintLayout idClRoot;

        public GameViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            idIvBg = (ImageView) itemView.findViewById(R.id.id_iv_bg);
            idTvMineVal = (TextView) itemView.findViewById(R.id.id_tv_mine_val);
            idClRoot = (ConstraintLayout) itemView.findViewById(R.id.id_cl_root);
        }
    }


}