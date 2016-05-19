package testScenarios;

import hydraTester.NetworkTest.NetworkApiTester;
import hydraTester.RetrieveadsTest.RetrieveadsTester;
import hydraTester.SdkTest.SdkApiTester;
import testerConfiguration.TesterConfiguration;

public class Tester {

	public static void main(String[] args) 
	{
		TesterConfiguration.loadConfiguration();
		SdkApiTester sdkTester = new SdkApiTester();
		NetworkApiTester networkTester = new NetworkApiTester();
		RetrieveadsTester retrieveadsTester = new RetrieveadsTester();
		
        String tagId_1 = "113";
        String tagId_2 = "112";          
        
        System.out.println("SDK API tests:");
        System.out.println("==============");
        System.out.println("Test 1...");
        
        sdkTester.cleanState();
        sdkTester.initState( tagId_1 );
        sdkTester.performTest();        
        
        System.out.println("SDK API tests:");
        System.out.println("==============");
        System.out.println("Test 2...");
        
        sdkTester.initState( tagId_2 );        
        sdkTester.performTest();
        
        String reportStr = sdkTester.createReport();
        System.out.println(reportStr);         
        
        System.out.println("\nSDK API test END:");
        System.out.println("========================\n"); 
		        
        
        System.out.println("\nRetrieveadsTester tests:");
        System.out.println("========================\n");
        
        retrieveadsTester.cleanState();
        retrieveadsTester.initState();
        
        retrieveadsTester.performTest();
        reportStr = retrieveadsTester.createReport();
        System.out.println(reportStr);
        
        System.out.println("\nRetrieveadsTester test END:");
        System.out.println("========================\n");   
        
        
        tagId_1 = "111";
        tagId_2 = "114";         
        
        System.out.println("Network API tests:");
        System.out.println("==================");
        System.out.println("Test 1...");
        
        networkTester.cleanState();
        networkTester.initState( tagId_1 );        
        networkTester.performTest();                       
        
        System.out.println("Network API tests:");
        System.out.println("==================");
        System.out.println("Test 2...");
        
        networkTester.initState( tagId_2 );        
        networkTester.performTest();
        reportStr = networkTester.createReport();
        System.out.println(reportStr);         
        
        System.out.println("\nNetwork API test END:");
        System.out.println("========================\n");         
	}

}
