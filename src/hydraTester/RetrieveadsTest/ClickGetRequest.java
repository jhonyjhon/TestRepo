package hydraTester.RetrieveadsTest;

import hydraTester.HttpHandler;
import hydraTester.StatusCode;
import hydraTester.states.State;
import hydraTester.states.StateRequest;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class ClickGetRequest extends StateRequest
{
    final String jsonResultStr;

    public ClickGetRequest( final String urlStr, final HttpHandler httHandler, final String jsonResultStr )
    {
        super(urlStr, httHandler);
        this.jsonResultStr = jsonResultStr;
    }

    protected State createNextState()
    {
        State holdState = null;

        return holdState;
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
        strBuffer.append("ClickGetRequest state ").
        append("----------------\n").
        append(completStatus).
        append("\n[ ").
        append(super.toString()).
        append(" ]").
        append("\n--------------------------------------");

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
                throw new IllegalArgumentException("Can't parse ClickGetRequest JSON : [ " + resultStr + " ]" + ex);
            }

            String adUrlStr = jsonObject.getAsString("adUrl");

            if ( adUrlStr != null )
            {
                holdState = new ClickGetRequest(adUrlStr, httHandler, resultStr);
            }

            return holdState;
        }

        public static Builder newBuilder()
        {
            return new Builder();
        }
    }
}