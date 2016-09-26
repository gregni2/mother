package com.mother2.stocks.StockDownloader

/**
 * Created by gregorynield on 8/20/16.
 */
class StockDownloaderTest extends GroovyTestCase {
    void testGetStockPoints() {

        //
        // Setup the time period to check it.
        //
        final GregorianCalendar START_STOCK_HISTORY = new GregorianCalendar(2010, 1, 1);
        final GregorianCalendar END_STOCK_HISTORY = new GregorianCalendar(2015, 3, 30);
        YahooFinanceStockDownloader stockDownloader = new YahooFinanceStockDownloader("FUN", START_STOCK_HISTORY, END_STOCK_HISTORY);

        //
        // Asserts. Check if it exists (it should). Check the number of stock points.
        //
        assert(stockDownloader.doesStockExist());
        assert(stockDownloader.getStockPoints() != null);
        assert(stockDownloader.getStockPoints().size() == 1321)
    }
}
