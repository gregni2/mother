package com.mother2.stocks

/**
 * Created by gregorynield on 8/21/16.
 */
class MotherTest extends GroovyTestCase {
    void testDownloadStocksHistoryToMothersDatabase() {

        Mother mother = new Mother();
        mother.downloadStocksHistoryToMothersDatabase(/* sizeOfStockName */ 2);
    }

    void testFindLeadersAndFollowers() {

    }
}
