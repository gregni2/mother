package com.mother2.stocks.Analyzer.datacorolators;

import java.util.ArrayList;

/**
 * Created by gregorynield on 5/15/16.
 */
public class Leader {

    public String mStockName;
    public ArrayList<Follower> mFollowers;
    public int mTimesLead;

    public Leader(String mStockName, int mTimesLead) {
        this.mStockName = mStockName;
        this.mTimesLead = mTimesLead;
    }

    public void addFollower(Follower follower) {
        mFollowers.add(follower);
    }
}
