package com.jlkh.gravedigger;

import android.graphics.Point;

public class GameCell {

    /**
     *
     * -1 mine;  0  other
     */
    private int type;

    /**
     * Whether has been clicked .  true  isClicked;  false not click
     *
     */
    private boolean isClicked;

    /**
     *  Cell shows the background of the id
     *
     */
    private int bgImgId;

    /**
     * The rest of the cells showed demining number
     *
     */
    private int cellVal;


    /**
     * The index of the cell
     *
     */
    private Point index;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public int getBgImgId() {
        return bgImgId;
    }

    public void setBgImgId(int bgImgId) {
        this.bgImgId = bgImgId;
    }

    public int getCellVal() {
        return cellVal;
    }

    public void setCellVal(int cellVal) {
        this.cellVal = cellVal;
    }

    public Point getIndex() {
        return index;
    }

    public void setIndex(Point index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "GameCell{" +
                "type=" + type +
                ", isClicked=" + isClicked +
                ", bgImgId=" + bgImgId +
                ", cellVal='" + cellVal + '\'' +
                ", index=" + index +
                '}';
    }
}
