package com.mother2.stocks;

import com.mother2.stocks.StockDownloader.StockPoint;
import com.mother2.stocks.StockDownloader.datacollector.MothersSQLStockDatabase;
import com.mother2.stocks.StockDownloader.YahooFinanceStockDownloader;
import com.mother2.stocks.Analyzer.datacorolators.LeaderFinder;
import com.mother2.stocks.StockDownloader.StockSymbolGenerator;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Created by gregorynield on 5/13/16.
 */
public class Mother {

    private static final int MAX_STOCKS_TO_READ = 100;
    private static final GregorianCalendar START_STOCK_HISTORY = new GregorianCalendar(2010, 1, 1);
    private static final GregorianCalendar END_STOCK_HISTORY = new GregorianCalendar(2015, 3, 30);
    private MothersSQLStockDatabase mothersDatabase = new MothersSQLStockDatabase();

    public void downloadStocksHistoryToMothersDatabase(int sizeOfStockSymbols) {
        //
        // Generate a list of stock names.
        //
        System.out.println("Generating names");
        StockSymbolGenerator nameGenerator = new StockSymbolGenerator(sizeOfStockSymbols);

        //
        // Create the stock history database.
        //
        mothersDatabase.createTable();

        //
        // Add all the stock history to the database.
        //
        int count = 0;
        int stocksFound = 0;
        Iterator<String> iter = nameGenerator.getStockNames().iterator();
        while (iter.hasNext() && count < MAX_STOCKS_TO_READ) {

            //
            // Get the name.
            //
            String stockName = iter.next();
            System.out.println("Stockname = " + stockName);

            //
            // Download the history of the stock.
            //
            YahooFinanceStockDownloader sd = new YahooFinanceStockDownloader(stockName, START_STOCK_HISTORY, END_STOCK_HISTORY);
            if (sd.doesStockExist()) {
                System.out.print("GOT IT Stock exists!\n\n");
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
                System.out.println("Date = " + stockPoint.getDate());

                //
                // Add the found data to mothers database.
                //
                //java.sql.Date sqlDate = new java.sql.Date(stockPoint.getDate().getTime());
                mothersDatabase.addStockPoint(stockPoint);
                pos++;
            }
            System.out.print("added " + pos);

            count++;
        }
    }

    public void listAllStocks()  {
        mothersDatabase.listAllStocks();
    }

    public void findLeadersAndFollowers() {
        LeaderFinder finder = new LeaderFinder(mothersDatabase, 5, 1, 1, 0.10);
    }


}
