package com.mother2.stocks.StockDownloader;

import com.mother2.stocks.StockDownloader.datacollector.MothersSQLStockDatabase;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Created by gregorynield on 8/22/16.
 */
public class YahooStocksToDatabaseDownloader {

    private int mMaxStockToRead;
    private GregorianCalendar mStartStockHistoryDate;
    private GregorianCalendar mEndStockHistoryDate;
    private MothersSQLStockDatabase mothersDatabase = new MothersSQLStockDatabase();
    private static final Logger mLogger = LogManager.getRootLogger();

    public YahooStocksToDatabaseDownloader(int maxStockToRead, GregorianCalendar startStockHistoryDate, GregorianCalendar endStockHistoryDate) {
        this.mMaxStockToRead = maxStockToRead;
        this.mStartStockHistoryDate = startStockHistoryDate;
        this.mEndStockHistoryDate = endStockHistoryDate;
    }

    /**
     * Download generated stocks from the internet and store them in Mother's database.
     *
     * @param sizeOfStockSymbols The max number of characters for a stock symbol.
     * @param sleepInMinutes The number of minutes to sleep.
     * @return int the number of stocks download.
     */
    private int downloadStocks(int sizeOfStockSymbols, int sleepInMinutes) {
        //
        // Generate a list of stock names.
        //
        mLogger.info("Generating names");
        StockSymbolGenerator nameGenerator = new StockSymbolGenerator(sizeOfStockSymbols);

        //
        // Create the stock history database.
        //
        mothersDatabase.createTable();

        //
        // Add all the stock history to the database.
        //
        int count = 1;
        int stocksFound = 0;
        Iterator<String> iter = nameGenerator.getStockNames().iterator();
        while (iter.hasNext() && count < mMaxStockToRead) {

            //
            // Don't kill yahoo. Sleep every now and then.
            //
            if ((count %300) == 0) {
                try {
                    System.out.println("Giving YAHOO a rest: SLEEP FOR " + sleepInMinutes + " minutes.");
                    Thread.sleep(sleepInMinutes*1000*60);
                    System.out.println("Done sleeping");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //
            // Get the name.
            //
            String stockName = iter.next();
            mLogger.debug("Stockname = " + stockName);
            System.out.println("Stockname = " + stockName + " count = " + count);


            //
            // Download the history of the stock.
            //
            YahooFinanceStockDownloader sd = new YahooFinanceStockDownloader(stockName, mStartStockHistoryDate, mEndStockHistoryDate);
            if (sd.doesStockExist()) {
                mLogger.debug("GOT IT Stock exists!\n\n");
                stocksFound++;
            }

            //
            // Go through each time and save it to the database.
            //
            int pos = 0;
            ArrayList<StockPoint> stockPoints = sd.getStockPoints();
            Iterator<StockPoint> stockPointIterator = stockPoints.iterator();
            while (stockPointIterator.hasNext()) {

                StockPoint stockPoint = stockPointIterator.next();
                mLogger.debug("Date = " + stockPoint.getDate());

                //
                // Add the found data to mothers database.
                //
                //java.sql.Date sqlDate = new java.sql.Date(stockPoint.getDate().getTime());
                mothersDatabase.addStockPoint(stockPoint);
                pos++;
            }
            mLogger.debug("added " + pos);

            count++;
        }

        mLogger.info("Downloaded " + count + " stocks to the database.");
        return count;
    }

}
