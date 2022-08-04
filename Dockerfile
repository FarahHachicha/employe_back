FROM openjdk:8
ADD target/employeemanager-0.0.1-SNAPSHOT.jar employeemanager-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","employeemanager-0.0.1-SNAPSHOT.jar"]


