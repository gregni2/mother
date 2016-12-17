package com.mother2.stocks.StockDownloader

import com.mother2.stocks.StockDownloader.datacollector.MothersSQLStockDatabase

/**
 * Created by gregorynield on 8/22/16.
 */
class YahooStocksToDatabaseDownloaderTest extends GroovyTestCase {
    void testDownloadStocks() {

        final int MAX_STOCKS_TO_READ = 40;
        final GregorianCalendar START_STOCK_HISTORY = new GregorianCalendar(2010, 1, 1);
        final GregorianCalendar END_STOCK_HISTORY = new GregorianCalendar(2015, 3, 30);

        YahooStocksToDatabaseDownloader yahooStocksToDatabaseDownloader =
                new YahooStocksToDatabaseDownloader(MAX_STOCKS_TO_READ, START_STOCK_HISTORY, END_STOCK_HISTORY);

        yahooStocksToDatabaseDownloader.downloadStocks(2 /*stock names*/, 0/*sleeping minutes*/);

        MothersSQLStockDatabase mothersSQLStockDatabase = new MothersSQLStockDatabase();
        assert(mothersSQLStockDatabase.getStockpointCount() == 25860);
    }
}
