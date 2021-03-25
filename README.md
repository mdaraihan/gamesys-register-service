Gamesys register application done by Md Abu Raihan

# gamesys-register-service
This is a java application which exposes rest endpoints for registration(Register entity). 
Ideally I would call it User entity but to make the path restful URI and match the requirement 
the entity named as above.

# prerequisite
`Java 1.8
Maven
`

# how to build and run
To run this application it needs building a jar. The following command needs to run from a terminal while
navigated in the project.
Build:
```bash
mvn clean install
```
This will generate a jar(gamesys-register-service-0.0.1-SNAPSHOT.jar) in /target

Run jar:
Navigate to the project `gamesys-register-service` and run the following command
```bash
java -jar target/gamesys-register-service-0.0.1-SNAPSHOT.jar
```

# end points
The CRUD endpoint for Register entity 

CREATE:
```
POST localhost:8080/api/v1/register
```

GET
```
GET localhost:8080/api/v1/registers
GET localhost:8080/api/v1/registers/1
```

PUT
```
TODO
```

DELETE
```
TODO
```

# Api doc
Api documentation was built using swagger2

http://localhost:8080/swagger-ui.html#/register-controller


# Application health / info
These endpoints can be used to monitor health of the application. This can be used for monitoring purposes.
```
GET http://localhost:8080/health
GET http://localhost:8080/info
```




