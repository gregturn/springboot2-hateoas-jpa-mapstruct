# README #

Accompanying source code for blog entry at https://tech.asimio.net/2020/04/06/Adding-HAL-Hypermedia-to-Spring-Boot-2-applications-using-Spring-HATEOAS.html


### Requirements ###

* Java 8+
* Maven 3.2.x+

### Building the artifact ###

```
mvn clean package
```

### Running the application from command line ###

```
mvn spring-boot:run
```

### Available URLs

```
curl http://localhost:8800/api/actors/{id}  (1, for instance)
curl http://localhost:8800/api/actors/{id}/films
curl http://localhost:8800/api/films/{id}   (1, for instance)
curl -H "Accept: application/hal+json" http://localhost:8080/api/actors/1/films
curl -H "Accept: application/json" -H "X-Forwarded-Host: gateway.asimio.net" -H "X-Forwarded-Port: 9090" http://localhost:8080/api/films/133
```
should result in successful responses.

### Who do I talk to? ###

* orlando.otero at asimio dot net
* https://www.linkedin.com/in/ootero