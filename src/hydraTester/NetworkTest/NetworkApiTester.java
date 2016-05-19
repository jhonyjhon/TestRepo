package hydraTester.NetworkTest;

import hydraTester.HttpHandler;
import hydraTester.HydraTest;
import hydraTester.states.Chain;
import hydraTester.states.State;

import java.util.ArrayList;
import java.util.List;

public class NetworkApiTester implements HydraTest
{

    private static final HttpHandler handler;
    private static final Chain rootChain;
    private static final List<State> stateList;	
	
    static
    {
        handler = new HttpHandler();
        rootChain = new Chain();
        stateList = new ArrayList<>();
    }
    
    
    private void sspRequest()
    {
        stateList.add(rootChain.pull());
    }

    private void clickRequest()
    {
        stateList.add(rootChain.pull());
    }

    private void impRequest()
    {
        stateList.add(rootChain.pull());
    }
    
    public void initState( String tagId )
    {
        State initState = SSPGetRequest.Builder.newBuilder().
                httHandler(handler).
                tagId( tagId ).
                build();
        
        rootChain.setState(initState);
    }
    
    public void cleanState( ){
        stateList.clear();
    }
    
    @Override
	public void performTest()
	{
        sspRequest();
        clickRequest();
        impRequest();	    
	}
	
    public String createReport()
    {
        StringBuffer strBuffer = new StringBuffer();

        for ( State state : stateList )
        {
            strBuffer.append(state.toString()).append("\n");
        }

        return strBuffer.toString();
    }    
}
