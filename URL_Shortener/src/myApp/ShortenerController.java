package myApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


public class ShortenerController 
{
	//creating a dictionary that will hold short URL as a key and long URL as a value
	static Map<String, String> urlMap = new HashMap<String, String>();
	//Domain name that will be placed before generated short URL
	static String domain = "shorty.com/";
	private Client client;
	
	
	public void getLongURL(String _shortURL)
	{
		String longUrl = "";
		
		//Getting the list of entries in URL database and looping 
		//through it to check if long URL already persists in the database
		List<UrlShortener> urlList = client.GetUrlShortener();
		
		for(UrlShortener url : urlList)
		{
			if(url.getShortUrl().equals(_shortURL))
			{
				longUrl = url.getLongUrl();
				System.out.printf("%nRedirecting to: " + longUrl);
				break;
			}
		}
		//if not, prints that URL does not exists
		if(longUrl == "")
		{
			System.out.printf("%nURL does not exists");
		}
		
	}
	
	//Shortening function will look in the database if it already contains
	//the URL, if not it will generate short version of the long URL
	public String ShorteningFunction(String _orginalURL) throws Exception
	{
		
		String shortURL = "";
		
		//takes care of few differences in URLs like;
		//http://www.facebook.com, https://www.facebook.com, www.facebook.com, www.facebook.com/
		_orginalURL = CheckURL(_orginalURL);
		
		//checks if already exists
		List<UrlShortener> urlList = client.GetUrlShortener();
		for(UrlShortener url : urlList)
		{
			if(url.getLongUrl().equals(_orginalURL))
			{
				shortURL = url.getShortUrl();
			}
		}
		
		//if not, generate short url with random function
		if(shortURL == "")
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
            
            //adding long and short URL into a Database
            client.Post(_orginalURL, shortURL);
		}
		
		System.out.printf("%nShort URL is: " + domain + shortURL);
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
}
