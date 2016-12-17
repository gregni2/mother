package com.mother2.stocks.Analyzer.datacorolators;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregorynield on 11/27/16.
 */
public class Lead {
    public String mStockName;
    public String mDateUp;
    public int mDaysUp;
    public List<Follower> mFollowerList = new ArrayList<>();

    public Lead(String mStockName, String mDateUp, int mDaysUp) {
        this.mStockName = mStockName;
        this.mDateUp = mDateUp;
        this.mDaysUp = mDaysUp;
    }
}
