package testerConfiguration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Configuration
{
	private General general;
	private Report report;
	
	protected Configuration()
	{
	}

	public General getGeneral()
	{
		return general;
	}

	@XmlElement(name="General")
	public void setGeneral(General general)
	{
		this.general = general;
	}
	
    public Report getReport()
    {
        return report;
    }

    @XmlElement(name="Report")
    public void setReport(Report report)
    {
        this.report = report;
    }	
}
