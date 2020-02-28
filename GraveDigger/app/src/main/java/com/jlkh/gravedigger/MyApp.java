package com.jlkh.gravedigger;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;


/**
 */

public class MyApp extends Application {

    private Context mContext;

    private static MyApp mMyApp;

    private List<MediaPlayer> mMediaPlayerList;

    public void addMediaPlayer(MediaPlayer mediaPlayer){
        if (!mMediaPlayerList.contains(mediaPlayer)){
            mMediaPlayerList.add(mediaPlayer);
        }
    }

    public List<MediaPlayer> getMediaPlayerList() {
        return mMediaPlayerList;
    }

    public void clear( ){
        if (mMediaPlayerList.size()>0) mMediaPlayerList.clear();
    }

    public static MyApp getInstance() {
        return mMyApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApp = this;
        mContext = getApplicationContext();
        SPTool.getInstanse().init(this);
        mMediaPlayerList = new ArrayList<>();

    }

    public Context getContext() {
        return mContext;
    }

}
