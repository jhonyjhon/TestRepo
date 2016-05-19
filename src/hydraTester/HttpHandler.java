package hydraTester;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpHandler
{
    public HttpResponse postRequest(String uri, String body) throws Exception
    {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(uri);
        
        HttpEntity entity = new ByteArrayEntity(body.getBytes("UTF-8"));
        request.setEntity(entity);
        return client.execute(request);
    }
    
    public HttpResponse getRequest(String uri) throws Exception
    {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(uri);
     
        return client.execute(request);
    }
    
	public String sendPostRequest(String uri, String body) throws Exception
	{
		HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(uri);
        
        HttpEntity entity = new ByteArrayEntity(body.getBytes("UTF-8"));
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        
        String result = EntityUtils.toString(response.getEntity());
        return result;
	}
	
	public String sendGetRequestAndReceiveResponse(String uri) throws Exception
	{ 
		HttpResponse response = sendHttpGetRequest(uri);
	 
		String result = EntityUtils.toString(response.getEntity());
        return result;
	}

	public int sendGetRequestAndReceiveStatusCode(String uri) throws Exception
	{ 
		HttpResponse response = sendHttpGetRequest(uri);
	 
		return response.getStatusLine().getStatusCode();
	}

	public HttpResponse sendHttpGetRequest(String uri) throws Exception
	{
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(uri);
	 
		HttpResponse response = client.execute(request);
		return response;
	}
}

