package myApp;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class Parser 
{
	boolean inUrl = false;
	boolean inUrls = false;
	boolean inLongUrl = false;
	boolean inShortUrl = false;
	
	UrlShortener currentUrlShortener;
	List<UrlShortener> currentUrlShortenerList;
	
	public List<UrlShortener> doParseUrlShortener(String s)
	{
		try
		{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser pullParser = factory.newPullParser();
			pullParser.setInput(new StringReader(s));
			processDocument(pullParser);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return currentUrlShortenerList;
	}
	
	public void processDocument(XmlPullParser pullParser)throws XmlPullParserException, IOException
	{
		int eventType = pullParser.getEventType();
		do
		{
			if(eventType == XmlPullParser.START_DOCUMENT)
			{
				System.out.println("Start Document");
			}
			else if (eventType == XmlPullParser.END_DOCUMENT)
			{
				System.out.println("End Document");
			}
			else if (eventType == XmlPullParser.START_TAG)
			{
				processStartElement(pullParser);
			}
			else if (eventType == XmlPullParser.END_TAG)
			{
				processEndElement(pullParser);
			}
			else if (eventType == XmlPullParser.TEXT)
			{
				processText(pullParser);
			}
			eventType = pullParser.next();
		}
		while(eventType != XmlPullParser.END_DOCUMENT);
	}
	
	public void processStartElement(XmlPullParser event)
	{
		String name = event.getName(); 
		if(name.equals("urlShorteners"))
		{
			inUrls = true;
			currentUrlShortenerList = new ArrayList<UrlShortener>();
		}
		else if(name.equals("urlShortener"))
		{
			inUrl = true;
			currentUrlShortener = new UrlShortener();
		}
		else if(name.equals("longUrl"))
		{
			inLongUrl = true;
		}
		else if(name.equals("shortUrl"))
		{
			inShortUrl = true;
		}
	}
	
	public void processText(XmlPullParser event)throws XmlPullParserException
	{
		if(inLongUrl)
		{
			String s = event.getText();
			currentUrlShortener.setLongUrl(s);
		}
		if(inShortUrl)
		{
			String s = event.getText();
			currentUrlShortener.setShortUrl(s);
		}
	}
	
	public void processEndElement(XmlPullParser event)
	{
		String name = event.getName();
		
		if(name.equals("urlShorteners"))
		{
			inUrls = false;
		}
		else if(name.equals("urlShortener"))
		{
			inUrl = false;
			currentUrlShortenerList.add(currentUrlShortener);
		}
		else if(name.equals("longUrl"))
		{
			inLongUrl = false;
		}
		else if(name.equals("shortUrl"))
		{
			inShortUrl = false;
		}
	}
}
