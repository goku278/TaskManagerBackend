Dear all.

I am sharing instructions to open this project on IntelliJ ide, and then how to run this java spring boot project.

1. Please clone this project to your directory, using git desktop, or other git applications, or by cmd

2. Please open application.properties and set this settings, as shown here.


	spring.application.name=TaskManager
        spring.datasource.url=jdbc:postgresql://localhost:5432/task
        spring.datasource.username=postgres
        spring.datasource.password=root
        spring.datasource.driver-class-name=org.postgresql.Driver
	spring.jpa.show-sql=true
	spring.jpa.hibernate.ddl-auto=update
	spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect


	[This is for setting up PostgreSQL]

Also please download PostgreSQL from this url link

https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

[I am sharing for windows platform]


Also please configure PostgreSQL, once it is downloaded successfully.

Please type

SELECT USER;

[To get to know the user name]

ALTER USER existing_username WITH PASSWORD 'new_password';


[And this is to change the existing_username password]

Then, please navigate to the TaskManagerApplication class and run the project

Also shared Postman collection and a demo video, kindly check those as well.

Thank you.


