package myApp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Main 
{

	//creating a dictionary that will hold short URL as a key and long URL as a value
	static Map<String, String> urlMap = new HashMap<String, String>();
	static String domain = "shorty.com/";
	
	
	//main
	public static void main(String[] args) 
	{
		
		Main s = new Main();
		s.ShortenURL();
		
	}
	
	private void getLongURL(String _shortURL)
	{
		Object urlObject = null;
		
		if(urlMap.containsKey(_shortURL))
		{
		    urlObject = urlMap.get(_shortURL);
		}
		
		String url = (String)urlObject;
		
		System.out.printf("The orginal URL is: " + url);
		
	}
	
	private void ShortenURL()
	{
		//Orginal long ULR
		String orginalURL = "https://www.google.com/search?q=puppy&rlz=1C1CHBF_enIE881IE881&sxsrf=ALe"
				+ "Kk03lOMzCJh94xCgxZw9dr4SJfTKwVA:1583606139857&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjNrLjLgInoAhXkpHEK"
				+ "HfcADc4Q_AUoAXoECBIQAw&biw=1920&bih=969#imgrc=AC2bVhmqa4roWM";	
		//running shortening function
		String shortUrl= new Main().ShorteningFunction(orginalURL);

////////Checking the results ////////
		if(!urlMap.isEmpty()) 
	    {
			System.out.printf("The orginal URL is: " + orginalURL + "%nshort URL is: " + domain + shortUrl);
	    }
		
		getLongURL(shortUrl);
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
    	int shortUrlLenght = 6;//length of the alphanumeric variable
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
