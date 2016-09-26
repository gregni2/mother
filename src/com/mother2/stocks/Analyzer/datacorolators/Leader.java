package com.mother2.stocks.Analyzer.datacorolators;

import java.util.ArrayList;

/**
 * Created by gregorynield on 5/15/16.
 */
public class Leader {

    public String mStockName;
    public String mDateUp;
    public int mDaysUp;
    public double mCurrentStockPrice;
    public ArrayList<Follower> mFollowers;

    public Leader(String mStockName, String mDateUp, int mDaysUp, double currentStockPrice) {
        this.mStockName = mStockName;
        this.mDateUp = mDateUp;
        this.mDaysUp = mDaysUp;
        this.mCurrentStockPrice = currentStockPrice;
    }

    public void addFollower(Follower follower) {
        mFollowers.add(follower);
    }
}
