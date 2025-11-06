FROM eclipse-temurin:17-jdk
EXPOSE 9988
ADD target/myapp.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]