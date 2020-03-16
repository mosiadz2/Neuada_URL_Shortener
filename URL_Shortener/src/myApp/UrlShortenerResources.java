package myApp;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/urlShortener")
public class UrlShortenerResources 
{
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
	public List<UrlShortener> getUrlShortener()
	{
		return UrlShortenerDAO.instance.getUrlShortener();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
	@Path("{urlShortenershortUrl}")
	public UrlShortener getUrlShortener(@PathParam("urlShortenershortUrl") String _shortUrl)
	{
		return UrlShortenerDAO.instance.getUrlShortener(_shortUrl);
	}
	

	
	@POST
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postUrlShortener(
			@FormParam("longUrl") String longUrl,
			@FormParam("shortUrl") String shortUrl,
			@Context HttpServletResponse servletResponse) throws IOException 
	{
		
		UrlShortener url = new UrlShortener();
		url.setLongUrl(longUrl);
		url.setShortUrl(shortUrl);

		UrlShortenerDAO.instance.createUrlEntry(url);

		servletResponse.sendRedirect("../createUrlEntry.html");
	}
}