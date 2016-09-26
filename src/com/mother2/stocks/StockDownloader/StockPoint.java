package com.mother2.stocks.StockDownloader;

import java.util.Date;

/**
 * Created by gregorynield on 8/20/16.
 */
public class StockPoint {
    private String mName;
    private Date mDate;
    private Double mOpen;
    private Double mClose;

    public StockPoint(String name, Date date, Double open, Double close) {
        this.mName = name;
        this.mDate = date;
        this.mOpen = open;
        this.mClose = close;
    }

    public Date getDate() {
        return mDate;
    }
    public Double getOpen() {
        return mOpen;
    }
    public String getmName() { return mName; }

    public Double getClose() {
        return mClose;
    }
}
