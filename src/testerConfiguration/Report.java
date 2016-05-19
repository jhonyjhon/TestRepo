package testerConfiguration;

import javax.xml.bind.annotation.XmlElement;

public class Report
{
   
	private boolean reportResultStr;
	private boolean reportAll;
	
	public boolean getReportResultStr()
	{
		return reportResultStr;
	}

	@XmlElement
	public void setReportResultStr(boolean reportResultStr)
	{
		this.reportResultStr = reportResultStr;
	}

	public boolean getReportAll()
	{
		return reportAll;
	}

	@XmlElement
	public void setReportAll(boolean reportAll)
	{
		this.reportAll = reportAll;
	}
	
}
