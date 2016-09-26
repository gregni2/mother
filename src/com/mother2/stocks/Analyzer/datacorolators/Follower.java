package com.mother2.stocks.Analyzer.datacorolators;

import java.util.Date;

/**
 * Created by gregorynield on 5/15/16.
 */
public class Follower {
    private String mStockName;
    private Date mDateUp;
    private String mDaysUp;

    public Follower(String mStockName, Date mDateUp, String mDaysUp) {
        this.mStockName = mStockName;
        this.mDateUp = mDateUp;
        this.mDaysUp = mDaysUp;
    }

}
