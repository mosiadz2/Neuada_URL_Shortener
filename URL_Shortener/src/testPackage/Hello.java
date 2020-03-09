package testPackage;

//Liblaries
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/hello")
public class Hello
{
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHelloHTML()
	{
		String response = "<html>" + "<title>" + "Hello Patryk" + "</title>" + "<body><h1>" + "Hello Patryk from HTML" + 
				"</h1></body>" + "</html>";
		return response;
	}
	
	  @GET
	  @Produces(MediaType.TEXT_XML) public String sayHelloXML() 
	  { 
		  String response = "<?xml version='1.0'?>" + "<hello>Hi Patryk, this is Hello from XML</hello>";
		  return response; 
	  
	  }
	  
	  @GET
	  @Produces(MediaType.TEXT_PLAIN) public String sayHelloPlain() 
	  { 
		  String response = "Hi Patryk, this is Hello from HTML"; 
		  return response;
	  }
	
}
