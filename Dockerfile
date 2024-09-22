FROM openjdk:23-jdk
COPY target/*.jar /usr/src/myapp/app.jar
ENTRYPOINT ["java","-jar","/usr/src/myapp/app.jar"]