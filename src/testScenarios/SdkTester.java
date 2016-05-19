package testScenarios;

import hydraTester.SdkTest.SdkApiTester;
import testerConfiguration.TesterConfiguration;

public class SdkTester
{
    public static void main(String[] args) 
    {
        TesterConfiguration.loadConfiguration();
        
        SdkApiTester sdkTester = new SdkApiTester();
        
        final String tagId_1 = "113";
        final String tagId_2 = "112";

        sdkTester.cleanState();  
        
        System.out.println("SDK API tests:");
        System.out.println("==============");
        System.out.println("Test 1...");
        
        sdkTester.initState( tagId_1 );
        sdkTester.performTest();        
        String reportStr_1 = sdkTester.createReport();
        System.out.println(reportStr_1); 

        
        
        sdkTester.cleanState();
        
        System.out.println("SDK API tests:");
        System.out.println("==============");
        System.out.println("Test 2...");
        
        sdkTester.initState( tagId_2 );        
        sdkTester.performTest();
        String reportStr_2 = sdkTester.createReport();
        System.out.println(reportStr_2);         
        
        System.out.println("\nSDK API test END:");
        System.out.println("========================\n");          
    }	
}
