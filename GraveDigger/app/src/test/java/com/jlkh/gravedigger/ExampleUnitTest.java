package com.jlkh.gravedigger;

import android.util.SparseIntArray;

import androidx.collection.SparseArrayCompat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        String boardSize = "6*7";
        String[] boardArr = boardSize.split("\\*");
        System.out.println(boardArr[0]);
        System.out.println(boardArr[1]);

        List<GameCell> gameCellList = new ArrayList<>();
        SparseIntArray colSparse = new SparseIntArray();
        SparseIntArray rowSparse = new SparseIntArray();
        SparseArrayCompat<List<Integer>> colSparseList  = new SparseArrayCompat();
        SparseArrayCompat<List<Integer>> rowSparseList  = new SparseArrayCompat();
        int colLen = 10; //  X coordinate
        int rowLen = 5; // Y coordinate
        int mineCount = 8;
        Set ranSet = new LinkedHashSet(mineCount);
        Random random = new Random();
        while (ranSet.size() < mineCount) {
            int index = random.nextInt(colLen * rowLen);
            ranSet.add(index);
            int x = index % colLen;
            int y = index / colLen;
            getIndexList(colSparseList, index, x);
            getIndexList(rowSparseList, index, y);
        }
        System.out.println(ranSet.toString());
        System.out.println("colSparseList=="+colSparseList.toString());
        System.out.println("rowSparseList=="+rowSparseList.toString());
        for (int i = 0; i < rowLen; i++) {
            int x = i * colLen;
            for (int j = 0; j < colLen; j++) {
                int index = x + j;
//                System.out.println("x===" + j + "===y===" + i);
                System.out.println(colSparse.indexOfKey(j));
//                System.out.println("index===" + index + "===isContains===" + ranSet.contains(index));
                if (colSparse.indexOfKey(j)==0){
                    colSparse.put(j,  colSparseList.get(j) == null ? 0 : colSparseList.get(j).size());
                    System.out.println(j + "==j==" + (colSparseList.get(j) == null ? 0 : colSparseList.get(j).size()));
                }
            }
            if (rowSparse.indexOfKey(i)==0){
                rowSparse.put(i,  rowSparseList.get(i) == null ? 0 : rowSparseList.get(i).size());
                System.out.println(i + "==i==" + (rowSparseList.get(i) == null ? 0 : rowSparseList.get(i).size()));
            }

        }
        printIntArray(colSparse);
        printIntArray(rowSparse);
    }

    private void printIntArray(SparseIntArray rowSparse) {
        for (int i = 0; i < rowSparse.size(); i++) {
            int keyTmp = rowSparse.keyAt(i);
            int valTmp = rowSparse.get(keyTmp);
            System.out.println("key = " + keyTmp);
            System.out.println("valTmp = " + valTmp);
        }
    }

    private void getIndexList(SparseArrayCompat<List<Integer>> colSparse, int index, int x) {
        List<Integer> xList = colSparse.get(x);
        xList = xList == null ? new ArrayList<>() : xList;
        xList.add(index);
        colSparse.put(x, xList);
    }

    /**
     * Access to a collection of random Numbers, is the ray of random coordinate set
     *
     * @param colLen
     * @param rowLen
     * @param mineCount
     * @return
     */
    private Set getRandomSet(int colLen, int rowLen, int mineCount) {
        Set ranSet = new LinkedHashSet(mineCount);
        Random random = new Random();
        while (ranSet.size() < mineCount) {
            int index = random.nextInt(colLen * rowLen);
            ranSet.add(index);
        }
        return ranSet;
    }
}