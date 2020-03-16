package myApp;

public class Main 
{	
	
	//main
	public static void main(String[] args) throws Exception 
	{	
		ShortenerController controller = new ShortenerController();
		
		
		//Short URL to retrieve long URL
		String shortUrl = "BCjPmO";
		
		//Past URL to generate Short Link
		String orginalURL = "https://www.google.com/search?q=cats&rlz=1C1CHBF_enIE881IE881&sxsrf=ALeKk00hDkA"
				+ "I5c1vXQI2JDAPTh1mpQXmhA:158423413961asdasd1&source=lnms&tbm=isch&sa=X&ved=2ahUKEwiQn4eJpJvoAhWGZx"
				+ "UIHQcWC9AQ_AUoAXoECCUQAw#imgrc=DGPsdfsdfdjeja1JKT8CM";
	
		
		
		
		////////FUNCTIONS//////////////
		//Only one could be ran at time.
		//Comment out the function that you don't want to use.
		
		//generate short URL from the long URL and save it in the database 
		//controller.ShorteningFunction(orginalURL);	
		
		//get long URL by short URL
		controller.getLongURL(shortUrl);
		
	}

}
