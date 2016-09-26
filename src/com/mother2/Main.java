package com.mother2;

import com.mother2.stocks.Mother;

public class Main {

    private final int sizeOfStockSymbolName = 4;
    //
    // WORK TODO
    // 1) DONE Download complete stock point.
    // 2) DONE Save to database the stock point with close.
    // 3) Create a leader that
    //    DONE  a) Goes up and stays up for a certain amount.
    //      b) 2 days to a month later. The follower goes up and stays up for a month.
    //      This has to happen > 3 times. Note the number of times.

    public static void main(String[] args) {


        //
        // Download the complete stock information.
        //
        Mother mother = new Mother();
        //int stocksFound = mother.downloadStocksHistoryToMothersDatabase(sizeOfStockSymbolName);



   //     System.out.println("\nStocks found = " + stocksFound);


        // mother.findLeadersAndFollowers();

        //
        // Ex dividen work.
        //



    }
}
