package hydraTester;

import java.util.HashMap;

public enum StatusCode
{
	TEST_OK(0, "testOk"),
	NO_RESPONSE(10, "noResponse"),
	MISSING_IMP_URL(20, "missingImpUrl"),
	MISSING_CLICK_URL(30, "missingClickUrl"),
	MISSING_DESTINATION_URL(35, "missingDestinationUrl"),
	NO_IMP_RESPONSE(40, "noImpResponse"),
	NO_CLICK_RESPONSE(50, "noClickResponse"),
	NO_BID_RESPONSE(60, "noBidResponse");
	
	
	private static HashMap<Integer, StatusCode> statusMap= new HashMap<Integer, StatusCode>();
	private int code;
	private String status;
	
	static
	{
		statusMap.put(TEST_OK.code, TEST_OK);
		statusMap.put(NO_RESPONSE.code, NO_RESPONSE);
		statusMap.put(MISSING_IMP_URL.code, MISSING_IMP_URL);
		statusMap.put(MISSING_CLICK_URL.code, MISSING_CLICK_URL);
		statusMap.put(MISSING_DESTINATION_URL.code, MISSING_DESTINATION_URL);
		statusMap.put(NO_IMP_RESPONSE.code, NO_IMP_RESPONSE);
		statusMap.put(NO_CLICK_RESPONSE.code, NO_CLICK_RESPONSE);
		statusMap.put(NO_BID_RESPONSE.code, NO_BID_RESPONSE);
	}
	
	private StatusCode(int code, String status)
	{
		this.code = code;
		this.status = status;
	}
	
	public static StatusCode get(Integer code)
	{
		if( statusMap.containsKey(code))
		{
			return statusMap.get(code);
		}
		return null;
	}

	public Integer getCode()
	{
		return code;
	}

	public String getStatus()
	{
		return status;
	}
}
