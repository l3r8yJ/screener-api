FROM openjdk:17
EXPOSE 8080
COPY target/screener_api_image.jar screener_api_image.jar
ENTRYPOINT ["java", "-jar", "/screener_api_image.jar"]
