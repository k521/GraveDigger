package com.jlkh.gravedigger;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import static com.jlkh.gravedigger.Constants.SP_CACHE_NAME;

/**
 */

public class SPTool {

    private final String TAG = "SPTool";

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    private SPTool() {
    }

    private static class Holder {
        private static SPTool singleton = new SPTool();
    }

    public static SPTool getInstanse() {
        return Holder.singleton;
    }

    public void init(Context context){
        if (context == null) {
            throw new IllegalArgumentException();
        }
        this.mContext = context;
        mSharedPreferences = context.getSharedPreferences(SP_CACHE_NAME,
                Context.MODE_PRIVATE);
    }


    /**
     *
     * @param key
     * @param object
     */
    public void setParam(String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        editor.commit();
    }


    /**
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getParam(String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        if ("String".equals(type)) {
            return mSharedPreferences.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return mSharedPreferences.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return mSharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return mSharedPreferences.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return mSharedPreferences.getLong(key, (Long) defaultObject);
        }
        return null;
    }


    /**
     */
    public void clear() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear().commit();
    }


    /**
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }

    /**
     *
     * @return
     */
    public Map<String, ?> getAllKey() {
        return mSharedPreferences.getAll();
    }

}
