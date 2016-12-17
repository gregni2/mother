package com.mother2.stocks.StockDownloader.datacollector;

import com.mother2.stocks.StockDownloader.StockPoint;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by gregorynield on 5/12/16.
 */
public class MothersSQLStockDatabase {

    private Connection mConnection = null;

    public MothersSQLStockDatabase() {

        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Got a sql light!");
        } catch (Exception e) {
            System.err.println("Failed to connect to sql light!");
        }

        try {
            mConnection = DriverManager.getConnection("jdbc:sqlite:motherdatabase.db");
        } catch (Exception e) {
            System.err.println("Exception e = " + e);
        }
    }

    public boolean createTable() {

        boolean tableCreated = false;
        try
        {
            // create a database mConnection
            Statement statement = mConnection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists stockpoint");
            statement.executeUpdate("create table stockpoint (id integer, symbolname string, date string, openprice double, closeprice double)");
            tableCreated = true;
        } catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }

        return tableCreated;
    }


    public void closeStockSaver() {
        try
        {
            if(mConnection != null)
                mConnection.close();
        }
        catch(SQLException e)
        {
            // mConnection close failed.
            System.err.println(e);
        }

    }

    public void addStockPoint(StockPoint stockPoint) {
        try {
            Statement statement = mConnection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //
            // Setup the string.
            //
            java.sql.Date sqlDate = new java.sql.Date(stockPoint.getDate().getTime());
            String sqlString =  "insert into stockpoint values(1, '" +
                                stockPoint.getmName() +
                                "', '" +
                                sqlDate +
                                "', '" +
                                stockPoint.getOpen() +
                                "', " +
                               stockPoint.getClose() + "  )";
            statement.executeUpdate(sqlString);
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    public int getStockpointCount() {
        Statement statement = null;
        try {
            statement = mConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM stockpoint");
            int total = rs.getInt("total");
            return total;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void listAllStocks() {

        try {
            Statement statement = mConnection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            // stockpoint (id integer, symbolname string, date string, openprice double, closeprice double);
            ResultSet rs = statement.executeQuery("SELECT a, b, c, d, e FROM stockpoint");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

    // The column count starts from 1
            for (int i = 1; i <= columnCount; i++ ) {
                String name = rsmd.getColumnName(i);
                System.out.println("******* NAME = " + name);
                // Do stuff with name
            }
        } catch (Exception e) {
        }

        try {
            Statement statement = mConnection.createStatement();
            ResultSet rs = statement.executeQuery("select * from stockpoint");
            while (rs.next()) {
                // read the result set
                System.out.println("stock = " + rs.getString("symbolname"));
                System.out.println("id = " + rs.getInt("id"));
                System.out.println("Close price = " + rs.getDouble("closeprice"));
                String dateupString = rs.getString("date");

                DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                java.util.Date today = df.parse(dateupString);

                System.out.println("date = " + today);
            }

        } catch (Exception e) {
            System.err.println("Exception = " + e);
            System.err.println(e.getStackTrace());
        }
    }


    public Connection getmConnection() {
        return mConnection;
    }

}
