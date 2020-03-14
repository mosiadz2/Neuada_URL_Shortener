package myApp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "urlShortener")

public class UrlShortener 
{
	private String shortUrl;
	private String longUrl;
	
	
	
	
	public String getShortUrl()
	{
		return shortUrl;
	}
	public void setShortUrl(String _shortUrl)
	{
		this.shortUrl = _shortUrl;
	}
	
	public String getLongUrl()
	{
		return longUrl;
	}
	public void setLongUrl(String _LongUrl)
	{
		this.longUrl = _LongUrl;
	}
	
}
