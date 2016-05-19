package hydraTester.NetworkTest;

import static hydraTester.Utils.RegExpUtil.*;
import hydraTester.HttpHandler;
import hydraTester.IfaGenerator;
import hydraTester.StatusCode;
import hydraTester.Utils.RegExpUtil;
import hydraTester.states.State;
import hydraTester.states.StateRequest;
import testerConfiguration.TesterConfiguration;

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

                String unescapedStr = resultStr.replaceAll("\\s", "");
                String impURL = RegExpUtil.findPattern(IMP_URL_REGEX, unescapedStr);
                String clickURL = RegExpUtil.findPattern(CLICK_URL_REGEX, unescapedStr);

                if ( unescapedStr.contains("<VAST") )
                {
                    impURL = RegExpUtil.findPattern(IMP_URL_REGEX, unescapedStr);
                    clickURL = RegExpUtil.findPattern(CLICK_URL_REGEX, unescapedStr);
                }
                else
                {
                    String clickUrl = RegExpUtil.findPattern(BANNER_CLICK_URL_REGEX, unescapedStr);
                    String destinationUrl = RegExpUtil.findPattern(EXTRACT_URI_REGEX, unescapedStr);
                    if ( clickUrl != null && destinationUrl != null && clickUrl.trim().length() > 0 && destinationUrl.trim().length() > 0 )
                    {
                        clickURL = clickUrl + "&url=" + destinationUrl;
                    }
                    impURL = RegExpUtil.findPattern(BANNER_IMP_URL_REGEX, unescapedStr);
                }

                if ( impURL == null || impURL.trim().length() <= 0 )
                {
                    resultStatus = StatusCode.MISSING_IMP_URL;
                }
                else if ( clickURL == null || clickURL.trim().length() <= 0 )
                {
                    resultStatus = StatusCode.MISSING_CLICK_URL;
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

        public Builder urlStr( String urlStr )
        {
            this.urlStr = urlStr;
            return this;
        }

        public Builder tagId( final String tagId )
        {
            String ifa = IfaGenerator.getIfa();
            String uri = TesterConfiguration.getConfiguration().getGeneral().getNetworkApiUrl();
            this.urlStr = uri + "?tagid=" + tagId + "&ifa=" + ifa + "&model=iPhone&ip=100.29.50.7&ua=Mozilla%2F5.0+%28iPhone%3B+CPU+iPhone+OS+6_1+like+Mac+OS+X%29+AppleWebKit%2F534.46+%28KHTML%2C+like+Gecko%29+Version%2F5.1+Mobile%2F9A334+Safari%2F7534.48.3";
            
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
