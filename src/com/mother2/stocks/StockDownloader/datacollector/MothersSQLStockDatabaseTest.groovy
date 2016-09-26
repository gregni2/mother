package com.mother2.stocks.StockDownloader.datacollector

import com.mother2.stocks.StockDownloader.StockPoint

import java.text.SimpleDateFormat

/**
 * Created by gregorynield on 8/21/16.
 */
class MothersSQLStockDatabaseTest extends GroovyTestCase {

    void testCreateTable() {
        MothersSQLStockDatabase mothersSQLStockDatabase = new MothersSQLStockDatabase();

        assert(mothersSQLStockDatabase.createTable());
    }

    void testAddStockPoint() {
        MothersSQLStockDatabase mothersSQLStockDatabase = new MothersSQLStockDatabase();
        mothersSQLStockDatabase.createTable();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.YY");

        StockPoint stockPointTestValue = new StockPoint("test", simpleDateFormat.parse("10.10.2010"), 10, 10);
        mothersSQLStockDatabase.addStockPoint(stockPointTestValue);
    }

    void testListAllStocks() {
        MothersSQLStockDatabase mothersSQLStockDatabase = new MothersSQLStockDatabase();
        mothersSQLStockDatabase.createTable();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < 100; i++) {
            StockPoint stockPointTestValue = new StockPoint("test"+i, simpleDateFormat.parse("05/05/2009"), 10+i, 10+i);
            mothersSQLStockDatabase.addStockPoint(stockPointTestValue);
        }

        mothersSQLStockDatabase.listAllStocks();
    }

    void testGetStockpointCount() {
        MothersSQLStockDatabase mothersSQLStockDatabase = new MothersSQLStockDatabase();
        mothersSQLStockDatabase.createTable();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 100; i++) {
            StockPoint stockPointTestValue = new StockPoint("test"+i, simpleDateFormat.parse("05/05/2009"), 10+i, 10+i);
            mothersSQLStockDatabase.addStockPoint(stockPointTestValue);
        }

        assert(mothersSQLStockDatabase.getStockpointCount() == 100);
    }
}
