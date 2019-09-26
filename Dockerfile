FROM openjdk:8
ADD target/myapp-0.0.1-SNAPSHOT.jar myapp-0.0.1-SNAPSHOT.jar
EXPOSE 8080
RUN sh -c 'touch /myapp-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/mydb", "-jar","myapp-0.0.1-SNAPSHOT.jar"]