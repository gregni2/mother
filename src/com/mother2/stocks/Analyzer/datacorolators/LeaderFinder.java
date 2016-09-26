package com.mother2.stocks.Analyzer.datacorolators;

import com.mother2.stocks.StockDownloader.datacollector.MothersSQLStockDatabase;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by gregorynield on 5/13/16.
 */

public class LeaderFinder {
    private ArrayList<Leader> mStocksMovedUp = new ArrayList<>();

    public LeaderFinder(MothersSQLStockDatabase savedStocks, int upForDays, int startDayLag, int endDayLag, double minPercentUp) {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        ArrayList<Leader> possibleLeaders = new ArrayList<>();


        //
        // Iterate through all the stocks that went up.
        //
        try {
            Statement statement = savedStocks.getmConnection().createStatement();
            ResultSet rs = statement.executeQuery("select * from stockpoint where up = 1");
            while (rs.next()) {
                String stockname = rs.getString("symbolname");
              //  System.out.println("stockname = " + stockname);

                //
                // Get the date it went up and the price.
                //
                String dateupString = rs.getString("dateup");
                Date startDate = df.parse(dateupString);
                Double startPrice = rs.getDouble("closeprice");

                possibleLeaders.add(new Leader(stockname, dateupString, upForDays, startPrice));
            }
            rs.close();

            Iterator<Leader> iter = possibleLeaders.iterator();

            while (iter.hasNext())
            {
                Leader possibleLeader = iter.next();

                //
                // See if it's still up in a given time period.
                //
                Date dateStart = df.parse(possibleLeader.mDateUp);
                Date dateEnd = addDays(dateStart, 40 /*upForDays*/);

                //
                // Get the stock price in upForDays.
                //
                String dateEndString = df.format(dateEnd);
            //    String queryString = "select * from stockpoint where dateup like \"" + dateEndString  +"\" and symbolname like " + "\"" + possibleLeader.mStockName + "\"";
              //  Statement newStatement = savedStocks.getmConnection().createStatement();
               // ResultSet inAWeek = newStatement.executeQuery(queryString);
               // Double lastPrice = inAWeek.getDouble("closePrice");

                double lastPrice = 0;
                Iterator<Leader> iter2 = possibleLeaders.iterator();
                boolean notFound = true;
                while (iter2.hasNext() && notFound) {
                    Leader stock = iter2.next();
                    //System.out.println("Stock = " + stock.mStockName + " possibleLeader.mStockName = " + possibleLeader.mStockName + " stock.mDateUp="+stock.mDateUp );
                    if (stock.mStockName.equalsIgnoreCase(possibleLeader.mStockName) && stock.mDateUp.equalsIgnoreCase(dateEndString)) {
                      //  System.out.println("GOT IT!");
                        lastPrice = stock.mCurrentStockPrice;
                        notFound = false;
                    }
                }


                //
                // If it's up by at least "minPercentUp" look for followers.
                //
                if ((possibleLeader.mCurrentStockPrice <= lastPrice) ) {
                    double change = lastPrice - possibleLeader.mCurrentStockPrice;
                    double percentUp = change/possibleLeader.mCurrentStockPrice;

                    if (percentUp > minPercentUp) {
                        //System.out.println("STOCK " + possibleLeader.mStockName + " is up by " + percentUp*100 + " for " + upForDays + " days.");
                        //System.out.println("CurrentStock = " + possibleLeader.mCurrentStockPrice + " lastPrice = " + lastPrice + "\n");
                        //
                        // Find followers.
                        //
                        mStocksMovedUp.add(possibleLeader);
                    }

                }
            }

            System.out.println("Got all the stocks up by the required percentage.!");

            //
            // Find stocks that go up after it moves up.
            //
            iter = mStocksMovedUp.iterator();

            while (iter.hasNext())
            {
                Leader possibleLeader = iter.next();

                //
                // Look for other stocks that moved up AFTER the possibleLeader.
                //
                System.out.println("Inspecting " + possibleLeader.mStockName + " at " + possibleLeader.mCurrentStockPrice + " for leadership.");


                //
                // Get the date to check if it went up.
                //
                String dateUp = possibleLeader.mDateUp;
                System.out.println("DateUp = " + dateUp);
                SimpleDateFormat currentLeadersDateFormat = new SimpleDateFormat("yyyy-dd-MM");
                Date currentLeaderDate = currentLeadersDateFormat.parse(dateUp);
                currentLeaderDate = addDays(currentLeaderDate, 2);



                Iterator<Leader> possibleFollowersIterator = mStocksMovedUp.iterator();
                while (possibleFollowersIterator.hasNext()) {
                    Leader possibleFollower = possibleFollowersIterator.next();
                    dateUp = possibleFollower.mDateUp;

                    //
                    Date currentFollowerDate = currentLeadersDateFormat.parse(dateUp);


                    if (!possibleFollower.mStockName.trim().equalsIgnoreCase(possibleLeader.mStockName.trim()) &&
                            currentFollowerDate.after(currentLeaderDate)) {
                       System.out.println("\t\tpossible follower " + possibleFollower.mStockName);
                       // System.out.println("Looking to see if others ");




                       //
                       // Loop through the rest of them.
                       //
                       Iterator<Leader> iter3 = mStocksMovedUp.iterator();
                       while (iter3.hasNext()) {
                           Leader follower = iter3.next();

                           //
                           // See if it's
                           //
                       }
                    }
                }
            }

            System.out.println("Leader = " + mStocksMovedUp.size());

        } catch (Exception e) {
            System.err.println("Exception = " + e);
        }

    }

    public static Date addDays(Date baseDate, int daysToAdd) {
        Date dayAfter = new Date(baseDate.getTime() + TimeUnit.DAYS.toMillis(daysToAdd));
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        return dayAfter;
    }
}
