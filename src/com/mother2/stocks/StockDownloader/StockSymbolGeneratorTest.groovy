package com.mother2.stocks.StockDownloader
/**
 * Created by gregorynield on 8/17/16.
 * Make sure this create greater then 4000 stocks.
 */
class StockSymbolGeneratorTest extends groovy.util.GroovyTestCase {
    void testGetStockNames() {

        StockSymbolGenerator stockSymbolGenerator = new StockSymbolGenerator(4);
        assert(stockSymbolGenerator.getStockNames().size() > 4000);
    }
}
