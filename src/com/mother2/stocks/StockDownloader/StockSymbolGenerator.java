package com.mother2.stocks.StockDownloader;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by gregorynield on 5/12/16.
 * Generate a number of stock symbols.
 */
public class StockSymbolGenerator {
    private ArrayList<String> mStockNames;
    private int mEndSize;

    //
    // Give a name from 0 to endSize generate stock names.
    //
    public StockSymbolGenerator(int endSize) {
        mEndSize = endSize;
        mStockNames = new ArrayList<>();

        //
        // Fill the array list with names.
        //
        String currentName = "";
        fillStockNames(currentName);
    }

    private void fillStockNames(String currentName) {
        if (currentName.length() >= mEndSize) {
            return;
        }

        for (int letter = 'A'; letter <= 'Z'; letter++ ) {
            String newName = currentName + (char)letter;
            mStockNames.add(newName);
            fillStockNames(newName);
        }
    }

    //
    // Return all possible stock symbol.
    //
    public ArrayList<String> getStockNames() { return mStockNames;}
}
