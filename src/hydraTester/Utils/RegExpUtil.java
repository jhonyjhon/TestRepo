package hydraTester.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtil
{
    public static final String BANNER_IMP_URL_REGEX = "<script>document\\.getElementById\\(\\'iaIImpressionTrackingPixel\\'\\)\\.src=\"(.*?)\"</script>";
    public static final String IMP_URL_REGEX = "<Impression><!\\[CDATA\\[(.*?)\\]\\]\\>\\</Impression>";
    public static final String BANNER_CLICK_URL_REGEX = "<script>functionlinkClicked\\(link\\,event\\)\\{variaServer=\"(.*?)\";varlinkHref=link";
    public static final String EXTRACT_URI_REGEX = "<ahref=\\'(.*?)\\'><img";
    public static final String CLICK_URL_REGEX = "<ClickTracking><!\\[CDATA\\[(.*?)\\]\\]\\>\\</ClickTracking>";
    
    public static String findPattern( String pattern, String input )
    {
        try
        {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(input);
            m.find();
            return ( m.group(1) );
        }
        catch ( Exception ex )
        {
            return null;
        }
    }
}
