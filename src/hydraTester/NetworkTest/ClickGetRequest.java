package hydraTester.NetworkTest;

import static hydraTester.Utils.RegExpUtil.*;
import hydraTester.HttpHandler;
import hydraTester.StatusCode;
import hydraTester.Utils.RegExpUtil;
import hydraTester.states.State;
import hydraTester.states.StateRequest;

public class ClickGetRequest extends StateRequest
{
    
    final String tagResultStr;
    
    
    public ClickGetRequest( final String urlStr, final HttpHandler httHandler, final String bannerResultStr)
    {
        super(urlStr, httHandler);
        this.tagResultStr = bannerResultStr;
    }
   
    protected State createNextState()
    {
        return null;
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
        strBuffer.append("ClickGetRequest state ")
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

            if ( resultStr.contains("<VAST") )
            {
                holdState = buildVast(resultStr, httHandler);
            }
            else
            {
                holdState = buildBanner(resultStr, httHandler);
            }

            return holdState;
        }

        private State buildVast( final String resultStr, final HttpHandler httHandler )
        {
            State holdState = null;
            String unescapedStr = resultStr.replaceAll("\\s", "");
            String clickUrl = RegExpUtil.findPattern(CLICK_URL_REGEX, unescapedStr);

            if ( clickUrl != null && httHandler != null )
            {
                holdState = new ClickGetRequest( clickUrl, httHandler, resultStr);
            }

            return holdState;
        }
        
        private State buildBanner( final String resultStr, final HttpHandler httHandler )
        {
            State holdState = null;
            String unescapedStr = resultStr.replaceAll("\\s", "");
            String clickUrl = RegExpUtil.findPattern(BANNER_CLICK_URL_REGEX, unescapedStr);
            String destinationUrl = RegExpUtil.findPattern(EXTRACT_URI_REGEX , unescapedStr);

            if ( clickUrl != null && destinationUrl != null && httHandler != null )
            {
                holdState = new ClickGetRequest( clickUrl + "&url=" + destinationUrl, httHandler, resultStr);
            }

            return holdState;
        }
        
        public static Builder newBuilder()
        {
            return new Builder();
        }
    }    
}