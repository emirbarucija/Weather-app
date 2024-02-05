# Weather-app

### Reference Documentation

Weather tracking application created in Spring Boot 3 and Java 17.

Application reads the data from [7timer API](https://www.7timer.info/doc.php?lang=en) and saves it to the SQL database (MySql).

### Client Interfaces:
- GET /weather/nextSevenDays

Returns the array of objects, representing weather forecast for the next seven days, containing:
- day of the week
- weather type
- minimum temperature
- maximum temperature
- wind speed range
- wind name

### Starting the project locally
In order to start the project locally, you need to have MySql Server installed and have it up & running.

When you run the application, it will run on port 8080 by default.

You need to provide username and password when querying the REST endpoints. Default credentials can be found in _application.properties_ file.
