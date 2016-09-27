# Hair-Salon-Example
A sample hair salon server and client.

The server is written in Java, the client in JavaScript, AngularJS CSS and HTML, and the database is SQL.

How to run the system - 

* Server side -  needs to be run on a local server (I'm using Apache Tomcat), on port 8080.

* MySQL server - The required SQL statements can be found in the SQL directory of the server folder. The SQL server needs to run locally on port 3306.
The authenticaion for the SQL server is as follows - 

  Usernmae - root
  
  Password - pass123

* Web server - needs to run locally on a local server (I'm using Apache Http - point it to index.html), on port 80 (Its vital that the web client to run on a server, for the Facebook Connect to work).

The web server's main file is index.html, and the CSS code is located in default.css.

Several points - 
------------------------

* User authentication is done through Facebook Connect.

* Calendar - 
  1. The calendar adapts to the selected service's duration.
  2. The calendar shows only the hair salon's opening hours (8:00 to 17:00).

* Appointments validation - 
Appointments validation is done in two phases - 
  1. Whether the selected time is one time slot - checked at client side.
  2. Whether the selected time is free or booked - done at server side.

* User registrations/authentication - 
User registrations/authentication is done in 2 phases - 
  1. Check whether there are cookies containing the user's information.
  2. If not - attemp to check if the user is registered through Facebook. If not - prompt the user with Facebook login.

* Database - 
The database consists of three tables; users, services and appointments.
The appointments table has 2 foreign keys, linking to the customer and the required service. This allows the owner of the salon to extract all the appointment's info through a simple SQL statement.

* The web client is designed to be adaptive to the changing content(e.g adding/removing services).

* Logging in the server side is done with log4j2.
