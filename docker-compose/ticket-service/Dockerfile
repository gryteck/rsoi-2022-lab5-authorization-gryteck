FROM openjdk:11

COPY *.jar app.jar

ENTRYPOINT ["java" ,"-Xmx512m", "-Xms512m", "-jar","/app.jar"]