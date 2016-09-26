package com.mother2.stocks.Analyzer.StockAnalyzer;

import com.mother2.stocks.Analyzer.StockAnalyzer.relationships.GSNWorkOnStocksMoveUpAfterLeaderRelationship;
import com.mother2.stocks.Analyzer.common.Relationship;
import com.mother2.stocks.Analyzer.common.RelationshipGenerator;
import org.apache.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregorynield on 8/24/16.
 */
public class ProposedStockPriceUpRelationshipGenerator implements RelationshipGenerator {

    private static final org.apache.log4j.Logger mLogger = LogManager.getRootLogger();
    //
    // Here are the possible parameters of movement. 1) Number of days and 2) Percent of movement.
    //
    private static final int DAYS_TO_LOOK_FOR_CHANGE = 45;
    private static final double LOOK_FOR_MOVEMENT_OF = 1.00;


    List<Relationship> mGeneratedRelationships = new ArrayList<>();

    public ProposedStockPriceUpRelationshipGenerator() {
        System.out.println("ProposedStockPriceUpRelationshipGenerator");
    }


    public void generateRelationships() {

        System.out.println("generateRelationships entered");

        //
        // Look for these price change relationships.
        //

        //
        // Look for changes from 5 percent to 100 percent.
        //
        for (double priceMovementMin = 0.05; priceMovementMin <= LOOK_FOR_MOVEMENT_OF; priceMovementMin=priceMovementMin+0.05 ) {
            //
            // Look for Changes up.
            //
            for (int daysChanged = 1; daysChanged <= DAYS_TO_LOOK_FOR_CHANGE; daysChanged++) {
                addRelationshipInTimePeriod(daysChanged, priceMovementMin);
            }

            //
            // Look for changed down.
            //
        }
    }

    private void addRelationshipInTimePeriod(int days, double percentChanged) {
        GSNWorkOnStocksMoveUpAfterLeaderRelationship generatedRelationShip = new GSNWorkOnStocksMoveUpAfterLeaderRelationship(percentChanged, days);
        mGeneratedRelationships.add(generatedRelationShip);
    }

}
