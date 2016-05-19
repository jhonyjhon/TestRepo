package testerConfiguration;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class TesterConfiguration
{
	private static Configuration configuration = null;
	private static String file = "/testerConfig.xml";
	
	private TesterConfiguration()
	{	
	}
	
	public static void loadConfiguration()
	{
		loadConfiguration(null);
	}

	public static void loadConfiguration(String pFile)
	{
		try 
		{
			if (pFile != null)
			{
				file = pFile;
			}
	        
			if (file == null || TesterConfiguration.class.getResourceAsStream(file) == null)
			{
				System.out.println("Configuration file is missing");
				System.exit(1);
			}
			TesterConfiguration.class.getResourceAsStream(file);
			
			if(configuration == null)
			{
				readFromXml();
			}
		}
		catch(Exception e)
		{
			System.out.println("Failed to load configuration. Exiting.");
			System.exit(1);
		}
	}
	
	public static Configuration getConfiguration() 
	{
		if(configuration == null)
		{
			System.out.println("Hydra configuraion is empty, please load configuration first");
		}
		
		return configuration;
	}
	
	public void reReadConfiguration() throws JAXBException
	{
		readFromXml();
	}

	private static void readFromXml() throws JAXBException 
	{	
		try 
		{
			InputStream stream = TesterConfiguration.class.getResourceAsStream(file);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			configuration = (Configuration) jaxbUnmarshaller.unmarshal(stream);
			System.out.println("The configuration was successfully loaded");
		} 
		catch (JAXBException e) 
		{
			throw e;
		}
	}
}