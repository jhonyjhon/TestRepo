package hydraTester.NetworkTest;

import hydraTester.HttpHandler;
import hydraTester.StatusCode;
import hydraTester.Utils.RegExpUtil;
import static hydraTester.Utils.RegExpUtil.*;
import hydraTester.states.State;
import hydraTester.states.StateRequest;

public class ImpGetRequest extends StateRequest
{
    
    final String tagResultStr;
        
    public ImpGetRequest( final String urlStr, final HttpHandler httHandler, final String bannerResultStr )
    {
        super(urlStr, httHandler);
        this.tagResultStr = bannerResultStr;
    }
    
    protected State createNextState()
    {        
        State holdState = null;
        if ( getStatusCode() == StatusCode.TEST_OK )
        {
            holdState = ClickGetRequest.Builder.newBuilder().
                    httHandler(this.getHttpHandler()).
                    resultStr(tagResultStr).
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
            String unescapedStr = resultStr.replaceAll("\\s","");        
            String impUrl = RegExpUtil.findPattern(IMP_URL_REGEX, unescapedStr);


            if ( impUrl != null && httHandler != null )
            {
                holdState = new ImpGetRequest(impUrl, httHandler, resultStr);
            }

            return holdState;
        }
        
        private State buildBanner( final String resultStr, final HttpHandler httHandler )
        {
            State holdState = null;
            String unescapedStr = resultStr.replaceAll("\\s","");        
            String impUrl = RegExpUtil.findPattern(BANNER_IMP_URL_REGEX, unescapedStr);

            if ( impUrl != null && httHandler != null )
            {
                holdState = new ImpGetRequest(impUrl, httHandler, resultStr);
            }

            return holdState;
        }
        
        public static Builder newBuilder()
        {
            return new Builder();
        }
    }      
}