# Neuada_URL_Shortener

<br/>Contributor: Patryk Mosiezny.
<br/>App functionality: 
Generate short alphanumeric value from given long URL and saves it into persistent database.
Retreive the long URL by giving generated short URL
Checking if Long URL already exists in database. 

IDE used: Eclipse Version: 2019-12 (4.14.0)
<br/>Server for REST API Used: Apache Tomcat Version 9.0.16
<br/>Database: HSQLDB Version 2.5.0

<br/>After configuring eclipse with Tomcat and HSQL Database, run server inside of eclipse and then database.
<br/>To run database go into folder hsqldb-2.5.0/hsqldb/bin and open runServer.bat
<br/>To run the application simply start the Main class inside eclipse. 
<br/>
<br/>To list all entries from the database, put this link into a web browser; http://localhost:8080/URL_Shortener/rest/urlShortener

