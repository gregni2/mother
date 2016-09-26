package com.mother2.stocks.Analyzer.common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;

/**
 * Created by gregorynield on 8/24/16.
 */
public class Relationship {
    private LinkedList<Object> mRelationshipWith;
    private static final Logger mLogger = LogManager.getRootLogger();


    public LinkedList<Object> getRelationshipWith() {
        return mRelationshipWith;
    }

    public void addRelationshipWith(Object object) {
        mRelationshipWith.add(object);
    }
}
