package hydraTester.states;

import java.security.InvalidParameterException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import testerConfiguration.TesterConfiguration;
import hydraTester.HttpHandler;
import hydraTester.StatusCode;

public abstract class StateRequest extends AbstractState
{
    private final String urlStr;
    private String resultStr = null;
    private int responseCode = 0;
    private final String postRequestBodyStr;
    private final boolean isPOSTRequest;
    private final HttpHandler httHandler;
    private StatusCode statusCode;

    public StateRequest( final String urlStr, HttpHandler httHandler )
    {
        this(urlStr, httHandler, false, null);
    }

    public StateRequest( final String urlStr, HttpHandler httHandler, boolean isPOSTRequest, final String bodyStr )
    {
        this.urlStr = urlStr;
        this.httHandler = httHandler;
        this.isPOSTRequest = isPOSTRequest;
        this.postRequestBodyStr = bodyStr;
    }

    public String getResultStr()
    {
        return resultStr;
    }

    public HttpHandler getHttpHandler()
    {
        return httHandler;
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public StatusCode getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode( StatusCode statusCode )
    {
        this.statusCode = statusCode;
    }

    //Override pull and return super's pull
    @Override
    public State pull( Chain wrapper )
    {
        HttpResponse response = null;
        try
        {
            response = createRequest();
            responseCode = response.getStatusLine().getStatusCode();
            resultStr = EntityUtils.toString(response.getEntity());
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            statusCode = initStatusCode(responseCode, resultStr);
        }
        
        return super.pull(wrapper);
    }

    protected HttpResponse createRequest() throws Exception
    {
        HttpResponse httpResponse = null;
        if ( isPOSTRequest )
        {
            if ( postRequestBodyStr != null )
            {
                httpResponse = httHandler.postRequest(urlStr, postRequestBodyStr);
            }
            else
            {
                throw new InvalidParameterException("Can't create POST request. Body is empty.");
            }
        }
        else
        {
            httpResponse = httHandler.getRequest(urlStr);
        }
        return httpResponse;
    }

    @Override
    public String toString()
    {
        StringBuffer strBuffer = new StringBuffer();

        strBuffer.append("statusCode : ").append(statusCode.getStatus())
        .append(", ")
        .append("responseCode : ").append(responseCode)
        .append(", ")
        .append("urlStr : ").append(urlStr);
        
        if ( TesterConfiguration.getConfiguration().getReport().getReportResultStr() || 
             TesterConfiguration.getConfiguration().getReport().getReportAll() )
        {
            strBuffer.append(", ").append("resultStr : ").append(resultStr);
        }
        
        if ( TesterConfiguration.getConfiguration().getReport().getReportAll() )
        {
            strBuffer.append(", ").append("postRequestBodyStr : ").append(postRequestBodyStr).
                      append(", ").append("isPOSTRequest : ").append(isPOSTRequest);
        }
        
        return strBuffer.toString();

    }

    
    // Base and simple implementation
    // Check response code. If responseCode == 200 set status StatusCode.TEST_OK
    protected StatusCode initStatusCode( int responseCode, String resultStr )
    {
        return ( responseCode == 200 ) ? StatusCode.TEST_OK : StatusCode.NO_RESPONSE;
    }
}
