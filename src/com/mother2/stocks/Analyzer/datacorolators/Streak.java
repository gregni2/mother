package com.mother2.stocks.Analyzer.datacorolators;

import java.util.ArrayList;

/**
 * Created by gregorynield on 11/27/16.
 */
public class Streak {
    public String mStockName;
    public String mDateUp;
    public int mDaysUp;
    public double mCurrentStockPrice;

    public Streak(String mStockName, String mDateUp, int mDaysUp, double mCurrentStockPrice) {
        this.mStockName = mStockName;
        this.mDateUp = mDateUp;
        this.mDaysUp = mDaysUp;
        this.mCurrentStockPrice = mCurrentStockPrice;
    }
}
