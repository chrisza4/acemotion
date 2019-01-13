FROM openjdk:8-alpine

COPY target/uberjar/acemotion.jar /acemotion/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/acemotion/app.jar"]
