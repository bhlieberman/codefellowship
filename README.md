# `Codefellowship`

Start this application with ./gradlew bootRun. The app exposes the following routes on http://localhost:8080:

`/`: This displays is the home page. Links for signing up and logging in are presented which redirect to the routes listed below.
`/signup`: This route can be accessed directly or by clicking "Signup" on the home page. It creates a new user and persists this data in a PostgreSQL database.
`/login`: This route displays the login form.

This application requires a local installation of PostgreSQL. Once configuration for your environment is complete, a file called application.properties should be created in your `/resources` folder. This will look like this:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/?user=<user>&password=<user>
#spring.datatsource.username=
#spring.datasource.password=

#//spring.jpa.hibernate.dll-auto=create //creates and drops tables everytime you run your app
# UPDATE means check current STATE of DB, updates what it needs to
# NONE, CREATE-DROP,
spring.jpa.hibernate.dll-auto=update
spring.jpa.generate-ddl=true```
```

where user and password are for your local Postgres setup. Please also be sure to use a JDBC-compliant connection string like the one shown above. Optionally, you can provide your username and password in the spring.datasource.username and spring.datasource.password properties, respectively.