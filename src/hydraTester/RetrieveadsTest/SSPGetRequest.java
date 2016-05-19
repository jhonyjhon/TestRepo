package hydraTester.RetrieveadsTest;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import testerConfiguration.TesterConfiguration;
import hydraTester.HttpHandler;
import hydraTester.StatusCode;
import hydraTester.states.State;
import hydraTester.states.StateRequest;

public class SSPGetRequest extends StateRequest
{

    public SSPGetRequest( String urlStr, HttpHandler httHandler )
    {
        super(urlStr, httHandler);
    }

    protected State createNextState()
    {
        State holdState = null;
        if ( getStatusCode() == StatusCode.TEST_OK )
        {
            holdState = ImpGetRequest.Builder.newBuilder().
                    httHandler(this.getHttpHandler()).
                    resultStr(this.getResultStr()).
                    build();
        }

        if ( holdState == null )
        {
            throw new IllegalArgumentException("SSPGetRequest can't create next state current error : " + getStatusCode());
        }

        return holdState;
    }

    @Override
    protected StatusCode initStatusCode( int responseCode, String resultStr )
    {
        StatusCode resultStatus = StatusCode.NO_RESPONSE;

        if ( responseCode == 200 )
        {
            if ( resultStr == null || resultStr.contains("default adm") || resultStr.contains("\"error\"") )
            {
                resultStatus = StatusCode.NO_BID_RESPONSE;
            }
            else
            {
                JSONObject jsonObject = null;
                JSONParser jsonParser = new JSONParser(JSONParser.MODE_PERMISSIVE);

                try
                {
                    jsonObject = (JSONObject) jsonParser.parse(resultStr);

                    if ( !jsonObject.containsKey("impUrl") || 
                          jsonObject.getAsString("impUrl") == null || 
                          jsonObject.getAsString("impUrl").trim().length() <= 0 )
                    {
                        resultStatus = StatusCode.MISSING_IMP_URL;
                    }
                    else if ( !jsonObject.containsKey("adUrl") || 
                               jsonObject.getAsString("adUrl") == null || 
                               jsonObject.getAsString("adUrl").trim().length() <= 0 )
                    {
                        resultStatus = StatusCode.MISSING_CLICK_URL;
                    }
                }
                catch ( ParseException ex )
                {
                    throw new IllegalArgumentException("Can't parse ImpGetRequest JSON : [ " + resultStr + " ]" + ex);
                }
            }
        }

        return ( resultStatus == StatusCode.NO_RESPONSE ) ? super.initStatusCode(responseCode, resultStr) : resultStatus;
    }
    
    @Override
    public String toString()
    {
        StringBuffer strBuffer = new StringBuffer();
        String completStatus = null;

        if ( getStatusCode() == StatusCode.TEST_OK )
        {
            completStatus = "SUCCESSFULLY COMPLETED";
        }
        else
        {
            completStatus = "FAILS WITH ERROR";
        }

        strBuffer.append("SSPGetRequest state ").append("----------------\n").append(completStatus).append("\n[ ").append(super.toString()).append(" ]").append("\n--------------------------------------");

        return strBuffer.toString();
    }

    public static class Builder
    {
        private String urlStr;
        private HttpHandler httHandler;

        public Builder urlStr( String urlStr )
        {
            this.urlStr = urlStr;
            return this;
        }

        public Builder uri()
        {
            String uri = TesterConfiguration.getConfiguration().getGeneral().getRetrieveadsUrl();            
            this.urlStr = uri + "?i=9.8.93.178&r=320x480&t=2&u=Mozilla%2F5.0+%28Linux%3B+U%3B+Android+4.2.2%3B+nl-nl%3B+GT-I9505+Build%2FJDQ39%29+AppleWebKit%2F534.30+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Mobile+Safari%2F534.30&loc=en-uk&nt=wifi&marital=single&income=100&interests=cars,sports,F1,stocks&edu=UG&pd=yaniv&gender=male&dob=19901212&age=15&lat=80&lon=99&tt_aff_impid=333&tt_aff_clickid=444&plc=1123&aff_id=351725&tt_sub_aff=11123";

            return this;
        }

        public Builder httHandler( HttpHandler httHandler )
        {
            this.httHandler = httHandler;
            return this;
        }

        public State build()
        {
            State holdState = null;

            if ( urlStr != null && httHandler != null )
            {
                holdState = new SSPGetRequest(this.urlStr, this.httHandler);
            }
            return holdState;
        }

        public static Builder newBuilder()
        {
            return new Builder();
        }
    }
}
