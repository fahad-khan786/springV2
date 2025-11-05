# Use Tomcat base image with JDK 17
FROM tomcat:9-jdk17

# Remove default Tomcat apps (optional but cleaner)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file to Tomcat's webapps directory
COPY target/myapp.war /usr/local/tomcat/webapps/ROOT.war

# Expose your application port
EXPOSE 9999

# Start Tomcat
CMD ["catalina.sh", "run"]
