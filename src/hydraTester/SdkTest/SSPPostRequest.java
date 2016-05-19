package hydraTester.SdkTest;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import testerConfiguration.TesterConfiguration;
import hydraTester.HttpHandler;
import hydraTester.IfaGenerator;
import hydraTester.StatusCode;
import hydraTester.states.State;
import hydraTester.states.StateRequest;

public class SSPPostRequest extends StateRequest
{

    public SSPPostRequest( String urlStr, HttpHandler httHandler, String bodyStr )
    {        
        super( urlStr, httHandler, true, bodyStr );
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
        
        if( holdState == null )
        {
            throw new IllegalArgumentException("SSPGetRequest can't create next state current error : " + getStatusCode());
        }        

        return holdState;
    }

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

                    if ( !jsonObject.containsKey("impurl") ||
                          jsonObject.getAsString("impurl") == null ||
                          jsonObject.getAsString("impurl").trim().length() <= 0 )
                    {
                        resultStatus = StatusCode.MISSING_IMP_URL;
                    }
                    else if ( !jsonObject.containsKey("clickurl") ||
                               jsonObject.getAsString("clickurl") == null ||
                               jsonObject.getAsString("clickurl").trim().length() <= 0 )
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
        
        if( getStatusCode() == StatusCode.TEST_OK ){
            completStatus = "SUCCESSFULLY COMPLETED";
        }
        else {
            completStatus = "FAILS WITH ERROR";
        }
        
        strBuffer.append("SSPGetRequest state ")
        .append("----------------\n")
        .append( completStatus )
        .append("\n[ ").append(super.toString()).append(" ]")
        .append("\n--------------------------------------");

        return strBuffer.toString();
    }
    
    public static class Builder
    {
        private String urlStr;
        private HttpHandler httHandler;
        private String body;

        public Builder urlStr( String urlStr )
        {
            this.urlStr = urlStr;
            return this;
        }

        public Builder tagId( final String tagId )
        {           
            urlStr = TesterConfiguration.getConfiguration().getGeneral().getSdkApiUrl();
            String ifa = IfaGenerator.getIfa();
            body = "{\"tagid\":\"" + tagId + "\",\u201Corientation\u201D:\"2\",\"dnt\":\"0\",\"ua\":\"Mozilla/5.0 (iPhone; CPU iPhone OS 6_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3\",\"country\":\"USA\",\"city\":\"Los Angeles\",\"ifa\":\"" + ifa + "\",\"carrier\":\"310-410\",\"language\":\"en\",\"make\":\"Apple\",\"model\":\"iPhone\",\"os\":\"iOS\",\"osv\":\"6.1\",\"connectiontype\":\"2\",\"width\":\"320\",\"height\":\"480\",\"firstlaunch\":\"1234556\",\"timestamp\":\"122345566\"}";
            
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
                holdState = new SSPPostRequest(this.urlStr, this.httHandler, this.body );
            }
            return holdState;
        }

        public static Builder newBuilder()
        {
            return new Builder();
        }
    }  
}
