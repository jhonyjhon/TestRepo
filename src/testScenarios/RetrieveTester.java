package testScenarios;

import hydraTester.RetrieveadsTest.RetrieveadsTester;
import testerConfiguration.TesterConfiguration;

public class RetrieveTester
{
    public static void main( String[] args )
    {
        TesterConfiguration.loadConfiguration();

        RetrieveadsTester retrieveadsTester = new RetrieveadsTester();
        
        retrieveadsTester.cleanState();
        
        System.out.println("\nRetrieveadsTester tests:");
        System.out.println("========================\n");
        
        retrieveadsTester.initState();
        
        retrieveadsTester.performTest();
        String reportStr = retrieveadsTester.createReport();
        System.out.println(reportStr);
        
        System.out.println("\nRetrieveadsTester test END:");
        System.out.println("========================\n");        
    }

}
