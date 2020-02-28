package com.jlkh.gravedigger;

/**
 * RecycleView item click events
 */

public interface IRVOnItemListener<T> {

    /***
     * Returns the index and the object of click
     *
     * @param t
     * @param position
     */
    void onItemClick(T t, int position);


    void onGameWinCallBack(T t, int position);


    void onGameOverCallBack(T t, int position);

}
