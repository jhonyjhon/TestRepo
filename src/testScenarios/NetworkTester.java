package testScenarios;

import hydraTester.NetworkTest.NetworkApiTester;
import testerConfiguration.TesterConfiguration;

public class NetworkTester
{
	public static void main(String[] args) 
	{
		TesterConfiguration.loadConfiguration();
		
		NetworkApiTester networkTester = new NetworkApiTester();
		
		final String tagId_1 = "111";
		final String tagId_2 = "15";

		networkTester.cleanState();  
        
        System.out.println("Network API tests:");
        System.out.println("==================");
        System.out.println("Test 1...");
        
        networkTester.initState( tagId_1 );
		networkTester.performTest();        
		String reportStr_1 = networkTester.createReport();
        System.out.println(reportStr_1); 

        
        
        networkTester.cleanState();
        
        System.out.println("Network API tests:");
        System.out.println("==================");
        System.out.println("Test 2...");
        
        networkTester.initState( tagId_2 );        
        networkTester.performTest();
        String reportStr_2 = networkTester.createReport();
        System.out.println(reportStr_2);         
        
        System.out.println("\nNetwork API test END:");
        System.out.println("========================\n");          
	}

}
