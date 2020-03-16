package myApp;

//The client side
//every request to the server will be done in this class


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Client 
{
	//post function
			public static void Post(String _longUrl, String _shortUrl) throws Exception
			{
					URI uri = new URIBuilder()
							.setScheme("http")
							.setHost("localhost")
							.setPort(8080)
							.setPath("/URL_Shortener/rest/urlShortener").build();
					
					System.out.println(uri.toString());
					
					HttpPost httpPost = new HttpPost(uri);
					httpPost.setHeader("Accept", "text/xml");
					CloseableHttpClient httpClient = HttpClients.createDefault();
					
					// POST
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("longUrl", _longUrl));
					nameValuePairs.add(new BasicNameValuePair("shortUrl", _shortUrl));

					              
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)) ;
					System.out.println("Sending request...");
					CloseableHttpResponse response = httpClient.execute(httpPost);
					
					System.out.print("Response: " + response.toString());
			}
			
			//getting the the list of URLs 
			public static List<UrlShortener> GetUrlShortener() 
			{
				CloseableHttpResponse response = null;
				CloseableHttpClient httpClient = null;
				
				List<UrlShortener> urlList = null;
				try 
				{
					URI uri = new URIBuilder()
							.setScheme("http")
							.setHost("localhost")
							.setPort(8080)
							.setPath("/URL_Shortener/rest/urlShortener").build();

					System.out.println(uri.toString());
					
					HttpGet httpGet = new HttpGet(uri);
					httpGet.setHeader("Accept", "application/xml");
					httpClient = HttpClients.createDefault();
					
					response = httpClient.execute(httpGet);
					
					String a;

					try 
					{
						HttpEntity entity = response.getEntity();
						a = getASCIIContentFromEntity(entity);
						//System.out.println(a);

						urlList = new Parser().doParseUrlShortener(a);
					} 
					finally 
					{
						response.close();
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				return urlList;
			}
			
			static String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException 
			{ 
				InputStream in = entity.getContent(); 
				StringBuffer out = new StringBuffer(); 
				int n = 1; 
				while (n > 0) 
				{ 
					byte[] b = new byte[4096]; 
					n = in.read(b); 
					if (n > 0) 
						out.append(new String(b, 0, n)); 
				} 
				return out.toString(); 
			}
	
}
