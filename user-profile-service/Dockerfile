FROM amazoncorretto:17-alpine
ENV APP_HOME=/opt/app
ARG JAR_FILE=./build/libs/user-profile-service-*.jar
WORKDIR $APP_HOME
COPY $JAR_FILE app.jar
EXPOSE 8080
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "app.jar"]