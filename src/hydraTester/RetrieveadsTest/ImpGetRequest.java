package hydraTester.RetrieveadsTest;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import hydraTester.HttpHandler;
import hydraTester.StatusCode;
import hydraTester.states.State;
import hydraTester.states.StateRequest;

public class ImpGetRequest extends StateRequest
{
    final String jsonResponseStr;

    public ImpGetRequest( final String urlStr, final HttpHandler httHandler, final String jsonResponseStr )
    {
        super(urlStr, httHandler);
        this.jsonResponseStr = jsonResponseStr;
    }
    
    protected State createNextState()
    {        
        State holdState = null;
        if ( getStatusCode() == StatusCode.TEST_OK )
        {
            holdState = ClickGetRequest.Builder.newBuilder().
                    httHandler(this.getHttpHandler()).
                    resultStr(jsonResponseStr).
                    build();
        }
        
        if( holdState == null )
        {
            throw new IllegalArgumentException("ImpGetRequest can't create next state current error : " + getStatusCode());
        } 

        return holdState;
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
        
        strBuffer.append("ImpGetRequest state ")
        .append("----------------\n")
        .append( completStatus )
        .append("\n[ ").append(super.toString()).append(" ]")
        .append("\n--------------------------------------");

        return strBuffer.toString();        
    }
    
    public static class Builder
    {
        
        private String resultStr;
        private HttpHandler httHandler;

        public Builder resultStr( String resultStr )
        {
            this.resultStr = resultStr;
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

            JSONObject jsonObject = null;
            JSONParser jsonParser = new JSONParser(JSONParser.MODE_PERMISSIVE);

            try
            {
                jsonObject = (JSONObject) jsonParser.parse(resultStr);
            }
            catch ( ParseException ex )
            {
                throw new IllegalArgumentException("Can't parse ImpGetRequest JSON : [ " + resultStr + " ]" + ex);
            }

            String adUrlStr = jsonObject.getAsString("impUrl");

            if ( adUrlStr != null )
            {
                holdState = new ImpGetRequest(adUrlStr, httHandler, resultStr);

            }

            return holdState;
        }
        
        public static Builder newBuilder()
        {
            return new Builder();
        }
    }    
}