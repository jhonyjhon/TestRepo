package testerConfiguration;

import javax.xml.bind.annotation.XmlElement;

public class General
{
	private String sdkApiUrl;
	private String networkApiUrl;
	private String retrieveadsUrl;
	
	public String getSdkApiUrl()
	{
		return sdkApiUrl;
	}

	@XmlElement
	public void setSdkApiUrl(String sdkApiUrl)
	{
		this.sdkApiUrl = sdkApiUrl;
	}

	public String getNetworkApiUrl()
	{
		return networkApiUrl;
	}

	@XmlElement
	public void setNetworkApiUrl(String networkApiUrl)
	{
		this.networkApiUrl = networkApiUrl;
	}

    public String getRetrieveadsUrl()
    {
        return retrieveadsUrl;
    }

    @XmlElement
    public void setRetrieveadsUrl(String retrieveadsUrl)
    {
        this.retrieveadsUrl = retrieveadsUrl;
    }
    
	
}
