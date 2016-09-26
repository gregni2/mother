package com.mother2.stocks.StockDownloader;

import org.apache.log4j.LogManager;

import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by gregorynield on 5/12/16.
 */
public class YahooFinanceStockDownloader {
        private ArrayList<StockPoint> stockPoints = new ArrayList<StockPoint>();
        private boolean stockExists = false;
        private int successfulStockLookup;
        private static final org.apache.log4j.Logger mLogger = LogManager.getRootLogger();

        public YahooFinanceStockDownloader(String symbol, GregorianCalendar start, GregorianCalendar end) {
            mLogger.debug("************** LOOKING AT SYMBOL = " + symbol + "\n\n\n");
            successfulStockLookup = 0;
            stockExists = true;

            //
            // Yahoo finance API URL.
            //
            String url = "http://real-chart.finance.yahoo.com/table.csv?s="+symbol +"&a=" + start.get(Calendar.MONTH) +
                    "&b=" + start.get(Calendar.DAY_OF_MONTH) +
                    "&c=" + start.get(Calendar.YEAR) +
                    "&d=" + end.get(Calendar.MONDAY) +
                    "&e=" + end.get(Calendar.DAY_OF_MONTH) +
                    "&f=" + end.get(Calendar.YEAR) +
                    "&g=d&ignore=.csv";

            try {
                URL yahooFinanceURL = new URL(url);
                URLConnection data = yahooFinanceURL.openConnection();
                Scanner input = new Scanner(data.getInputStream());
                if (input.hasNext()) {
                    input.nextLine();

                    // start reading.
                    while (input.hasNext()) {
                        String line = input.nextLine();
                        stockPoints.add(getStockPointFromString(symbol, line));
                    }

                    successfulStockLookup++;
                }
            } catch (Exception e) {
                stockExists = false;
            }
        }

        private StockPoint getStockPointFromString(String name, String line) throws ParseException {
            //
            // Break up the parts of the stock point.
            //
            String[] value = line.split(",");

            //
            // Parse the date.
            //
            DateFormat parserSDF = new SimpleDateFormat("yyyy-mm-dd");
            Date date = parserSDF.parse(value[0]);

            //
            // Get up or down.
            //
            double open = Double.parseDouble(value[1]);
            double close = Double.parseDouble(value[4]);
            return new StockPoint(name, date, open, close);
        }

        public boolean doesStockExist() {
            return stockExists;
        }

        public ArrayList<StockPoint> getStockPoints() {
        return stockPoints;
    }
}
