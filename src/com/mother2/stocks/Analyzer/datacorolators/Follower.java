package com.mother2.stocks.Analyzer.datacorolators;

import java.util.Date;

/**
 * Created by gregorynield on 5/15/16.
 */
public class Follower {
    public String mStockName;
    public String mDateUp;
    public int mDaysUp;

    public Follower(String mStockName, String mDateUp, int mDaysUp) {
        this.mStockName = mStockName;
        this.mDateUp = mDateUp;
        this.mDaysUp = mDaysUp;
    }

}
