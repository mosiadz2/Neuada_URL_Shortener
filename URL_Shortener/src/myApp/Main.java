package myApp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;

public class Main 
{

	//creating a dictionary that will hold short URL as a key and long URL as a value
	static Map<String, String> urlMap = new HashMap<String, String>();
	static String domain = "shorty.com/";
	
	
	//main
	public static void main(String[] args) throws Exception 
	{
		
		Main s = new Main();
		String shortUrl = "H2bzld";
		
		
		List<UrlShortener> urlList = GetUrlShortener();
		
		for(UrlShortener url : urlList)
		{
			System.out.println("LongUrl: " + url.getLongUrl());
			System.out.println("ShortUrl: " + url.getShortUrl());
		}
		
		
		//generate short URL from the long specified and save it in the data
		//Orginal long ULR
		String orginalURL = "https://www.google.com/search?q=puppy&rlz=1C1CHBF_enIE881IE881&sxsrf=ALe"
				+ "Kk03lOMzCJh94xCgxZw9dr4SJfTKwVA:1583606139857&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjNrLjLgInoAhXkpHEK"
				+ "HfcADc4Q_AUoAXoECBIQAw&biw=1920&bih=969#imgrc=AC2bVhmqa4roWM";	
		s.ShortenURL(orginalURL);
		
	}
	
	private void getLongURL(String _shortURL)
	{
		String longUrl;
		
        for (Entry<String, String> entry : urlMap.entrySet()) 
        {
            if (entry.getValue().equals(_shortURL)) 
            {
                System.out.printf("%n%nshort URL is: " + domain + _shortURL + "%nOrginal URL is: " + entry.getKey());
            }
        }
		
	}
	
	private void ShortenURL(String _orginalUrl) throws Exception
	{
		//running shortening function
		String shortUrl= new Main().ShorteningFunction(_orginalUrl);
		
		//getLongURL(shortUrl);
		
		Post(_orginalUrl, shortUrl);
	}
	
	//Shortening function will look in the map if it already contains
	//the URL, if not it will generate short url
	private String ShorteningFunction(String _orginalURL)
	{
		
		String shortURL;
		
		//takes care of few differences in URLs like;
		//http://www.facebook.com, https://www.facebook.com, www.facebook.com, www.facebook.com/
		_orginalURL = CheckURL(_orginalURL);
		
		
		//checks if already exists
		if(urlMap.containsKey(_orginalURL))
		{
			shortURL = urlMap.get(_orginalURL);
		}
		//if not, generate short url with random function
		else
		{
			StringBuilder charList = new StringBuilder();
            do
            {
                String random = GetRandom();
                charList.append((String)random);  
            }
            while(urlMap.containsValue(charList.toString()));

            
            urlMap.put(_orginalURL, charList.toString());
            shortURL =  charList.toString();
		}
		
		
		return shortURL;
	}
	
    
	//function that will generate random alphanumeric variable with the length of 6
    private String GetRandom()
    {
    	int shortUrlLenght = 6;//length of the shortUrl variable
    	String shortUrl;//variable where random string will be stored 
    	
    	// chose a Character random from this String 
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sBuilder = new StringBuilder(shortUrlLenght); 
  
        for (int i = 0; i < shortUrlLenght; i++) { 
  
            // generate a random string between 
            int index = (int)(alphaNum.length() * Math.random()); 
  
            // add Character one by one in end of string Builder 
            sBuilder.append(alphaNum.charAt(index)); 
        }
        shortUrl = sBuilder.toString();
  
        return shortUrl; //returns generated random String
    }
    
	String CheckURL(String _orginalURL) 
	{
		if (_orginalURL.charAt(_orginalURL.length() - 1) == '/')
		{
			_orginalURL = _orginalURL.substring(0, _orginalURL.length() - 1);
		}
		
		if (_orginalURL.substring(0, 7).equals("http://"))
		{
			_orginalURL = _orginalURL.substring(7);
		}
		
		if (_orginalURL.substring(0, 8).equals("https://"))
		{
			_orginalURL = _orginalURL.substring(8);
		}
	
		return _orginalURL;
	}
	
	//post
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
