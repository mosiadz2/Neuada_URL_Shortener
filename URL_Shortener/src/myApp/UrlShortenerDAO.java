package myApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum UrlShortenerDAO
{
	instance;
	

	private Map<String, UrlShortener> urlMap = new HashMap<String, UrlShortener>();
	
	private UrlShortenerDAO()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;

		try {
			// Registering the HSQLDB JDBC driver
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			// Creating the connection with HSQLDB
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
			
			if (con != null) 
			{
				System.out.println("Connection created successfully");

			} else 
			{
				System.out.println("Problem with creating connection");
			}

			stmt = con.createStatement();
			result = stmt.executeQuery("SELECT * FROM \"PUBLIC\".\"URLS\"");

			while (result.next()) 
			{
				UrlShortener url = new UrlShortener();
				url.setLongUrl(result.getString("longUrl"));
				url.setShortUrl(result.getString("shortUrl"));
				urlMap.put(url.getLongUrl(), url);
			}

		} catch (Exception e) 
		{
			e.printStackTrace(System.out);
		}
	}
	
	public void createUrlEntry(UrlShortener url) 
	{
		Connection con = null;
		Statement stmt = null;
		int result = 0;
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
			stmt = con.createStatement();
			result = stmt
					.executeUpdate("INSERT INTO URLS VALUES("+"'" + url.getLongUrl() + "', " + "'" + url.getShortUrl() + "')");
	         //result = stmt.executeUpdate("INSERT INTO URLS VALUES ('jhdasgfyuagfuy', 'dfresa')"); 
			con.commit();
		} 
		catch (Exception e) 
		{
			e.printStackTrace(System.out);
		}
		System.out.println(result + " rows effected");
		System.out.println("Rows inserted successfully");

		urlMap.put(url.getLongUrl(), url);
	}
	
	public List<UrlShortener> getUrlShortener() 
	{
		urlMap.clear();

		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;

		try {
			// Registering the HSQLDB JDBC driver
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			// Creating the connection with HSQLDB
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "sa", "");
			if (con != null) 
			{
				System.out.println("Connection created successfully");

			} 
			else 
			{
				System.out.println("Problem with creating connection");
			}

			stmt = con.createStatement();
			result = stmt.executeQuery("SELECT * FROM Urls");
			

			while (result.next()) 
			{
				UrlShortener url = new UrlShortener();
				url.setLongUrl(result.getString("longUrl"));
				url.setShortUrl(result.getString("shortUrl"));
				urlMap.put(url.getShortUrl(), url);
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace(System.out);
		}

		List<UrlShortener> url = new ArrayList<UrlShortener>();
		url.addAll(urlMap.values());
		return url;
	}

	public UrlShortener getUrlShortener(String _shortUrl) 
	{
		return urlMap.get(_shortUrl);
	}
	
	public void SelectQuery(UrlShortener url)
	{
		   
		      Connection con = null;
		      Statement stmt = null;
		      ResultSet result = null;
		      
		      try {
		         Class.forName("org.hsqldb.jdbc.JDBCDriver");
		         con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
		         stmt = con.createStatement();
		         result = stmt.executeQuery("SELECT longUrl, shortUrl, FROM Urls WHERE shortUrl = '" + url.getShortUrl() + "'");

		         while(result.next())
		         {
		             System.out.println(result.getString("longUrl")+" |"+result.getString("shortUrl"));
		          }
		         
		      } catch (Exception e) {
		         e.printStackTrace(System.out);
		      }
	}

}
