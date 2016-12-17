package com.mother2.stocks.Analyzer.common

import com.mother2.stocks.Analyzer.datacorolators.Follower
import com.mother2.stocks.Analyzer.datacorolators.Lead
import com.mother2.stocks.Analyzer.datacorolators.Leader
import com.mother2.stocks.Analyzer.datacorolators.Streak
import com.mother2.stocks.StockDownloader.StockSymbolGenerator
import com.mother2.stocks.StockDownloader.YahooStocksToDatabaseDownloader
import com.mother2.stocks.StockDownloader.datacollector.MothersSQLStockDatabase

import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by gregorynield on 10/1/16.
 */
class StockPriceupLeaderIndentiferTest extends GroovyTestCase {

    void testFindStreaks() {
        //
        // Download some stocks.
        //
        final int MAX_STOCKS_TO_READ = 5;
        final GregorianCalendar START_STOCK_HISTORY = new GregorianCalendar(2010, 1, 1);
        final GregorianCalendar END_STOCK_HISTORY = new GregorianCalendar(2015, 3, 30);
        YahooStocksToDatabaseDownloader yahooStocksToDatabaseDownloader =
                new YahooStocksToDatabaseDownloader(MAX_STOCKS_TO_READ, START_STOCK_HISTORY, END_STOCK_HISTORY);
        yahooStocksToDatabaseDownloader.downloadStocks(2 /*stock names*/, 0/*sleeping mintes*/);

        //
        // Get mother's database.
        //
        MothersSQLStockDatabase mothersSQLStockDatabase = new MothersSQLStockDatabase();
        //mothersSQLStockDatabase.listAllStocks();

        //
        // Create the stock change identifier.
        //
        StockPriceupLeaderIndentifer stockPriceupLeaderIndentifer = new StockPriceupLeaderIndentifer(mothersSQLStockDatabase);

        //
        // Look for streaks.
        //
        StockSymbolGenerator stockSymbolGenerator = new StockSymbolGenerator(4);
        Iterator<String> stockIterator = stockSymbolGenerator.getStockNames().iterator();
        while (stockIterator.hasNext()) {
            String name = stockIterator.next();

            //
            // Look for a streak.
            //
            List<Leader> upPointsList = new ArrayList<>();
            stockPriceupLeaderIndentifer.getAllStreaksForStock(name, upPointsList, /* what's a streak*/ 6);
        }
    }

    void testGetListOfLeads() {
        //
        // Download some stocks.
        //
        final int MAX_STOCKS_TO_READ = 50000;
        final GregorianCalendar START_STOCK_HISTORY = new GregorianCalendar(2005, 1, 1);
        final GregorianCalendar END_STOCK_HISTORY = new GregorianCalendar(2015, 3, 30);
        YahooStocksToDatabaseDownloader yahooStocksToDatabaseDownloader =
                new YahooStocksToDatabaseDownloader(MAX_STOCKS_TO_READ, START_STOCK_HISTORY, END_STOCK_HISTORY);
        // yahooStocksToDatabaseDownloader.downloadStocks(2 /*stock names*/, 5/*sleeping mintes*/);

        //
        // Get mother's database.
        //
        MothersSQLStockDatabase mothersSQLStockDatabase = new MothersSQLStockDatabase();
        //mothersSQLStockDatabase.listAllStocks();

        //
        // Create the stock change identifier.
        //
        StockPriceupLeaderIndentifer stockPriceupLeaderIndentifer = new StockPriceupLeaderIndentifer(mothersSQLStockDatabase);

        //
        // Look for streaks.
        //
        StockSymbolGenerator stockSymbolGenerator = new StockSymbolGenerator(2);
        Iterator<String> stockIterator = stockSymbolGenerator.getStockNames().iterator();
        List<Streak> completeStreakList = new ArrayList<>();
        int count = 0;
        System.out.println("Generating stocks names. Number of names = " + stockSymbolGenerator.getStockNames().size());
        System.out.println("Looking at ")
        while (stockIterator.hasNext()) {
            //
            // Look for a streak.
            //
            List<Streak> streakList = new ArrayList<>();
            String name = stockIterator.next();
            stockPriceupLeaderIndentifer.getAllStreaksForStock(name, streakList, /* what's a streak*/ 10);
            completeStreakList.addAll(streakList);
            count++;
        }

        //
        // What's being tested! GET LEAD LIST.
        //
        List<Lead> leadList = new ArrayList<Lead>();
        int daysToFollow = 30;
        stockPriceupLeaderIndentifer.getLeadList(completeStreakList, leadList, daysToFollow);


        //
        // Print out the leader list.
        //
        System.out.println("*********** LEADERS FOUND (" + leadList.size() + ") ****************");
        Iterator<Lead> iter = leadList.iterator();
        while (iter.hasNext()) {
            Lead lead = iter.next();
            System.out.println("LEADER:" + lead.mStockName);
            System.out.println("\tFollowers");

            Iterator<Follower> followerIterator = lead.mFollowerList.iterator();
            while (followerIterator.hasNext()) {
                Follower follower = followerIterator.next();
                System.out.println("\t"+follower.mStockName + "(dayup="+follower.mDateUp+" streak="+follower.mDaysUp+")");
            }
        }
    }


    public void save(String fileName, List<Streak> streaks) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        for (Streak streak : streaks)
            pw.println(streak);
        pw.close();
    }


}
