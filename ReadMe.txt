Notes:
	Database will be automatically created upon startup of tomcat.
------------------------------------
Environment / Tools:
Maven		Apache Maven 3.3.9
Java 		Java version: 1.8.0_101
Spring		Version 4
Hibernate	4.3.5.Final
Tomcat		apache-tomcat-8.0.44
MySQL		MySQL Server 5.7
Toad		Toad for MySQL 7.9.0.637
Postman		Chrome
-------------------------------------
MySQL configuration
url	 	jdbc:mysql://localhost:3306/testDB
username	admin
password	Admin123

* Modify username and password based on the MySQL admin setup
\src\main\webapp\WEB-INF\properties\config_env.properties
-------------------------------------
Steps to deploy:
1. Copy SoccerMatch.war to tomcat directory (apache-tomcat-8.0.44\webapps)
2. Start MySQL server. 
	> Open command prompt and type services.msc
	> Find MySQL57 and click start
2. Start tomcat apache-tomcat-8.0.44\bin\startup.bat
-------------------------------------



============
testing webhook 1