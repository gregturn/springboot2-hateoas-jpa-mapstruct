# README #

Accompanying source code for blog entry at FIXME


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
curl -v "http://localhost:8800/api/actors/{id}"  (1, for instance)
curl -v "http://localhost:8800/api/actors/{id}/films"
curl -v "http://localhost:8800/api/films/{id}"   (1, for instance)
```
should result in successful responses.

### Who do I talk to? ###

* orlando.otero at asimio dot net
* https://www.linkedin.com/in/ootero