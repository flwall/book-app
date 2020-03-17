**Running the Application:**  
For a Dev Run: `mvnw quarkus:dev`   
 -> App runs on localhost:8080  (can be configured in application.properties with quarkus.http.port=<port>)

To Package the application into a jar:
`mvnw clean package`

Swagger UI can be accessed via `/swagger`  

Right now, everything is untested and unstable

**Todos**  
* application profiles (dev and prod)  
* branching
