package com.mother2.stocks.Analyzer.common;

import com.mother2.stocks.Analyzer.datacorolators.Follower;
import com.mother2.stocks.Analyzer.datacorolators.Lead;
import com.mother2.stocks.Analyzer.datacorolators.Leader;
import com.mother2.stocks.Analyzer.datacorolators.Streak;
import com.mother2.stocks.StockDownloader.datacollector.MothersSQLStockDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gregorynield on 10/1/16.
 */
public class StockPriceupLeaderIndentifer extends LeaderIdentifier {

    private static final int MAX_STOCKS_TO_READ = 100;
    private static final GregorianCalendar START_STOCK_HISTORY = new GregorianCalendar(2010, 1, 1);
    private static final GregorianCalendar END_STOCK_HISTORY = new GregorianCalendar(2015, 3, 30);
    private MothersSQLStockDatabase mMothersDatabase = new MothersSQLStockDatabase();
    private Statement statement = null;

    StockPriceupLeaderIndentifer(MothersSQLStockDatabase mothersDatabase) {
            try {
                statement = mMothersDatabase.getmConnection().createStatement();
            } catch (SQLException e) {
                 e.printStackTrace();
            }
            mMothersDatabase = mothersDatabase;
    }


  /*  public boolean isLeader(String stockSymbol, int numOfDaysForAStreak) {

        //
        // Are their times it went up for a while.
        //
        List<Streak> upPointsList =  new ArrayList<>();

        getAllStreaksForStock(stockSymbol, upPointsList, numOfDaysForAStreak);

        if (upPointsList.size() > 0) {
            System.out.println("Possible leader. It had streaks." + upPointsList.size() + stockSymbol);

            //
            // Are their times when stocks followed after a given period of time.
            //
            boolean didStocksFollow = didStocksFollow(stockSymbol, upPointsList);
        }


        return false;
    }
    */



    public void getLeadersList(List<Leader> leaders) {

    }

    private boolean didStocksFollow(String stockSymbol, List<Streak> upPointsList) {
        return false;
    }

    private class StockPosition {
        public Date mDate;
        public Double mPrice;

        public StockPosition(Date date, Double price) {
            mDate = date;
            mPrice = price;
        }
    }

    public void getAllStreaksForStock(String stockSymbol, List<Streak> streakList, int numOfDaysForAStreak) {
        try {
            String sqlQuery = "select * from stockpoint where symbolname='" + stockSymbol +"'";
            // System.out.println("SqlQuery = " + sqlQuery);

            double previousDaysPrice = -1.00;
            double startOfStreakPrice = 0.0;
            int currentDaysOnStreak = 0;

            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                String stockname = rs.getString("symbolname");

                //
                // Get the date it went up and the price.
                //
                String dateupString = rs.getString("date");
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                Date startDate = df.parse(dateupString);
                Double startPrice = rs.getDouble("closeprice");

                //
                // If we went up. Increase the last streak.
                //
                if (previousDaysPrice > 0) {
                    //
                    // System.out.println("stockname = " + stockname + ":startPrice = " +startPrice + " streak = " + currentDaysOnStreak);
                    //
                    if ( (startPrice - previousDaysPrice) > 0) {
                        currentDaysOnStreak++;
                    } else {
                        if (currentDaysOnStreak > numOfDaysForAStreak) {
                            System.out.println("Streak found for " + stockname + " : streak = " + currentDaysOnStreak + " starting = " + dateupString);
                            streakList.add(new Streak(stockname, dateupString, currentDaysOnStreak, startPrice));
                        }

                        currentDaysOnStreak = 0;
                        startOfStreakPrice = 0;
                    }
                    if (currentDaysOnStreak == 1) {
                        startOfStreakPrice = previousDaysPrice;
                    }
                }
                previousDaysPrice = startPrice;
                /* 3) Save it it's up. */
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Given the list of streaks. When did a stock lead the way.
     * @param streakList List<Streak>
     * @param leadList List<Lead> returning.
     */
    public void getLeadList(List<Streak> streakList, List<Lead> leadList, int daysToFollow) {
        Collections.sort(streakList, new DateComparator());

        int curPos = 0;
        while (curPos < streakList.size()) {
            Streak originalStreak = streakList.get(curPos);
            Lead lead = new Lead(originalStreak.mStockName, originalStreak.mDateUp, originalStreak.mDaysUp);

            //
            // Second list looking for followers.
            // Find within a date range of days to follow.
            //
            boolean done = false;;

            System.out.println("EXAMINING A STREAK = " + originalStreak.mStockName + " DATE = " + originalStreak.mDateUp + "curPos = " + curPos);
            curPos++;
            int followers = 0;
            while ((curPos+followers) < streakList.size() && !done) {
                Streak newSt = streakList.get(curPos+followers);

                //
                // If it's the same stock symble as the streak. Ignore it. But don't remove it??
                //
                if (originalStreak.mStockName.equalsIgnoreCase(newSt.mStockName)) {
                    System.out.println("\tIT'S THE SAME DARN SYMBOL!!!");
                    followers++;
                } else if (isInDateRange(originalStreak.mDateUp, daysToFollow, newSt.mDateUp)) {
                    System.out.println("\tFollower! " + newSt.mStockName );
                    Follower follower = new Follower(newSt.mStockName, newSt.mDateUp, newSt.mDaysUp);
                    lead.mFollowerList.add(follower);
                    followers++;
                } else {
                    System.out.println("Done");
                    done = true;
                }
            }
          //  curPos += followers; GSN. I DON'T THINK WE ACTUALLY WANT TO IGNORE THESE GUYS.

            if (lead.mFollowerList.size()>0) {
               System.out.println("\tLeader = " + lead.mStockName + " with " + lead.mFollowerList.size() + " followers");
                leadList.add(lead);
            }

            System.out.println("Streak list size = " + streakList.size() + " current pos " + curPos);
        }
    }

    class DateComparator implements Comparator<Streak> {
        @Override
        public int compare(Streak a, Streak b) {
            Date aDate = parseDateUp(a.mDateUp);
            Date bDate = parseDateUp(b.mDateUp);
          //  System.out.println("Compare a=" + a.mDateUp + " b =" + b.mDateUp + " compare = " + aDate.compareTo(bDate));
            return aDate.compareTo(bDate);

        }
    };

    Date parseDateUp(String dateUp) {
        //System.out.println("Date out = " + dateUp);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-dd-mm");
        Date date = null;
        try {
            date = parser.parse(dateUp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    boolean isInDateRange(String startDateString, int numberOfDaysUp, String currentDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            //System.out.println("Start Date String = " + startDateString);
            Date startDate = sdf.parse(startDateString);
            c.setTime(startDate);

            Date currentDate = sdf.parse(currentDateString);
            //System.out.println("Current Date = " + currentDate);


            Calendar c2 = Calendar.getInstance();
            c.add(Calendar.DATE, numberOfDaysUp);
            Date endDate = c.getTime();

            //System.out.print("Comparing start date = " + startDate + " to end date = " + endDate + " to current date" + currentDate);
            //System.out.println("Before = " + currentDate.before(endDate));
            return currentDate.before(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    boolean isFollower(Leader leader, String possibleFollower) { return false; };

}
