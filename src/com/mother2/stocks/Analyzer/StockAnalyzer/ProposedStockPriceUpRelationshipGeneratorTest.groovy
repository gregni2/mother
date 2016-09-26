package com.mother2.stocks.Analyzer.StockAnalyzer

/**
 * Created by gregorynield on 8/25/16.
 */
class ProposedStockPriceUpRelationshipGeneratorTest extends GroovyTestCase {
    void testGenerateRelationships() {
        System.out.println("ProposedStockPriceUpRelationshipGeneratorTest");
        ProposedStockPriceUpRelationshipGenerator proposedStockRelationshipGenerator = new ProposedStockPriceUpRelationshipGenerator();

        System.out.println("proposedStockRelationshipGenerator.generateRelationships()");
        proposedStockRelationshipGenerator.generateRelationships();
    }
}
